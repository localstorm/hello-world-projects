package org.localstorm.mcc.ejb.cashflow.impl;

import org.localstorm.mcc.ejb.cashflow.Price;
import org.localstorm.mcc.ejb.cashflow.impl.ticker.connector.JointConnector;

import javax.ejb.Stateless;
import java.util.Map;

/**
 *
 * @author localstorm
 */
@Stateless
public class PriceTickerManagerBean implements PriceTickerManagerLocal
{

    public PriceTickerManagerBean() {

    }

    @Override
    public Map<String, Price> getCurrentPrices() throws Exception {
        JointConnector jc = new JointConnector();
        return jc.fetch();
    }
}
