package org.localstorm.mcc.web.gtd.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AwaitedReport")
public class AwaitingsReportActionBean extends GtdBaseActionBean
{

    private List<Task> awaitedTasks;

    public List<Task> getAwaitedTasks() {
        return awaitedTasks;
    }

    public void setAwaitedTasks(Collection<? extends Task> awaitedTasks) {
        this.awaitedTasks = new ArrayList<Task>(awaitedTasks);
    }


    @DefaultHandler
    public Resolution filling() throws Exception {

        User user = super.getUser();

        FlightPlanManager fp = super.getFlightPlanManager();
        Collection<Task> awaited = super.getTaskManager().findAllAwaited(user);
        Collection<Task> fpt = fp.getTasksFromFlightPlan(fp.findCurrent(user));

        FilterUtil.applyContextFilter(awaited, super.getContextIdFilter());
        this.setAwaitedTasks(WrapUtil.genWrappers(awaited, fpt));

        super.setAffectedContexts(this.getAwaitedTasks());
        
        return new ForwardResolution(Views.VIEW_AW);
    }

    
}
