package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWCharEvent extends GLFWEvent {

    private int codePoint;

    public GLFWCharEvent(Window source, int codePoint) {
        super(source);
        this.codePoint = codePoint;
    }

    public int getCodePoint() {
        return codePoint;
    }
}
