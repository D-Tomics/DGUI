package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired when Mouse enters a gui
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseEnterEvent extends D_GuiEvent {
    public D_GuiMouseEnterEvent(D_Gui gui) {
        super(gui);
    }
}
