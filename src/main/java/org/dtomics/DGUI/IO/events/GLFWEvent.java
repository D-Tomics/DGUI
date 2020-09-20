package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.utils.D_Event;

public class GLFWEvent extends D_Event<Window> {
    public GLFWEvent(Window source) {
        super(source);
    }
}
