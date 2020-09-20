package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**This event is fired on focused gui when a scrolling device is used, such as a mouse wheel or scrolling area of a touchpad.
 *
 * @author Abdul Kareem
 */
public class D_GuiScrollEvent extends D_GuiEvent {

    private double xoffset;
    private double yoffset;

    /**
     * Will be called when a scrolling device is used, such as a mouse wheel or scrolling area of a touchpad.
     *
     * @param gui     the gui that received the event
     * @param xoffset the scroll offset along the x-axis
     * @param yoffset the scroll offset along the y-axis
     */
    public D_GuiScrollEvent(D_Gui gui, double xoffset, double yoffset) {
        super(gui);
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }
}
