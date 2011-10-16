package org.localstorm.marketwatch.pricing;

import au.com.bytecode.opencsv.CSVReader;
import org.localstorm.marketwatch.Asset;
import org.localstorm.marketwatch.Price;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:06 AM
 */
public class CsvSource implements PricingSource {

    private CSVReader reader;
    private String assetName;
    private String[] data;

    public CsvSource(String assetName, File csv) throws IOException {
        this.reader = new CSVReader(new FileReader(csv));
        reader.readNext();
        this.assetName = assetName;
    }

    public boolean updatePrices() throws IOException {
        if (reader == null) {
            return false;
        }
        String[] tmp = reader.readNext();
        if (tmp != null) {
            data = tmp;
            return true;
        } else {
            reader.close();
            reader = null;
        }

        return false;
    }

    public List<Asset> getPrices() {
        Asset asset = new Asset();
        asset.setName(assetName);
        Price price = new Price();
        price.setBuy(Double.parseDouble(data[0]));
        price.setSell(Double.parseDouble(data[1]));
        asset.setPrice(price);

        List<Asset> list = new ArrayList<Asset>();
        list.add(asset);
        return list;
    }

    public boolean isLocalSource() {
        return true;
    }

    public Price getAssetPrice(String assetName) {
        List<Asset> prices = getPrices();
        for (Asset a : prices) {
            if (a.getName().equals(assetName)) {
                return a.getPrice();
            }
        }
        return null;
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
