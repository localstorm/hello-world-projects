package org.localstorm.mcc.ejb.gtd.dao;

/**
 *
 * @author localstorm
 */
public class DashboardReportRow {
    private String contextName;
    private int contextId;
    private int total;
    private int awaited;
    private int flightPlan;
    private int red;
    private int dead;

    public int getAwaited() {
        return awaited;
    }

    public int getContextId() {
        return contextId;
    }

    public String getContextName() {
        return contextName;
    }

    public int getDead() {
        return dead;
    }

    public int getFlightPlan() {
        return flightPlan;
    }

    public int getRed() {
        return red;
    }

    public int getTotal() {
        return total;
    }

    public void setAwaited(int awaited) {
        this.awaited = awaited;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public void setFlightPlan(int flightPlan) {
        this.flightPlan = flightPlan;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
