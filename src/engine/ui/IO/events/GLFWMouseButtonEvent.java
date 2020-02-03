package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWMouseButtonEvent extends GLFWEvent{

    private int button;
    private int action;
    private int mods;

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
