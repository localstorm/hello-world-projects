package org.localstorm.camel.ts;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.quartz.QuartzEndpoint;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.localstorm.camel.util.Sequence;
import org.localstorm.camel.util.EndpointUtil;
import org.localstorm.camel.util.ProcessUtil;
import org.localstorm.stocks.tracker.AnalyzerInstruction;
import org.localstorm.stocks.tracker.StockEvent;
import org.localstorm.stocks.tracker.StockTrackingRequest;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

/**
 * @in StockTrackingRequest instance
 * @out AnalyzerInstruction instance
 * @author Alexey Kuznetsov
 */
public class TrackingSchedulerEndpoint extends DefaultEndpoint<DefaultExchange>
{
    public static final String ANALYZER_INSTRUCTION_KEY = "$aInstruction";
    public static final String   SCHEDULER_ENDPOINT_KEY = "$schedulerEp";

    private List<DefaultConsumer> consumers = new CopyOnWriteArrayList<DefaultConsumer>();
    private Sequence seq = new Sequence();

    public TrackingSchedulerEndpoint(String endpointUri,
                             TrackingSchedulerComponent component) {
        super(endpointUri, component);
    }

    public TrackingSchedulerEndpoint(String endpointUri, Object some) {
        super(endpointUri);
    }

    public Consumer<DefaultExchange> createConsumer(Processor p) throws Exception {

        return new DefaultConsumer<DefaultExchange>(this, p) {

            @Override
            public void start() throws Exception {
                consumers.add(this);
                super.start();
            }

            @Override
            public void stop() throws Exception {
                super.stop();
                consumers.remove(this);
            }
        };
    }

    public Producer<DefaultExchange> createProducer() throws Exception {
        return new TrackingSchedulerProducer(this);
    }

    public boolean isSingleton() {
        return true;
    }

    // Some business -----------------------

    /* package */ void scheduleTracking(StockTrackingRequest str) {
        
        String account = str.getAccount();

        // TODO: Check str.size() here (configurable)

        for (StockEvent e: str.getWatchList()) {

            if (this.needSchedule(e)) {
                this.scheduleTrackingEvents(account, e);
            }
   
        }
    }

    /*package*/ void onJobExecute(AnalyzerInstruction ai) throws Exception
    {
        System.out.println("Resending: "+ai);
        ProcessUtil.process(ai, this.getCamelContext(), consumers);
    }


    /*package*/ Collection<DefaultConsumer> getConsumers() {
        return Collections.unmodifiableCollection(consumers);
    }


    // Private: -----------------------

    private void scheduleTrackingEvents(String account, StockEvent e) {

        QuartzEndpoint       endEp = EndpointUtil.getEndpoint(this, generateQuartzUri(account));
        SimpleTrigger   endTrigger = (SimpleTrigger) endEp.getTrigger();

        QuartzEndpoint     startEp = EndpointUtil.getEndpoint(this, generateQuartzUri(account));
        SimpleTrigger startTrigger = (SimpleTrigger) startEp.getTrigger();

        Date end = e.getEnd();
        Date reqStart = e.getStart();
        Date start = (reqStart!=null) ? reqStart : immediately();

        this.configureTrigger(endTrigger,   end);
        this.configureTrigger(startTrigger, start);

        JobDetail   endJob = this.generateJodDetail(account, e, true);
        JobDetail startJob = this.generateJodDetail(account, e, false);

        try {
            endEp.addTrigger(endTrigger, endJob);
        } catch(SchedulerException ex) {
            ex.printStackTrace();
            // TODO: log here, exception here!
            // Can't schedule termination -- aborting...
            return;
        }

        try {
            startEp.addTrigger(startTrigger, startJob);
        } catch(SchedulerException ex) {
            ex.printStackTrace();
            cancelQuietly(endEp, endTrigger,endJob);
            // Can't schedule start -- cancelling end job
        }

    }

    private void cancelQuietly(QuartzEndpoint endpoint, SimpleTrigger t, JobDetail j) {
        try {
            endpoint.removeTrigger(t, j);
        } catch(SchedulerException e) {
            // TODO: log here
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

        sb.append(')');

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

    private String generateQuartzUri(String account) {
        return "quartz://" + account + "/" + generateTriggerName();
    }

    private String generateTriggerName() {
        return System.currentTimeMillis()+"-"+seq.next();
    }

    private Date getMinEnd() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 5); // TODO: configure this param
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
}
