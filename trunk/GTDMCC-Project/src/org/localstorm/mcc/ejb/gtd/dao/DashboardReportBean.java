package org.localstorm.mcc.ejb.gtd.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author localstorm
 */
public class DashboardReportBean {

    private List<DashboardReportRow> rows;
    private DashboardReportRow totals;

    public DashboardReportBean()
    {
        this.rows   = new LinkedList<DashboardReportRow>();
        this.totals = new DashboardReportRow();
    }

    public List<DashboardReportRow> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public DashboardReportRow getTotals() {
        return this.totals;
    }

    public void addReportRow(DashboardReportRow row) {
        totals.setPending(totals.getPending()+row.getPending());
        totals.setAwaited(totals.getAwaited()+row.getAwaited());
        totals.setFlightPlan(totals.getFlightPlan()+row.getFlightPlan());
        totals.setRed(totals.getRed()+row.getRed());
        totals.setDead(totals.getDead()+row.getDead());
        rows.add(row);
    }
    
}
