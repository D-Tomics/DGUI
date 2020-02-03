package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWWindowFocusGainEvent extends GLFWEvent{
    public GLFWWindowFocusGainEvent(Window source) {
        super(source);
    }
}
