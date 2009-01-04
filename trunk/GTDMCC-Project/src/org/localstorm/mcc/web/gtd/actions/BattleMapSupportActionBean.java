package org.localstorm.mcc.web.gtd.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.contexts.Context;
import org.localstorm.mcc.ejb.gtd.contexts.ContextManager;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/BattleMapSupport")
public class BattleMapSupportActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private Integer contextId;

    @Validate( required=true )
    private String filter;

    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<? extends Task> tasks) {
        this.tasks = new ArrayList<Task>(tasks);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getContextId() {
        return contextId;
    }

    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }


    @DefaultHandler
    public Resolution filling() throws Exception {

        User            user = super.getUser();
        ContextManager    cm = super.getContextManager();
        TaskManager       tm = super.getTaskManager();
        FlightPlanManager fp = super.getFlightPlanManager();

        Filter    f = Filter.valueOf(this.getFilter());
        Context ctx = cm.findById(this.getContextId());

        Collection<Task> _tasks = null;

        Collection<Task> fpt = fp.getTasksFromFlightPlan(fp.findCurrent(user));

        switch( f ) {
            case ALL:
                _tasks = tm.findUnfinished(user, ctx);
                break;
            case AWAITED:
                _tasks = tm.findAwaited(user, ctx);
                break;
            case REDLINE:
                _tasks = tm.findRedlinedTasks(user, ctx);
                break;
            case DEADLINE:
                _tasks = tm.findDeadlinedTasks(user, ctx);
                break;
            case FLIGHT:
                _tasks = new ArrayList<Task>(fpt.size());
                _tasks.addAll(fpt);
                FilterUtil.applyContextFilter(_tasks, contextId);
                break;
            default:
                throw new RuntimeException("Unexpected case!");
        }

        this.setTasks(WrapUtil.genWrappers(_tasks, fpt));

        ReturnPageBean rpb = new ReturnPageBean(Pages.BMS_VIEW.toString());
        {
            rpb.setParam(InputParameters.CTX,    this.getContextId().toString());
            rpb.setParam(InputParameters.FILTER, this.getFilter());
        }

        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.VIEW_BMS);
    }

    private static interface InputParameters
    {
        public static final String CTX    = "contextId";
        public static final String FILTER = "filter";
    }

    private static enum Filter
    {
        ALL,
        AWAITED,
        FLIGHT,
        REDLINE,
        DEADLINE
    }
}
