package org.localstorm.mcc.ejb.gtd.agent;

import java.util.Collection;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.entity.FlightPlan;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class FlightCommandHandler implements CommandHandler
{

    @Override
    public String handle(int uid, String from, String to, String param)
    {
        FlightPlanManager fpm = ContextLookup.lookup(FlightPlanManager.class, FlightPlanManager.BEAN_NAME);
        UserManager   um = ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);

        User owner = um.findById(uid);
        FlightPlan fp = fpm.findByUser(owner);
        
        return this.buildResponse(fpm.getTasksFromFlightPlan(fp));
    }

    private String buildResponse(Collection<Task> tasks)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("--- FLIGHT PLAN tasks ---\n");
        for (Task t: tasks)
        {
            if (t.isCancelled() || t.isFinished())
            {
                continue;
            }
            sb.append('[');
            sb.append(t.getId());
            sb.append("] ");
            sb.append(t.getSummary());
            sb.append('\n');

            String rn = t.getRuntimeNote();
            if (rn!=null && rn.length()>0)
            {
                sb.append(rn);
                sb.append('\n');
            }
            sb.append("--------------------\n");
        }

        return sb.toString();
    }

    
}