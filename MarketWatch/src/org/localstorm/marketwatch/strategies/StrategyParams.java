package org.localstorm.marketwatch.strategies;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StrategyParams {

    private double deviationFactor;
    private double spreadCap;
    private boolean dynamicChunks;
    private boolean dynamicSpread;

    public static StrategyParams load(File f) throws IOException {
        Properties p = new Properties();
        FileReader reader = new FileReader(f);
        try {
            p.load(reader);
            double deviationFactor = Double.parseDouble(p.getProperty("deviationFactor"));
            double spreadCap = Double.parseDouble(p.getProperty("spreadCap"));
            boolean dynamicChunks = Boolean.parseBoolean(p.getProperty("dynamicChunks"));
            boolean dynamicSpread = Boolean.parseBoolean(p.getProperty("dynamicSpread"));
            StrategyParams sp = new StrategyParams();
            sp.setDeviationFactor(deviationFactor);
            sp.setSpreadCap(spreadCap);
            sp.setDynamicChunks(dynamicChunks);
            sp.setDynamicSpread(dynamicSpread);
            return sp;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public double getDeviationFactor() {
        return deviationFactor;
    }

    public double getSpreadCap() {
        return spreadCap;
    }

    public void setDeviationFactor(double deviationFactor) {
        this.deviationFactor = deviationFactor;
    }

    public void setSpreadCap(double spreadCap) {
        this.spreadCap = spreadCap;
    }

    public boolean isDynamicChunks() {
        return dynamicChunks;
    }

    public void setDynamicChunks(boolean dynamicChunks) {
        this.dynamicChunks = dynamicChunks;
    }

    public boolean isDynamicSpread() {
        return dynamicSpread;
    }

    public void setDynamicSpread(boolean dynamicSpread) {
        this.dynamicSpread = dynamicSpread;
    }
}
