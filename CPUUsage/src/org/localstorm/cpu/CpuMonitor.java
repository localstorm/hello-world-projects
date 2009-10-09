package org.localstorm.cpu;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CpuMonitor extends Thread {

    private static final int ONE_MILLIS_IN_NANOS = 1000000;
    private final long samplingDelay;
    private final ThreadMXBean tmb;

    public CpuMonitor(long samplingDelay) {
	this.samplingDelay = samplingDelay;
	tmb = ManagementFactory.getThreadMXBean();
    }

    @Override
    public void run() {
	if (!tmb.isThreadCpuTimeSupported()) {
	    System.out.println("Thread CPU time not supported!");
	    return;
	}

	if (!tmb.isThreadCpuTimeEnabled()) {
	    tmb.setThreadCpuTimeEnabled(true);
	}

	long[] cpuTimes = new long[0];
	long[] cpuTimesSnapshots = new long[0];
	long[] cpuTimeDeltas = new long[0];

	long time = nanos();
	boolean recalcNeeded = true;

	while (!Thread.interrupted()) {
	    try {
		long[] ids = tmb.getAllThreadIds();

		if (cpuTimes.length != ids.length || recalcNeeded) {
		    time = nanos();
		    cpuTimes = new long[ids.length];
		    cpuTimesSnapshots = new long[ids.length];
		    cpuTimeDeltas = new long[ids.length];
		    recalcNeeded = fillInitialCpuTimes(ids, cpuTimes,
			    cpuTimesSnapshots);
		    continue;
		}

		long nowNanos = nanos();

		if (nowNanos - time > ONE_MILLIS_IN_NANOS*3*samplingDelay) {
		    time = nowNanos;
		    recalcNeeded = fillInitialCpuTimes(ids, cpuTimes,
			    cpuTimesSnapshots);
		    continue;
		}

		recalcNeeded = cpuTimeStapshot(ids, cpuTimes,
			cpuTimesSnapshots, cpuTimeDeltas);
		if (recalcNeeded) {
		    continue;
		}

		System.out.println(calculateAvgLoad(cpuTimeDeltas, time)*100);
	    } finally {
		idle(); // idle in any case
	    }
	}
    }

    private double calculateAvgLoad(long[] cpuTimeDeltas, long time) {
	long timeDelta = nanos() - time;
	if (timeDelta != 0) {
	    long cpuTimeSum = 0;
	    for (int i = cpuTimeDeltas.length - 1; i >= 0; i--) {
		cpuTimeSum += cpuTimeDeltas[i];
	    }
	    return (double) cpuTimeSum / timeDelta;
	}
	return 0;
    }

    private boolean cpuTimeStapshot(long[] ids, long[] cpuTimes,
	    long[] cpuTimesSnapshots, long[] cpuTimeDeltas) {
	for (int i = ids.length - 1; i >= 0; i--) {
	    long time = tmb.getThreadCpuTime(ids[i]);
	    if (time < 0) {
		return true;
	    }
	    cpuTimesSnapshots[i] = time;
	    cpuTimeDeltas[i] = time - cpuTimes[i];
	}
	return false;
    }

    private boolean fillInitialCpuTimes(long[] ids, long[] cpuTimes,
	    long[] cpuTimesSnapshots) {
	for (int i = ids.length - 1; i >= 0; i--) {
	    long time = tmb.getThreadCpuTime(ids[i]);
	    if (time < 0) {
		return true;
	    }
	    cpuTimes[i] = cpuTimesSnapshots[i] = time;
	}
	return false;
    }

    private void idle() {
	try {
	    Thread.sleep(samplingDelay);
	} catch (InterruptedException e) {
	    return;
	}
    }

    private long nanos() {
	return System.nanoTime();
    }
}
