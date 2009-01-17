package org.localstorm.mcc.ejb.gtd.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.localstorm.mcc.ejb.AbstractEntity;

/**
 *
 * @author localstorm
 */
public class DashboardReportBean extends AbstractEntity {

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
        totals.setDone(totals.getDone()+row.getDone());
        totals.setElementary(totals.getElementary()+row.getElementary());
        totals.setEasy(totals.getEasy()+row.getEasy());
        totals.setMedium(totals.getMedium()+row.getMedium());
        totals.setDifficult(totals.getDifficult()+row.getDifficult());
        totals.setVeryDifficult(totals.getVeryDifficult()+row.getVeryDifficult());
        rows.add(row);
    }
    
}
