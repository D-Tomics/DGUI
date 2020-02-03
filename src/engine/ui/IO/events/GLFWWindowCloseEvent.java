package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWWindowCloseEvent extends GLFWEvent{

    public GLFWWindowCloseEvent(Window source) {
        super(source);
    }
}
