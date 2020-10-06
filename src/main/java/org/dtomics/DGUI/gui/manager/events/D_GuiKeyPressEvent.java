package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired on the focused gui when keyboard keys are pressed
 *
 * @author Abdul Kareem
 */
public class D_GuiKeyPressEvent extends D_GuiEvent {

    private final int key;
    private final int scanCode;
    private final int mods;

    /**
     * Will be called when a key is pressed
     *
     * @param gui      the gui that has received the event
     * @param key      the keyboard key that was pressed or released
     * @param scanCode the system-specific scancode of the key
     * @param mods     bitfield describing which modifiers keys were held down
     */
    public D_GuiKeyPressEvent(D_Gui gui, int key, int scanCode, int mods) {
        super(gui);
        this.key = key;
        this.scanCode = scanCode;
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
