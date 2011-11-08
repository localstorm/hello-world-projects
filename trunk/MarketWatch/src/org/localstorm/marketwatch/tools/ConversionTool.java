package org.localstorm.marketwatch.tools;

import au.com.bytecode.opencsv.CSVReader;
import org.localstorm.marketwatch.*;
import org.localstorm.marketwatch.pricing.EventType;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;


public class ConversionTool {


    public static void main(String[] args) throws Exception {

        String assetName = "Gold";
        CSVReader csv = new CSVReader(new FileReader("history/2011-" + assetName + ".csv"));
        csv.readNext();

        String[] data;

        while ((data = csv.readNext()) != null) {

            File f = new File("history/" + assetName + ".mkt.csv");
            boolean needHeader = !f.exists();

            FileOutputStream fos = new FileOutputStream(f, true);
            PrintStream pst = new PrintStream(fos);
            try {
                if (needHeader) {
                    pst.println("DateTime,EventType,Asset,BuyPrice,SellPrice,Quantity,Volume,Spread");
                    System.out.println("DateTime,EventType,Asset,BuyPrice,SellPrice,Quantity,Volume,Spread");
                }
                String line = data[2] + "," +
                        EventType.PriceChange.toString() + "," +
                        assetName + "," +
                        data[0] + "," +
                        data[1] + "," +
                        "0," +
                        "0," +
                        getSpread(data[0], data[1]);
                pst.println(line);
                System.out.println(line);
            } finally {
                pst.close();
            }
        }

    }

    private static double getSpread(String b, String s) {
        Price p = new Price();
        p.setBuy(Double.parseDouble(b));
        p.setSell(Double.parseDouble(s));
        return p.getSpread();
    }


}
