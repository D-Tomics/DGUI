package org.dtomics.DGUI.utils;

public class Delay {

    private final long length;
    private long endTime;

    private boolean start;

    public Delay(long timeInMs) {
        this.length = timeInMs * 1000000;
    }

    public void start() {
        if (start)
            return;
        start = true;
        endTime = Time.getTime() + length;
    }

    public boolean over() {
        start();
        if (Time.getTime() <= endTime) return false;
        reset();
        return true;
    }

    public void reset() {
        start = false;
    }

}
