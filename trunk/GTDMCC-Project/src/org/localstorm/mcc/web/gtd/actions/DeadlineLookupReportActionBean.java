package org.localstorm.mcc.web.gtd.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlan;
import org.localstorm.mcc.ejb.gtd.flight.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.gtd.RequestAttributes;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.TaskMarker;
import org.localstorm.mcc.web.gtd.actions.wrap.TaskWrapper;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/DeadlineLookupReport")
public class DeadlineLookupReportActionBean extends GtdBaseActionBean
{
    private Date today;
    private List<TaskMarker> broken;
    private List<TaskMarker> following;

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public Collection<TaskMarker> getBroken() {
        return broken;
    }

    public void setBroken(List<TaskMarker> broken) {
        this.broken = broken;
    }

    public Collection<TaskMarker> getFollowing() {
        return following;
    }

    public void setFollowing(List<TaskMarker> following) {
        this.following = following;
    }


    @DefaultHandler
    public Resolution filling() throws Exception {

        this.setToday(new Date());
        this.setBroken(new LinkedList<TaskMarker>());
        this.setFollowing(new LinkedList<TaskMarker>());

        TaskManager tm = super.getTaskManager();
        FlightPlanManager fpm = super.getFlightPlanManager();

        User user = super.getUser();

        FlightPlan fp = fpm.findCurrent(user);
        Collection<Task> fpt   = fpm.getTasksFromFlightPlan(fp);
        Collection<Task> tasks = tm.findScheduledNonFinishedTasks(user);

        FilterUtil.applyContextFilter(tasks, super.getContextIdFilter());

        Collection<TaskWrapper> wrapped = WrapUtil.genWrappers(tasks, fpt);

        for (Task t: wrapped) {

            TaskMarker tmarker = new TaskMarker(t, today);
            if (tmarker.getRedCrossed()!=null) {
                broken.add(tmarker);
            }
            if (tmarker.getRedNonCrossed()!=null) {
                following.add(tmarker);
            }
        }

        this.orderByRemains(broken, true);
        this.orderByRemains(following, false);

        this.setAffectedContextsByMarkers(this.broken,
                                          this.following);
        
        return new ForwardResolution(Views.VIEW_DLR);
    }

    private void orderByRemains(List<TaskMarker> markers, final boolean useCrossed) {
        Comparator<TaskMarker> cmp = new Comparator<TaskMarker>() {

            @Override
            public int compare(TaskMarker o1, TaskMarker o2) {
                if (useCrossed) {
                    Integer r1 = o1.getTimeRemainsCrossed();
                    Integer r2 = o2.getTimeRemainsCrossed();
                    return r1.compareTo(r2);
                } else {
                    Integer r1 = o1.getTimeRemainsNonCrossed();
                    Integer r2 = o2.getTimeRemainsNonCrossed();
                    return r1.compareTo(r2);
                }
            }
        };

        Collections.sort(markers, cmp);
    }

    public void setAffectedContextsByMarkers(Collection<TaskMarker> ... cols) {
        Integer ctxId = this.getContextIdFilter();
        HttpServletRequest req = this.getContext().getRequest();

        Map<Integer, Boolean> affectedContexts = new HashMap<Integer, Boolean>();
        {
            if (ctxId > 0) {
                affectedContexts.put(ctxId, Boolean.TRUE);
            } else {
                for (Collection<TaskMarker> col: cols) {
                    this.appendAffectedCtxs(col, affectedContexts);
                }
                if (affectedContexts.isEmpty()) {
                    affectedContexts.put(-1, Boolean.FALSE);
                }
            }
        }

        req.setAttribute(RequestAttributes.AFFECTED_CONTEXTS, affectedContexts);
    }

    private void appendAffectedCtxs(Collection<TaskMarker> tasks, Map<Integer, Boolean> affectedContexts) {
        for (TaskMarker tm : tasks) {
            affectedContexts.put(tm.getTask().getList().getContext().getId(), Boolean.TRUE);
        }
    }

   
}