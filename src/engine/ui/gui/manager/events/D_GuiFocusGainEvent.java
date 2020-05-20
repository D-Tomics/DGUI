package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.events.D_GuiEvent;

/**This event is fired when the gui gains focus.
 *
 * @author Abdul Kareem
 */
public class D_GuiFocusGainEvent extends D_GuiEvent {
    public D_GuiFocusGainEvent(D_Gui gui) {
        super(gui);
    }
}
