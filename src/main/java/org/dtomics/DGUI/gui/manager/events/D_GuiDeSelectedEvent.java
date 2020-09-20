package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is called when a gui is de selected
 *
 * @author Abdul Kareem
 */
public class D_GuiDeSelectedEvent extends D_GuiEvent{
    public D_GuiDeSelectedEvent(D_Gui gui) {
        super(gui);
    }
}
