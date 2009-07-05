package org.localstorm.mcc.ejb.gtd.agent;

import java.util.Collection;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.dao.FiredHintsReportBean;
import org.localstorm.mcc.ejb.gtd.entity.FlightPlan;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class AgendaCommandHandler implements CommandHandler
{

    @Override
    public String handle(int uid, String from, String to, String message)
    {
        FlightPlanManager fpm = ContextLookup.lookup(FlightPlanManager.class, FlightPlanManager.BEAN_NAME);
        HintManager   hm = ContextLookup.lookup(HintManager.class, HintManager.BEAN_NAME);
        UserManager   um = ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);

        User owner = um.findById(uid);
        FiredHintsReportBean fhrb = hm.getFiredHintsReport(owner);
        FlightPlan fp = fpm.findByUser(owner);

        
        return this.buildResponse(fhrb, fpm.getTasksFromFlightPlan(fp));
    }

    private String buildResponse(FiredHintsReportBean fhrb, Collection<Task> tasks)
    {
        Collection<FiredHintsReportBean.TaskStub> fired = fhrb.getFired();
        StringBuilder sb = new StringBuilder();

        sb.append("--- HINTED tasks ---\n");
        for (FiredHintsReportBean.TaskStub ts: fired)
        {
            sb.append('[');
            sb.append(ts.getId());
            sb.append("] ");
            sb.append(ts.getSummary());
            sb.append('\n');
            sb.append("--------------------\n");
        }

        sb.append("--- FLIGHT PLAN tasks ---\n");
        for (Task t: tasks)
        {
            sb.append('[');
            sb.append(t.getId());
            sb.append("] ");
            sb.append(t.getSummary());
            sb.append('\n');
            sb.append("--------------------\n");
        }

        return sb.toString();
    }

    
}
