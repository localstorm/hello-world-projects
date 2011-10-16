package org.localstorm.marketwatch.pricing;

/**
 * Created by IntelliJ IDEA.
 * User: LocalStorm
 * Date: 10/10/11
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class MinMax {
    private double min;
    private double max;

    public MinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
