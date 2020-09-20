package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWScrollEvent extends GLFWEvent {

    private double xoffset;
    private double yoffset;

    public GLFWScrollEvent(Window source, double xoffset, double yoffset) {
        super(source);
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }

}
