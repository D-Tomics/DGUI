package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWKeyReleaseEvent extends GLFWEvent{

    private int key;
    private int scanCode;
    private int mods;

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
