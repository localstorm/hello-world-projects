/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package quartzhw;

import java.text.ParseException;
import java.util.Date;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

/**
 *
 * @author localstorm
 */
public class Main {

    public static final class MyJob implements Job
    {
        public MyJob()
        {
        }
        
        public void execute(JobExecutionContext arg0)
                throws JobExecutionException
        {
            System.out.println(arg0.getTrigger().getName()+": Yo-yo!");
        }
        
    }
    
    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SchedulerException, ParseException 
    {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();
        
        sched.start();

        JobDetail jobDetail = new JobDetail("myJob", null, MyJob.class);

        // fire every hour
        Trigger trigger = new CronTrigger("cronTrigger", "cronGroup", "0/15/30/45 * * * * ?");
        
        // start on the next even hour
        trigger.setStartTime(TriggerUtils.getEvenMinuteDate(new Date()));  
        trigger.setName("myTrigger");

        sched.scheduleJob(jobDetail, trigger);
    }

}
