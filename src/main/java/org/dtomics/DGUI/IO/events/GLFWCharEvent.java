package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWCharEvent extends GLFWEvent {

    private int codePoint;

    public GLFWCharEvent(Window source, int codePoint) {
        super(source);
        this.codePoint = codePoint;
    }

    public int getCodePoint() {
        return codePoint;
    }
}
