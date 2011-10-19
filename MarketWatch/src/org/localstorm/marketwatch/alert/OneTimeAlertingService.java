package org.localstorm.marketwatch.alert;


import au.com.bytecode.opencsv.CSVReader;
import org.localstorm.marketwatch.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OneTimeAlertingService implements AlertingService {

    private Map<String, List<AlertingCondition>> conditions = new HashMap<String, List<AlertingCondition>>();
    private Alerting alerting;

    public OneTimeAlertingService(Alerting alerting) {
        this.alerting = alerting;
    }

    public void onPriceChange(Asset a) {
        List<AlertingCondition> conds = conditions.get(a.getName());
        if (conds == null) {
            return;
        }

        for (Iterator<AlertingCondition> it = conds.iterator(); it.hasNext();) {
            AlertingCondition c = it.next();
            double buy = a.getPrice().getBuy();
            double sell = a.getPrice().getSell();
            double spread = 2 * (buy - sell) / (buy + sell);
            if (buy <= c.getBuyLowerCap() && spread <= c.getSpreadCap()) {
                this.alerting.sendMessage(a.getName() + " price fall: " + buy + "<=" + c.getBuyLowerCap() + ", Spread: " + spread);
                it.remove();
                continue;
            }
            if (sell >= c.getSellHigherCap() && spread <= c.getSpreadCap()) {
                this.alerting.sendMessage(a.getName() + " price raise: " + sell + ">=" + c.getSellHigherCap()+", Spread: "+spread);
                it.remove();
                continue;
            }
        }
    }


    public void loadAlertingConditions(File f) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(f));

        try {
            String[] op = reader.readNext();

            while (op != null) {
                op = reader.readNext();
                if (op == null) {
                    break;
                }
                String assetName = op[0];
                double buyLo = Double.parseDouble(op[1]);
                double sellHi = Double.parseDouble(op[2]);
                double spread = Double.parseDouble(op[3]);
                List<AlertingCondition> cList = conditions.get(assetName);

                if (cList == null) {
                    cList = new ArrayList<AlertingCondition>();
                    conditions.put(assetName, cList);
                }

                cList.add(new AlertingCondition(buyLo, sellHi, spread));
            }
        } finally {
            reader.close();
        }
    }
}
