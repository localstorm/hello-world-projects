package org.localstorm.stocktracker.camel.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.localstorm.stocktracker.exchange.AnalyzerInstruction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TrackingSchedulerJob implements Job {

    private static final Log log = LogFactory.getLog(TrackingSchedulerJob.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException
    {
        try {
            JobDetail   jd = ctx.getJobDetail();
            JobDataMap jdm = jd.getJobDataMap();

            AnalyzerInstruction ai = (AnalyzerInstruction) jdm.get(TrackingSchedulerEndpoint.ANALYZER_INSTRUCTION_KEY);
            TrackingSchedulerEndpoint   ep = (TrackingSchedulerEndpoint)   jdm.get(TrackingSchedulerEndpoint.SCHEDULER_ENDPOINT_KEY);

            ep.onJobExecute(ai);
        } catch(Exception e) {
            log.error(e);
        }
    }

}
