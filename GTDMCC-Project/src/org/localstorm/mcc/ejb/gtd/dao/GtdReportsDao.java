package org.localstorm.mcc.ejb.gtd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.dao.Guard;
import org.localstorm.mcc.ejb.dao.JdbcDaoHelper;
import org.localstorm.mcc.ejb.dao.PreparedStatementsCache;
import org.localstorm.mcc.ejb.dao.QueriesLoader;
import org.localstorm.mcc.ejb.users.User;

/**
 * @author localstorm
 */
public class GtdReportsDao {

    private static final Logger log = Logger.getLogger(GtdReportsDao.class);
    private static final PreparedStatementsCache cache = new PreparedStatementsCache();

    public static final String AWAITED_TASKS    = "awaited";
    public static final String CONTEXT_ID       = "cid";
    public static final String CONTEXT_NAME     = "cname";
    public static final String DEADLINE_TASK    = "dead";
    public static final String FLIGHT_PLAN_TASKS = "flight";
    public static final String REDLINE_TASKS    = "red";
    public static final String PENDING_TASKS    = "pending";
    public static final String DONE_TASKS       = "done";
    public static final String ELEMENTARY_TASKS = "effort1";
    public static final String EASY_TASKS       = "effort2";
    public static final String MEDIUM_TASKS     = "effort3";
    public static final String DIFFICULT_TASKS  = "effort4";
    public static final String VERY_DIFFICULT_TASKS  = "effort5";

    

    private DataSource ds;

    public GtdReportsDao(DataSource ds)
    {
        Guard.checkDataSourceNotNull(ds);
        this.ds = ds;
    }

    public DashboardReportBean getDashboardReport(User u) throws SQLException
    {

        QueriesLoader ql = QueriesLoader.getInstance();
        String    rptSql = ql.getQuery(QueriesLoader.GTD_DASHBOARD_REPORT);

        Connection conn = null;
        try {
            conn = ds.getConnection();
            Guard.checkConnectionNotNull(conn);


            PreparedStatement ps = conn.prepareStatement(rptSql);

            for (int i=1; i<=12; i++) {
                JdbcDaoHelper.setInteger(ps, i, u.getId());
            }

            ResultSet rs = ps.executeQuery();

            DashboardReportBean drb = new DashboardReportBean();

            while (rs.next())
            {
                // Null is impossible here

                String ctxName = JdbcDaoHelper.getString(rs, CONTEXT_NAME);
                int ctxId      = JdbcDaoHelper.getInteger(rs, CONTEXT_ID);
                int pending    = JdbcDaoHelper.getInteger(rs, PENDING_TASKS);
                int awaited    = JdbcDaoHelper.getInteger(rs, AWAITED_TASKS);
                int flight     = JdbcDaoHelper.getInteger(rs, FLIGHT_PLAN_TASKS);
                int red        = JdbcDaoHelper.getInteger(rs, REDLINE_TASKS);
                int dead       = JdbcDaoHelper.getInteger(rs, DEADLINE_TASK);
                int done       = JdbcDaoHelper.getInteger(rs, DONE_TASKS);
                int elementary = JdbcDaoHelper.getInteger(rs, ELEMENTARY_TASKS);
                int easy       = JdbcDaoHelper.getInteger(rs, EASY_TASKS);
                int medium     = JdbcDaoHelper.getInteger(rs, MEDIUM_TASKS);
                int difficult  = JdbcDaoHelper.getInteger(rs, DIFFICULT_TASKS);
                int vd         = JdbcDaoHelper.getInteger(rs, VERY_DIFFICULT_TASKS);

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
                    row.setDone(done);
                    row.setEasy(easy);
                    row.setElementary(elementary);
                    row.setMedium(medium);
                    row.setDifficult(difficult);
                    row.setVeryDifficult(vd);
                }

                drb.addReportRow(row);
            }
            
            return drb;
        } finally {
            JdbcDaoHelper.safeClose(conn, log);
        }
    }
}
