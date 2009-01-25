package org.localstorm.mcc.ejb.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Cache for prepared statements.
 * Is <i>not</i> thread-save.
 */
public final class PreparedStatementsCache
{
    private static final Logger log = Logger.getLogger(PreparedStatementsCache.class);
    
    private static final int MAX_STATEMENTS_MAP_SIZE = 1000;    

    // Map< key=tableName, value=Map< key=queryName, value=PreparedStatement > > 
    private final Map statementsCache;

    public PreparedStatementsCache()
    {
        this.statementsCache = new HashMap();
    }

    public PreparedStatement getStatement(String tableName,
                                          String queryName)
    {
        assert (tableName != null && queryName != null);
        final Map map = (Map) this.statementsCache.get(tableName);

        if (map == null)
        {
            return null;
        }

        PreparedStatement stmt = (PreparedStatement) map.get(queryName);

        if (stmt != null)
        {
            try
            {
                stmt.clearParameters();
            } catch (final SQLException e)
            {
                try
                {
                    stmt.close();
                } catch (final SQLException e1)
                {
                    // Nothing needed: statement will be removed from the cache and will not be returned
                }

                PreparedStatementsCache.log.warn("Can't clear parameters for prepared statement", e);
                map.remove(queryName);
                stmt = null;
            }
        }

        return stmt;
    }

    public void putStatement(String tableName,
                             String queryName,
                             PreparedStatement stmt)
    {
        assert (tableName != null && queryName != null && stmt != null);
        Map map = (Map) this.statementsCache.get(tableName);

        if (map == null)
        {
            map = new HashMap();
            this.statementsCache.put(tableName, map);
        }

        if ( map.size()<PreparedStatementsCache.MAX_STATEMENTS_MAP_SIZE )
        {
            map.put(queryName, stmt);
        }
    }

    public void clearAllStatements()
    {
        boolean wasError = false; // To prevent dublicating warning messages

        for (Iterator itc =  this.statementsCache.entrySet().iterator(); itc.hasNext(); )
        {
            Map.Entry mapEntry  = (Map.Entry) itc.next();
            assert (mapEntry.getValue() != null);

            for (Iterator it = ((Map) mapEntry.getValue()).entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry) it.next();
                assert (entry.getValue() != null);
                
                PreparedStatement stmt = (PreparedStatement) entry.getValue();

                try
                {
                    stmt.close();
                } catch (final SQLException e)
                {
                    if (!wasError)
                    {
                        PreparedStatementsCache.log.warn("Can't release prepared statements", e);
                        wasError = true; // To prevent dublicating warning messages
                    }
                }
            }
        }

        this.statementsCache.clear();
    }
}
