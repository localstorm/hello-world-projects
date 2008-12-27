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
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/SubmitTaskSearch")
public class TaskSearchSubmitActionBean extends GtdBaseActionBean
{
    private boolean found;
    private String text;
    private List<Task> tasks;

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public List<Task> getOperativeTasks() {
        return tasks;
    }

    public List<Task> getAwaitedTasks() {
        return tasks;
    }

    public List<Task> getArchiveTasks() {
        return tasks;
    }

    public void setTasks(Collection<? extends Task> awaitedTasks) {
        this.tasks = new ArrayList<Task>(awaitedTasks);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @DefaultHandler
    public Resolution filling() throws Exception {
        FlightPlanManager fp = this.getFlightPlanManager();
        Collection<Task> awaited = this.getTaskManager().findAllAwaited(this.getUser());
        Collection<Task> fpt = fp.getTasksFromFlightPlan(fp.findCurrent(this.getUser()));

        FilterUtil.applyContextFilter(awaited, super.getContextIdFilter());
        this.setTasks(WrapUtil.genWrappers(awaited, fpt));

        this.setFound(true);
        return new ForwardResolution(Views.SEARCH_TASKS);
    }

    
}
