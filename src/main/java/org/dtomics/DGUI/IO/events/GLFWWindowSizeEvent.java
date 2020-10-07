package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWWindowSizeEvent extends GLFWEvent {

    private final float prevWidth;
    private final float prevHeight;

    public GLFWWindowSizeEvent(Window source, float prevWidth, float prevHeight) {
        super(source);
        this.prevWidth = prevWidth;
        this.prevHeight = prevHeight;
    }

    public float getPrevWidth() {
        return prevWidth;
    }

    public float getPrevHeight() {
        return prevHeight;
    }

    public float getWidth() {
        return getSource().getWidth();
    }

    public float getHeight() {
        return getSource().getHeight();
    }
}
