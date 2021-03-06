package org.dtomics.DGUI.IO.events;

import org.dtomics.DGUI.IO.Window;

public class GLFWMouseButtonEvent extends GLFWEvent {

    private final int button;
    private final int action;
    private final int mods;

    public GLFWMouseButtonEvent(Window source, int button, int action, int mods) {
        super(source);
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }
}
