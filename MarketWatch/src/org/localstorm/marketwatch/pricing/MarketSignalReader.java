package org.localstorm.marketwatch.pricing;


import au.com.bytecode.opencsv.CSVReader;
import org.localstorm.marketwatch.Price;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class MarketSignalReader implements MarketSignalSource {

    private CSVReader csv;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy HH:mm:ss");

    public MarketSignalReader(File f) throws IOException {
        this.csv = new CSVReader(new FileReader(f));
        if (csv.readNext() == null) {
            throw new IllegalArgumentException("Input file is empty!");
        }
    }

    public MarketSignal readSignal() throws Exception {
        String[] data = csv.readNext();
        if (data == null) {
            return null;
        }

        MarketSignal signal = new MarketSignal();
        signal.setDateTime(dateFormat.parse(data[0]));
        signal.setEventType(EventType.valueOf(data[1]));
        signal.setAsset(data[2]);
        Price price = new Price();
            price.setBuy(Double.parseDouble(data[3]));
            price.setSell(Double.parseDouble(data[4]));
        signal.setPrice(price);
        signal.setQuantity(Double.parseDouble(data[5]));
        signal.setVolume(Double.parseDouble(data[6]));
        signal.setSpread(Double.parseDouble(data[7]));
        return signal;
    }

    public void close() {
        try {
            csv.close();
        } catch (IOException e) {
        }
    }

}
