package engine.ui.IO.events;

import engine.ui.IO.Window;
import engine.ui.utils.D_Event;

public class GLFWEvent extends D_Event<Window> {
    public GLFWEvent(Window source) {
        super(source);
    }
}
