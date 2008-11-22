package org.localstorm.mcc.web.gtd.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/AwaitedReport")
public class AwaitingsReportActionBean extends BaseActionBean
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
        FlightPlanManager fp = this.getFlightPlanManager();
        Collection<Task> awaited = this.getTaskManager().findAllAwaited(this.getUser());
        Collection<Task> fpt = fp.getTasksFromFlightPlan(fp.findCurrent(this.getUser()));

        FilterUtil.applyContextFilter(awaited, super.getContextIdFilter());
        this.setAwaitedTasks(WrapUtil.genWrappers(awaited, fpt));
        
        return new ForwardResolution(Views.VIEW_AW);
    }

    
}
