package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired when ever the position of mouse changes inside the gui
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseMoveEvent extends D_GuiEvent {

    private final double xPos;
    private final double yPos;

    /**
     * Will be called when Mouse position changes
     *
     * @param gui  gui that received the event
     * @param xPos new x position of mouse
     * @param yPos new y position of mouse
     */
    public D_GuiMouseMoveEvent(D_Gui gui, double xPos, double yPos) {
        super(gui);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
}
