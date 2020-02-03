package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWKeyEvent extends GLFWEvent{

    private int key;
    private int codePoint;
    private int action;
    private int mod;

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
