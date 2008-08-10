package org.localstorm.mcc.ejb.flight;

import java.util.Collection;
import org.localstorm.mcc.ejb.BaseSingletonManager;
import org.localstorm.mcc.ejb.tasks.Task;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface FlightPlanManager extends BaseSingletonManager<FlightPlan, User>
{
    public static final String BEAN_NAME = "FlightPlanManagerBean";
    
    public void addTaskToFlightPlan(Task t, FlightPlan fp);
    public void removeTaskFromFlightPlan(Task t, FlightPlan fp);
    public Collection<Task> getTasksFromFlightPlan(FlightPlan fp);
}
