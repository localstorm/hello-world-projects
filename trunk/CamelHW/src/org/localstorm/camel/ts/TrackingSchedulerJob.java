package org.localstorm.camel.ts;

import org.localstorm.stocks.tracker.AnalyzerInstruction;
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

    public void execute(JobExecutionContext ctx) throws JobExecutionException
    {
        try {
            JobDetail   jd = ctx.getJobDetail();
            JobDataMap jdm = jd.getJobDataMap();

            AnalyzerInstruction ai = (AnalyzerInstruction) jdm.get(TrackingSchedulerEndpoint.ANALYZER_INSTRUCTION_KEY);
            TrackingSchedulerEndpoint   ep = (TrackingSchedulerEndpoint)   jdm.get(TrackingSchedulerEndpoint.SCHEDULER_ENDPOINT_KEY);

            ep.onJobExecute(ai);

            System.out.println("Triggered: "+ai);
        } catch(Exception e) {
            e.printStackTrace();
            //TODO: Log!
        }
    }

}
