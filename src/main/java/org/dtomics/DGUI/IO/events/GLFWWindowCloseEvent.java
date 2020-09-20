package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWWindowCloseEvent extends GLFWEvent{

    public GLFWWindowCloseEvent(Window source) {
        super(source);
    }
}
