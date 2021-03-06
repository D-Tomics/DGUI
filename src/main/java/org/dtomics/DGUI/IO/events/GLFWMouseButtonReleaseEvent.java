package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWMouseButtonReleaseEvent extends GLFWEvent {

    private final int button;
    private final int mods;

    public GLFWMouseButtonReleaseEvent(Window source, int button, int mods) {
        super(source);
        this.button = button;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }

    public int getMods() {
        return mods;
    }
}
