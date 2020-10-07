package org.dtomics.DGUI.utils;

public class Time {

    private static long currentTime;
    private static long lastFrameTime;
    private static double delta;

    public static void init() {
        currentTime = getTime();
        lastFrameTime = getTime();
    }

    public static void update() {
        currentTime = getTime();
        delta = (currentTime - lastFrameTime);
        lastFrameTime = currentTime;
    }

    public static double getDeltaInSec() {
        return delta / 1000000000f;
    }

    public static double getDeltaInMs() {
        return delta / 1000000f;
    }

    public static double getDeltaInUs() {
        return delta / 1000f;
    }

    public static double getDeltaInNs() {
        return delta;
    }

    public static long getTime() {
        return System.nanoTime();
    }

}
