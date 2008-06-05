package com.swsoft.trial.server;

/**
 * Thread with custom stop/pause/resume 
 * @author Kuznetsov A.
 * @version 1.0
 */
class ManagedThread extends Thread {
	
    public ManagedThread() {
        this.desiredState = ThreadStates.Running;
    }

    public void pauseThread() {
        synchronized (this) {
            if (desiredState!=ThreadStates.Stop) {
                desiredState = ThreadStates.Sleep;
            }
        }
    }

    public void resumeThread()
    {
        synchronized (this) {
            if (desiredState != ThreadStates.Stop)
            {
                desiredState = ThreadStates.Running;
                notifyAll();
            }
        }
    }

    public void stopThread() {
        synchronized (this) {
            desiredState = ThreadStates.Stop;
            notifyAll();
        }
    }

    protected boolean keepRunning() {
        synchronized (this) {
            if (desiredState == ThreadStates.Running) {
                return true;
            } else {
                while(true) {
                    if (desiredState == ThreadStates.Stop) {
                        return false;
                    } else if (desiredState == ThreadStates.Sleep) {
                        try {
                            
                        	this.wait();
                        	
                        } catch (Exception ex) {
                            return false;
                        }
                    } else if (desiredState == ThreadStates.Running) {
                        return true;
                    }
                }
            }
        }
    }

    public static boolean await(long msec) {
        try {
        	
            Thread.sleep(msec);
            return true;
            
        } catch(InterruptedException ex) {
            return false;
        }
    }

    private ThreadStates desiredState;

    private static enum ThreadStates {
	    Running, Sleep, Stop
	}

}
