package org.localstorm.marketwatch.pricing;

import org.localstorm.marketwatch.Price;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class NomosSource implements MarketSignalSource {
    private Queue<MarketSignal> signals;
    private String silver;
    private String gold;

    public NomosSource(String silverAsset, String goldAsset) {
        this.silver = silverAsset;
        this.gold = goldAsset;
        this.signals = new LinkedList<MarketSignal>();
    }

    public MarketSignal readSignal() throws Exception {
        if (!signals.isEmpty()) {
            return signals.poll();
        }

        try {
            File file = File.createTempFile("nomos", "prices");

            Html2XmlUrlConnector c = new Html2XmlUrlConnector("http://www.nomos.ru/", file);
            c.fetch();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            String silverSellEx = "/HTML/BODY/DIV[1]/DIV[3]/TABLE[2]/TBODY/TR[last()-2]/TD[2]/text()";
            String silverBuyEx = "/HTML/BODY/DIV[1]/DIV[3]/TABLE[2]/TBODY/TR[last()-2]/TD[3]/text()";
            String goldSellEx = "/HTML/BODY/DIV[1]/DIV[3]/TABLE[2]/TBODY/TR[last()-3]/TD[2]/text()";
            String goldBuyEx = "/HTML/BODY/DIV[1]/DIV[3]/TABLE[2]/TBODY/TR[last()-3]/TD[3]/text()";

            double silverSell = RussianPriceParser.parse(eval(silverSellEx, doc)).doubleValue();
            double silverBuy = RussianPriceParser.parse(eval(silverBuyEx, doc)).doubleValue();
            double goldSell = RussianPriceParser.parse(eval(goldSellEx, doc)).doubleValue();
            double goldBuy = RussianPriceParser.parse(eval(goldBuyEx, doc)).doubleValue();

            if (gold != null) {
                setGoldPrice(goldBuy, goldSell);
            }
            if (silver != null) {
                setSilverPrice(silverBuy, silverSell);
            }

            file.delete();
        } catch (Exception e) {
            throw new IOException(e);
        }

        if (!signals.isEmpty()) {
            return signals.poll();
        } else {
            return null;
        }
    }

    public void close() {

    }

    private void setSilverPrice(double silverBuy, double silverSell) {
        Price p = new Price(silverBuy, silverSell);

        MarketSignal ms = new MarketSignal();
        ms.setEventType(EventType.PriceChange);
        ms.setDateTime(new Date());
        ms.setAsset(silver);
        ms.setPrice(p);
        ms.setQuantity(0);
        ms.setVolume(0);
        ms.setSpread(p.getSpread());
        signals.add(ms);
    }

    private void setGoldPrice(double goldBuy, double goldSell) {
        Price p = new Price(goldBuy, goldSell);

        MarketSignal ms = new MarketSignal();
        ms.setEventType(EventType.PriceChange);
        ms.setDateTime(new Date());
        ms.setAsset(gold);
        ms.setPrice(p);
        ms.setQuantity(0);
        ms.setVolume(0);
        ms.setSpread(p.getSpread());
        signals.add(ms);
    }

//    private boolean hugeDeviation(Asset old, Asset fresh) {
//        double b = (old.getPrice().getBuy() + fresh.getPrice().getBuy()) / 2;
//        double s = (old.getPrice().getSell() + fresh.getPrice().getSell()) / 2;
//        return (b > 2 * old.getPrice().getBuy() || b < 0.5 * old.getPrice().getBuy()) ||
//                (s > 2 * old.getPrice().getSell() || s < 0.5 * old.getPrice().getSell());
//    }

    private static String eval(String ex, Document doc) throws Exception {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(ex);
        String s = (String) expr.evaluate(doc, XPathConstants.STRING);
        return s.trim();
    }
}
