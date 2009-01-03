package org.localstorm.mcc.ejb.gtd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.localstorm.mcc.ejb.dao.QueriesLoader;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public class GtdReportsDao {
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
            

            ps.setInt(1, u.getId());
            ps.setInt(2, u.getId());
            ps.setInt(3, u.getId());
            ps.setInt(4, u.getId());
            ps.setInt(5, u.getId());
            ps.setInt(6, u.getId());
            
            ResultSet rs = ps.executeQuery();

            DashboardReportBean drb = new DashboardReportBean();

            while (rs.next())
            {
                // Null is impossible here

                String ctxName = rs.getString("cname");
                int ctxId      = rs.getInt("cid");
                int total      = rs.getInt("total");
                int awaited    = rs.getInt("awaited");
                int flight     = rs.getInt("flight");
                int red        = rs.getInt("red");
                int dead       = rs.getInt("dead");

                // Interpreting
                DashboardReportRow row = new DashboardReportRow();
                {
                    row.setAwaited(awaited);
                    row.setTotal(total);
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
