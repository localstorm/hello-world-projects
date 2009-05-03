package org.localstorm.camel.analyzer;

import org.localstorm.stocks.tracker.*;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RulesModel 
{
    // Key: Symbol+'-'+StockChangeType (ex.: MSFT-RAISE)
    private final ConcurrentNavigableMap<String, ConcurrentLinkedQueue<Rule>> rules;


    public RulesModel() {
        rules = new ConcurrentSkipListMap<String, ConcurrentLinkedQueue<Rule>>();
    }

    public void addRule(String symbol, StockChangeType type, BigDecimal threshold, String account) {
        ConcurrentLinkedQueue<Rule> newQ = new ConcurrentLinkedQueue<Rule>();
        ConcurrentLinkedQueue<Rule> queue = this.rules.putIfAbsent(symbol+type, newQ);

        if (queue==null) {
            queue = newQ;
        }
        
        queue.add(new Rule(threshold, account));
    }

    public void removeRule(String symbol, StockChangeType type, BigDecimal threshold, String account) {
        ConcurrentLinkedQueue<Rule> queue = this.rules.get(symbol+type);
        if (queue!=null) {
            queue.remove(new Rule(threshold, account));
        }
    }

    // ------ Private rule class (impl details)

    private static final class Rule {
        private BigDecimal threshold;
        private String account;

        public Rule(BigDecimal threshold, String account) {
            //TODO: Guard not null!
            this.threshold = threshold;
            this.account   = account;
        }

        public String getAccount() {
            return account;
        }

        public BigDecimal getThreshold() {
            return threshold;
        }

        @Override
        public int hashCode()
        {
            return account.hashCode()+threshold.hashCode();
        }

        @Override
        public boolean equals(Object obj)
        {
            // TODO: is this correct?
            if (obj==null) {
                return false;
            }

            if (!(obj instanceof Rule)) {
                return false;
            }

            Rule o = (Rule) obj;
            return this.threshold.equals(o.threshold) &&
                   this.account.equals(o.account);
        }
    }

}
