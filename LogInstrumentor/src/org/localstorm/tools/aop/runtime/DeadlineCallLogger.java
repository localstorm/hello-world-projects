package org.localstorm.tools.aop.runtime;


/**
 * @author Alexey Kuznetsov
 */
public class DeadlineCallLogger
{
    private static final DeadlineCallLogger ctl = new DeadlineCallLogger();
    private static final String DEADLINE_CALL_PROCESSOR_PROPERTY="org.localstorm.tools.aop.deadline";

    private DeadlineCallProcessor dcp;

    private DeadlineCallLogger()
    {
        try {

            String dcpClass = System.getProperty(DEADLINE_CALL_PROCESSOR_PROPERTY);
            if (dcpClass!=null) {
                Class c = Class.forName(dcpClass);
                Object o = c.newInstance();
                if (o instanceof DeadlineCallProcessor) {
                    this.dcp = (DeadlineCallProcessor) o;
                } else {
                    System.err.println("Specified DeadlineCallProcessor ["+dcpClass+"] is invalid!");
                    this.dcp = new DefaultDcp();
                }
            } else {
                System.err.println("DeadlineCallProcessor was not specified. Using default implementation...");
                this.dcp = new DefaultDcp();
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println("DeadlineCallProcessor wasn't initiallized. Using default implementation");
            this.dcp = new DefaultDcp();
        }
    }

    public static DeadlineCallLogger getInstance() {
        return ctl;
    }
            
    public void logTime(String className, String methodName, long deadline, long actualTime) {
        this.dcp.logTime(className, methodName, deadline, actualTime);
    }

    public DeadlineCallProcessor getProcessor() {
        return this.dcp;
    }

    private static final class DefaultDcp implements DeadlineCallProcessor {
        @Override
        public synchronized void logTime(String className, String methodName, long deadline, long actualTime) {
            System.out.println("Deadline: "+className+"."+methodName+" ("+actualTime+" ms.) but "+deadline+" ms. expected!");
        }
    }
}
