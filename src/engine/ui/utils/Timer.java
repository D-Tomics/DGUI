package engine.ui.utils;

public final class Timer {

    private double time;
    private boolean started;

    public void start() {
        if(started) return;
        started = true;
        time = 0;
    }

    public void stop() {
        started = false;
        time = 0;
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

}
