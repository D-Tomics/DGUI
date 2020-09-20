package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**This event is fired when the gui gains focus.
 *
 * @author Abdul Kareem
 */
public class D_GuiFocusGainEvent extends D_GuiEvent {
    public D_GuiFocusGainEvent(D_Gui gui) {
        super(gui);
    }
}
