package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired when a gui is dragged using the mouse
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseDragEvent extends D_GuiEvent {

    private final int button;
    private final int mods;

    private float dx, dy;

    /**
     * Will be called when a gui is dragged
     *
     * @param gui    the gui that has received the event
     * @param button the mouse button used to drag the gui
     * @param mods   bitfield describing which modifiers keys were held down
     */
    public D_GuiMouseDragEvent(D_Gui gui, int button, int mods, float dx, float dy) {
        super(gui);
        this.button = button;
        this.mods = mods;
        this.dx = dx;
        this.dy = dy;
    }

    public int getButton() {
        return button;
    }

    public int getMods() {
        return mods;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
}
