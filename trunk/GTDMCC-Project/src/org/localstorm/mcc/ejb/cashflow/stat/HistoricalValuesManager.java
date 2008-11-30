package org.localstorm.mcc.ejb.cashflow.stat;

import java.util.Collection;
import org.localstorm.mcc.ejb.users.User;


/**
 *
 * @author localstorm
 */

public interface HistoricalValuesManager
{
    public static final String BEAN_NAME="HistoricalValuesManagerBean";

    public void log(HistoricalValue hv);

    public Collection<HistoricalValue> findByValueTag(String valueTag, User user);

    public Collection<HistoricalValue> findByValueTagAndObjectId(String valueTag, Integer objectId, User user);
}
