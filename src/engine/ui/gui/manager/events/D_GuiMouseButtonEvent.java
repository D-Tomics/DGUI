package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiMouseButtonEvent extends D_GuiEvent {

    private int button;
    private int action;
    private int mods;

    public D_GuiMouseButtonEvent(D_Gui gui, int button, int action, int mods) {
        super(gui);
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
