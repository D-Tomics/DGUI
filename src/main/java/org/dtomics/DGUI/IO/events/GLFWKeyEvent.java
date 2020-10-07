package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWKeyEvent extends GLFWEvent {

    private final int key;
    private final int codePoint;
    private final int action;
    private final int mod;

    public GLFWKeyEvent(Window source, int key, int codePoint, int action, int mod) {
        super(source);
        this.key = key;
        this.codePoint = codePoint;
        this.action = action;
        this.mod = mod;
    }

    public int getKey() {
        return key;
    }

    public boolean isKey(int key) {
        return this.key == key;
    }

    public int getCodePoint() {
        return codePoint;
    }

    public int getAction() {
        return action;
    }

    public boolean isAction(int action) {
        return this.action == action;
    }

    public int getMod() {
        return mod;
    }
}
