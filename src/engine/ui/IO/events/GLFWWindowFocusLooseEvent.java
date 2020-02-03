package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWWindowFocusLooseEvent extends GLFWEvent {
    public GLFWWindowFocusLooseEvent(Window source) {
        super(source);
    }
}
