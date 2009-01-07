package org.localstorm.mcc.ejb.cashflow.stat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import org.localstorm.mcc.ejb.users.User;


/**
 *
 * @author localstorm
 */

public interface HistoricalValuesManager
{
    public static final String BEAN_NAME="HistoricalValuesManagerBean";

    public HistoricalValue findLastByValueTag(ValueType vt,
                                              BigDecimal defaultValue,
                                              User user);

    public void log(HistoricalValue hv);

    public Collection<HistoricalValue> findByValueTag(ValueType valueTag,
                                                      User user,
                                                      Date minDate);

    public Collection<HistoricalValue> findByValueTagAndObjectId(ValueType valueTag, 
                                                                 Integer objectId,
                                                                 User user,
                                                                 Date minDate);
}