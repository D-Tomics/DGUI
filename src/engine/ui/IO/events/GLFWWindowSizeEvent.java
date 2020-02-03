package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWWindowSizeEvent extends GLFWEvent{

    public GLFWWindowSizeEvent(Window source) {
        super(source);
    }

    public float getWidth() {
        return getSource().getWidth();
    }

    public float getHeight() {
        return getSource().getHeight();
    }
}
