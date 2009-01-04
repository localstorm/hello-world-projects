package org.localstorm.mcc.ejb.gtd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.localstorm.mcc.ejb.dao.QueriesLoader;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public class GtdReportsDao {
    public static final String AWAITED_TASKS    = "awaited";
    public static final String CONTEXT_ID       = "cid";
    public static final String CONTEXT_NAME     = "cname";
    public static final String DEADLINE_TASK    = "dead";
    public static final String FLIGHT_PLAN_TASKS = "flight";
    public static final String REDLINE_TASKS    = "red";
    public static final String PENDING_TASKS    = "pending";

    private DataSource ds;

    public GtdReportsDao(DataSource ds)
    {
        this.ds = ds;
        if (ds==null)
        {
            throw new NullPointerException("Given DataSource is null!");
        }
    }

    public DashboardReportBean getDashboardReport(User u) throws SQLException
    {
        QueriesLoader ql = QueriesLoader.getInstance();
        String    rptSql = ql.getQuery(QueriesLoader.GTD_DASHBOARD_REPORT);

        Connection conn = null;
        try {
            conn = ds.getConnection();

            if (conn==null) {
                throw new SQLException("Can't obtain a connection.");
            }

            PreparedStatement ps = conn.prepareStatement(rptSql);

            for (int i=1; i<=6; i++) {
                ps.setInt(i, u.getId());
            }

            ResultSet rs = ps.executeQuery();

            DashboardReportBean drb = new DashboardReportBean();

            while (rs.next())
            {
                // Null is impossible here

                String ctxName = rs.getString(CONTEXT_NAME);
                int ctxId      = rs.getInt(CONTEXT_ID);
                int pending    = rs.getInt(PENDING_TASKS);
                int awaited    = rs.getInt(AWAITED_TASKS);
                int flight     = rs.getInt(FLIGHT_PLAN_TASKS);
                int red        = rs.getInt(REDLINE_TASKS);
                int dead       = rs.getInt(DEADLINE_TASK);

                // Interpreting
                DashboardReportRow row = new DashboardReportRow();
                {
                    row.setAwaited(awaited);
                    row.setPending(pending);
                    row.setContextName(ctxName);
                    row.setContextId(ctxId);
                    row.setFlightPlan(flight);
                    row.setRed(red);
                    row.setDead(dead);
                }

                drb.addReportRow(row);
            }

            return drb;
        } finally {
            if (conn!=null)
            {
                conn.close();
            }
        }
    }
}
