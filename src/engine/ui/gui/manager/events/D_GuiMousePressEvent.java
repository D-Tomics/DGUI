package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiMousePressEvent extends D_GuiEvent {

    private int button;
    private int mods;

    public D_GuiMousePressEvent(D_Gui gui, int button, int mods) {
        super(gui);
        this.button = button;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }
    public int getMods() { return mods; }

    public boolean isButton(int button) {
        return this.button == button;
    }

}
