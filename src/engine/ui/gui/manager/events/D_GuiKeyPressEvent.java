package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiKeyPressEvent extends D_GuiEvent {

    private int key;
    private int scanCode;
    private int mods;

    public D_GuiKeyPressEvent(D_Gui gui, int key, int codePoint, int mods) {
        super(gui);
        this.key = key;
        this.scanCode = codePoint;
        this.mods = mods;
    }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getMods() {
        return mods;
    }
}
