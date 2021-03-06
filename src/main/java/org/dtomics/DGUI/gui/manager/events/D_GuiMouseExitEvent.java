package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired when Mouse exits a gui
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseExitEvent extends D_GuiEvent {
    public D_GuiMouseExitEvent(D_Gui gui) {
        super(gui);
    }
}
