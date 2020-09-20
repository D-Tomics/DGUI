package org.dtomics.DGUI.utils;

public final class Timer {

    private double time;
    private boolean started;

    public Timer() { }
    public Timer(boolean start) { if(start) start(); }

    public void start() {
        if(started) return;
        started = true;
        time = 0;
    }

    public double stop() {
        started = false;
        return time;
    }

    public void update() {
        if(!started) return;
        time += Time.getDeltaInSec();
    }

    public void restart() {
        started = true;
        time = 0;
    }

    public double getTime() {
        return time;
    }

    public boolean isStarted() { return started; }
    public boolean isStopped() { return !started; }

}
