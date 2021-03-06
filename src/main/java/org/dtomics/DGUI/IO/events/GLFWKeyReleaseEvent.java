package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWKeyReleaseEvent extends GLFWEvent {

    private final int key;
    private final int scanCode;
    private final int mods;

    public GLFWKeyReleaseEvent(Window source, int key, int scanCode, int mods) {
        super(source);
        this.key = key;
        this.scanCode = scanCode;
        this.mods = mods;
    }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getMods() {
        return mods;
    }
}
