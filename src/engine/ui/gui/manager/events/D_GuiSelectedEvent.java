package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

/**
 * this event is fired when a gui is selected
 *
 * @author Abdul Kareem
 */
public class D_GuiSelectedEvent extends D_GuiEvent{
    public D_GuiSelectedEvent(D_Gui gui) {
        super(gui);
    }
}
