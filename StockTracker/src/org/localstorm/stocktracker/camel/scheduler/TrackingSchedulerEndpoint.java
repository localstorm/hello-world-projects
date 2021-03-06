package org.localstorm.stocktracker.camel.scheduler;

import org.localstorm.stocktracker.camel.GenericConsumerableEndpoint;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.config.Configuration;
import org.localstorm.stocktracker.config.GlobalConfiguration;
import org.localstorm.stocktracker.exchange.*;
import org.localstorm.stocktracker.util.misc.Sequence;
import org.localstorm.stocktracker.camel.util.ProcessUtil;
import org.localstorm.stocktracker.util.misc.ThreadUtil;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;

/**
 * @in StockTrackingRequest instance
 * @out AnalyzerInstruction instance
 * @author Alexey Kuznetsov
 */
public class TrackingSchedulerEndpoint extends GenericConsumerableEndpoint<DefaultExchange>
{
    private static final Log log = LogFactory.getLog(TrackingSchedulerEndpoint.class);
    // This guarantee the only request per user at the same time
    private static final ConcurrentMap<String, Object> locks = new ConcurrentHashMap<String, Object>(10240);

    public static final String ANALYZER_INSTRUCTION_KEY = "$aInstruction";
    public static final String   SCHEDULER_ENDPOINT_KEY = "$schedulerEp";

    private Scheduler scheduler;
    private Sequence  seq = new Sequence();
    private int userQuota;
    private int minIntervalInSeconds;

    public TrackingSchedulerEndpoint(String endpointUri,
                                     TrackingSchedulerComponent component) throws Exception {
        super(endpointUri, component);

        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        scheduler = schedFact.getScheduler();
        scheduler.start();

        Configuration   conf = GlobalConfiguration.getConfiguration();
        userQuota            = conf.getUserMaxTrackingEventsQuota();
        minIntervalInSeconds = conf.getEventMinIntervalSize();
    }

