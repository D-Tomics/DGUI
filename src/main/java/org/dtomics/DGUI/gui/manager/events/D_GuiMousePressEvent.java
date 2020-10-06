package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired on the focused gui when pressed Mouse buttons are pressed
 *
 * @author Abdul Kareem
 */
public class D_GuiMousePressEvent extends D_GuiEvent {

    private final int button;
    private final int mods;

    /**
     * Will be called when a key is pressed.
     *
     * @param gui    the gui that has received the event
     * @param button the Mouse Button that was pressed
     * @param mods   bitfield describing which modifiers keys were held down
     */
    public D_GuiMousePressEvent(D_Gui gui, int button, int mods) {
        super(gui);
        this.button = button;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }

    public int getMods() {
        return mods;
    }

    public boolean isButton(int button) {
        return this.button == button;
    }

}
