package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

/**
 * This event is fired when the mouse hovers over a gui
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseHoverEvent extends D_GuiEvent {

    public D_GuiMouseHoverEvent(D_Gui gui) {
        super(gui);
    }
    
}