    public Producer<DefaultExchange> createProducer() throws Exception {
        return new TrackingSchedulerProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    // Some business -----------------------

    /* package */ void scheduleTracking(StockTrackingRequest str) throws SchedulerException {
        
        String account = str.getAccount();
        Object lock    = this.getAccountLock(account);

        synchronized(lock) {
            this.checkUserQuota(str);
            this.cancelTracking(account);

            for (StockEvent e: str.getWatchList()) {

                if ( this.needSchedule(e) ) {
                    this.scheduleTrackingEvents(account, e);
                }

            }
        }
        
    }

    /*package*/ void onJobExecute(AnalyzerInstruction ai)
    {
        try {
            // Sending AnalyzerInstruction instance to all consumers
            ProcessUtil.process(ai, this.getCamelContext(), super.getConsumers());
        } catch(Exception e) {
            log.error(e);
        }
    }


    // Private: -----------------------

    private void cancelTracking(String account) throws SchedulerException
    {
        scheduler.pauseTriggerGroup(account);
        waitForExecutedJobGroup(scheduler, account);
        forceTrackingEnd(scheduler, account);
        scheduler.resumeTriggerGroup(account);
    }

    private void checkUserQuota(StockTrackingRequest str) throws SchedulerException
    {
        if ( str.getWatchList().size() > userQuota ) {
            throw new SchedulerException("User event tracking overquota!");
        }
    }

    private void forceTrackingEnd(Scheduler sched, String account)
            throws SchedulerException {
        // Executing all end-jobs

        String[] jobNames = sched.getJobNames(account);
        for (String jobName : jobNames) {
            JobDetail jobDetail = sched.getJobDetail(jobName, account);
            if (jobDetail==null) {
                continue;
            }

            JobDataMap jdm = jobDetail.getJobDataMap();
            AnalyzerInstruction ai = (AnalyzerInstruction) jdm.get(TrackingSchedulerEndpoint.ANALYZER_INSTRUCTION_KEY);
            if (ai.isEnd()) {
                this.onJobExecute(ai); // Forced end of tracking
            }

            sched.deleteJob(jobName, account);
        }
    }

    private Object getAccountLock(String account)
    {
        Object newLock = new Object();
        Object lock = locks.putIfAbsent(account, newLock);
        if (lock == null)
        {
            lock = newLock;
        }
        return lock;
    }

    private void scheduleTrackingEvents(String account, StockEvent e) throws SchedulerException {

        SimpleTrigger   endTrigger = new SimpleTrigger(generateNextTriggerName(), account);
        SimpleTrigger startTrigger = new SimpleTrigger(generateNextTriggerName(), account);

        Date end = e.getEnd();
        Date reqStart = e.getStart();
        Date start = (reqStart!=null) ? reqStart : immediately();

        this.configureTrigger(endTrigger,   end);
        this.configureTrigger(startTrigger, start);

        JobDetail   endJob = this.generateJodDetail(account, e, true);
        JobDetail startJob = this.generateJodDetail(account, e, false);

        try {
            scheduler.scheduleJob(endJob, endTrigger);
        } catch(SchedulerException ex) {
            log.warn("Can't schedule termination job. Aborting..", ex);
            throw ex;
        }

        this.onJobExecute((AnalyzerInstruction) startJob.getJobDataMap().get(ANALYZER_INSTRUCTION_KEY));
        this.onJobExecute((AnalyzerInstruction) endJob.getJobDataMap().get(ANALYZER_INSTRUCTION_KEY));

        try {
            if (log.isDebugEnabled()) {
                log.debug("Scheduling job: "+startTrigger.getName()+";"+endJob.getName());
            }

            scheduler.scheduleJob(startJob, startTrigger);
            
       } catch(SchedulerException ex) {
            log.error("Can't schedule start job. Cancelling end job schedule:", ex);
            cancelQuietly(endTrigger, account);
            throw ex;
       }
    }


    private void cancelQuietly(SimpleTrigger t, String account) {
        try {
            scheduler.unscheduleJob(t.getName(), account);
        } catch(SchedulerException e) {
            log.error(e);
        }
    }

    private void configureJob(JobDetail j, String account, StockEvent e, boolean end) {
        JobDataMap jdm = j.getJobDataMap();

        AnalyzerInstruction ai =  new AnalyzerInstruction(end,
                                                          e.getSymbol(),
                                                          account,
                                                          e.getType(),
                                                          e.getThreshold());
        jdm.put(ANALYZER_INSTRUCTION_KEY, ai);
        jdm.put(SCHEDULER_ENDPOINT_KEY, this);
    }

    private void configureTrigger(SimpleTrigger t, Date time) {
        t.setStartTime(time);
        t.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
    }

    private String generateJobName(String account, StockEvent e, boolean isEnd) {
        // Need verbose name here
        StringBuilder sb = new StringBuilder();
        sb.append(account);
        sb.append('-');
        sb.append(e.getSymbol());
        sb.append('(');
        sb.append(e.getThreshold());
        sb.append(':');
        sb.append(e.getType());
        sb.append(')');
        sb.append("-(");

        Date start = e.getStart();
        sb.append((start!=null) ? start.getTime(): -1);
        sb.append(':');
        sb.append(e.getEnd().getTime());

        sb.append(")-");

        sb.append(seq.next());

        if (isEnd) {
            sb.append("-END");
        }

        
        
        return sb.toString();
    }

    private JobDetail generateJodDetail(String account, StockEvent e, boolean end) {
        JobDetail j = new JobDetail(generateJobName(account, e, end), account, TrackingSchedulerJob.class);
        this.configureJob(j, account, e, end);
        return j;
    }

    private String generateNextTriggerName() {
        StringBuilder sb = new StringBuilder(20);
        sb.append(System.currentTimeMillis());
        sb.append('-');
        sb.append(seq.next());
        return sb.toString();
    }

    private Date getMinEnd() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, minIntervalInSeconds);
        return c.getTime();
    }

    private Date immediately() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, -1);
        return c.getTime();
    }

    private boolean needSchedule(StockEvent e)
    {
        if (e.getStart()!=null && (!e.getEnd().after(e.getStart()))) {
            return false;
        }
        if (e.getEnd().before(getMinEnd())) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private void waitForExecutedJobGroup(Scheduler sched, String account)
            throws SchedulerException
    {
        boolean found;

        do {
            found = false;

            List<JobExecutionContext> jobs = sched.getCurrentlyExecutingJobs();
            for (JobExecutionContext ctx : jobs) {

                String group = ctx.getTrigger().getGroup();
                if (account.equals(group)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                // TODO: That is not very good but still no better ideas
                ThreadUtil.sleep(50);
            }
            
        } while (found);
    }
}
