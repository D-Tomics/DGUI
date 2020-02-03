package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiMouseReleaseEvent extends D_GuiEvent{

    private int button;
    private int mods;

    public D_GuiMouseReleaseEvent(D_Gui gui, int button, int mods) {
        super(gui);
        this.button = button;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }
    public int getMods() { return mods; }

}
