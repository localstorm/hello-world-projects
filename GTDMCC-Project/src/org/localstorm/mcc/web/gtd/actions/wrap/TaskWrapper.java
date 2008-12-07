package org.localstorm.mcc.web.gtd.actions.wrap;

import java.util.Date;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.web.util.EscapeUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TaskWrapper extends Task
{
    private Task t;
    private boolean inFlightPlan;
    
    public TaskWrapper(Task t) {
        this.t = t;
    }

    public TaskWrapper(Task t, boolean inFp) {
        this(t);
        this.setInFlightPlan(inFp);
    }

    public boolean isInFlightPlan() {
        return inFlightPlan;
    }

    public void setInFlightPlan(boolean inFlightPlan) {
        this.inFlightPlan = inFlightPlan;
    }

    @Override
    public Date getCreation() {
        return t.getCreation();
    }

    @Override
    public Date getDeadline() {
        return t.getDeadline();
    }

    @Override
    public String getDetails() {
        return t.getDetails();
    }

    @Override
    public Integer getId() {
        return t.getId();
    }

    @Override
    public GTDList getList() {
        return t.getList();
    }

    @Override
    public Date getRedline() {
        return t.getRedline();
    }

    @Override
    public String getRuntimeNote() {
        return t.getRuntimeNote();
    }

    @Override
    public Integer getSortOrder() {
        return t.getSortOrder();
    }

    @Override
    public String getSummary() {
        return t.getSummary();
    }

    @Override
    public int getEffort() {
        return t.getEffort();
    }

    
    @Override
    public boolean isAwaited() {
        return t.isAwaited();
    }

    @Override
    public boolean isCancelled() {
        return t.isCancelled();
    }

    @Override
    public boolean isDelegated() {
        return t.isDelegated();
    }

    @Override
    public boolean isFinished() {
        return t.isFinished();
    }

    @Override
    public boolean isPaused() {
        return t.isPaused();
    }

    @Override
    public void setAwaited(boolean started) {
        t.setAwaited(started);
    }

    @Override
    public void setCancelled(boolean cancelled) {
        t.setCancelled(cancelled);
    }

    @Override
    public void setCreation(Date creation) {
        t.setCreation(creation);
    }

    @Override
    public void setDeadline(Date deadline) {
        t.setDeadline(deadline);
    }

    @Override
    public void setDelegated(boolean delegated) {
        t.setDelegated(delegated);
    }

    @Override
    public void setDetails(String details) {
        t.setDetails(details);
    }

    @Override
    public void setFinished(boolean accomplished) {
        t.setFinished(accomplished);
    }

    @Override
    public void setList(GTDList list) {
        t.setList(list);
    }

    @Override
    public void setPaused(boolean paused) {
        t.setPaused(paused);
    }

    @Override
    public void setRedline(Date redline) {
        t.setRedline(redline);
    }

    @Override
    public void setRuntimeNote(String runtimeNote) {
        t.setRuntimeNote(runtimeNote);
    }

    @Override
    public void setSortOrder(Integer sortOrder) {
        t.setSortOrder(sortOrder);
    }

    @Override
    public void setSummary(String summary) {
        t.setSummary(summary);
    }

    @Override
    public void setEffort(int effort) {
        t.setEffort(effort);
    }

    public String getDetailsHtmlEscaped()
    {
        return EscapeUtil.forHTML(this.getDetails());
    }

    
}
