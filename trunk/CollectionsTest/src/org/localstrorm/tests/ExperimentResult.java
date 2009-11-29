package org.localstrorm.tests;

/**
 * Created by IntelliJ IDEA.
 * User: localstorm
 * Date: Nov 29, 2009
 * Time: 11:56:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExperimentResult {
    private final long time;
    private final long factor;
    private final int size;

    public ExperimentResult(long time, long factor, int size) {
        this.time = time;
        this.size = size;
        this.factor = factor;
    }

    public long getTime() {
        return time;
    }

    public int getSize() {
        return size;
    }

    public long getFactor() {
        return factor;
    }
}
