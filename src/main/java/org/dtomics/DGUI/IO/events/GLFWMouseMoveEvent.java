package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWMouseMoveEvent extends GLFWEvent {

    private double xpos;
    private double ypos;

    public GLFWMouseMoveEvent(Window source, double xpos, double ypos) {
        super(source);
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public double getXpos() {
        return xpos;
    }

    public double getYpos() {
        return ypos;
    }
}
