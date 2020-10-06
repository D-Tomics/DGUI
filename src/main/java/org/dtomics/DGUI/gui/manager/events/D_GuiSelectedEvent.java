package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * this event is fired when a gui is selected
 *
 * @author Abdul Kareem
 */
public class D_GuiSelectedEvent extends D_GuiEvent {
    public D_GuiSelectedEvent(D_Gui gui) {
        super(gui);
    }
}
