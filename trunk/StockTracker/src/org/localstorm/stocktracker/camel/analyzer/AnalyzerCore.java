package org.localstorm.stocktracker.camel.analyzer;

import org.localstorm.stocktracker.exchange.*;
import java.math.BigDecimal;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.localstorm.stocktracker.util.misc.Guard;

/**
 * Analyzer algorithm and data is located here
 * @author Alexey Kuznetsov
 */
class AnalyzerCore
{
    // Key: Symbol+'#'+StockEventType (ex.: MSFT-RAISE)
    private final ConcurrentNavigableMap<String, ConcurrentLinkedQueue<Rule>> rules;


    public AnalyzerCore() {
        rules = new ConcurrentSkipListMap<String, ConcurrentLinkedQueue<Rule>>();
    }

    /**
    * Appends new rule to analyzer active rule set
    */
    public void addRule(String symbol, StockEventType type, BigDecimal threshold, String account) {
        ConcurrentLinkedQueue<Rule> newQ = new ConcurrentLinkedQueue<Rule>();
        ConcurrentLinkedQueue<Rule> queue = this.rules.putIfAbsent(getKey(symbol, type), newQ);

        if (queue==null) {
            queue = newQ;
        }
        
        queue.add(new Rule(threshold, account));
    }
    
    /**
    * Removes rule from analyzer active rule set.
    * Does nothing if such rule doesn't exist
    */
    public void removeRule(String symbol, StockEventType type, BigDecimal threshold, String account) {
        ConcurrentLinkedQueue<Rule> queue = this.rules.get(getKey(symbol, type));
        if (queue!=null) {
            queue.remove(new Rule(threshold, account));
        }
    }

    /**
    * Returns all fired notifications for given stock event
    */
    @SuppressWarnings("unchecked")
    public NotificationsChunk getFiredNotifications(StockPrice sp) {
        
        StockEventType type   = sp.getType();
        BigDecimal    price   = sp.getPrice();
        Queue<Rule>   toCheck = this.rules.get(getKey(sp.getSymbol(), type));

        NotificationsChunk chunk = new NotificationsChunk();

        if (toCheck==null || toCheck.isEmpty()) {
            return chunk;
        }
        
        switch (type) {
            case RAISE:
                for (Rule rule: toCheck) {
                    if (rule.getThreshold().compareTo(price)<=0) {
                        this.appendNotification(chunk, rule, StockEventType.RAISE, sp);
                    }
                }
                break;
            case FALL:
                for (Rule rule: toCheck) {
                    if (rule.getThreshold().compareTo(price)>=0) {
                        this.appendNotification(chunk, rule, StockEventType.FALL, sp);
                    }
                }
                break;
            default:
                throw new RuntimeException("Unexpected case: "+type);
        }

        return chunk;
    }


    // Private ---------
    
    private void appendNotification(NotificationsChunk chunk, Rule rule, StockEventType type, StockPrice sp) {
        NotificationFire ntf = new NotificationFire(rule.getAccount(),
                                                    sp.getSymbol(), 
                                                    type,
                                                    rule.getThreshold(),
                                                    sp.getPrice());
        chunk.addNote(ntf);
    }


    private String getKey(String symbol, StockEventType type) {
        String t = type.toString();

        StringBuilder sb = new StringBuilder(symbol.length()+t.length()+1);
        sb.append(symbol);
        sb.append('#');
        sb.append(type);

        return sb.toString();
    }


    // ------ Private rule class (impl details)

    private static final class Rule {
        private BigDecimal threshold;
        private String account;

        public Rule(BigDecimal threshold, String account) {
            Guard.checkNotNull(threshold, NullPointerException.class, "Threshold is null!");
            Guard.checkNotNull(account,   NullPointerException.class, "Account is null!");

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
