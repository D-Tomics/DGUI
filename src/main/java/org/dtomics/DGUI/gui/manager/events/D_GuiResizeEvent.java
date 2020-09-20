package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * Thi event is fired when a gui's size is changed
 *
 * @author Abdul Kareem
 */
public class D_GuiResizeEvent extends D_GuiEvent{

    private float previousWidth;
    private float previousHeight;

    private float currentWidth;
    private float currentHeight;

    /** Will be called when resizing a gui
     *
     * @param gui               the gui that received the event
     * @param previousWidth     width of gui before the change
     * @param previousHeight    height of gui before the change
     * @param currentWidth      new width of gui
     * @param currentHeight     new height of gui
     */
    public D_GuiResizeEvent(D_Gui gui, float previousWidth, float previousHeight, float currentWidth, float currentHeight) {
        super(gui);
        this.previousWidth = previousWidth;
        this.previousHeight = previousHeight;
        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
    }

    public float getPreviousWidth() {
        return previousWidth;
    }

    public float getPreviousHeight() {
        return previousHeight;
    }

    public float getCurrentWidth() {
        return currentWidth;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }
}
