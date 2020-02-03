package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiKeyEvent extends D_GuiEvent {

    private int key;
    private int scanCode;
    private int mods;
    private int action;

    public D_GuiKeyEvent(D_Gui gui, int key, int scanCode, int mods, int action) {
        super(gui);
        this.key = key;
        this.scanCode = scanCode;
        this.mods = mods;
        this.action = action;
    }

    public boolean isAction(int...actions) { for(int action : actions) if(action == this.action) return true; return false; }
    public boolean isKey(int...keys) { for(int key : keys) if(this.key == key) return true; return false; }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getMods() {
        return mods;
    }

    public int getAction() {
        return action;
    }
}
