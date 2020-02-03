package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWWindowMinimizeEvent extends GLFWEvent{
    public GLFWWindowMinimizeEvent(Window source) {
        super(source);
    }
}
