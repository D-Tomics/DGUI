package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWWindowFocusLooseEvent extends GLFWEvent {
    public GLFWWindowFocusLooseEvent(Window source) {
        super(source);
    }
}
