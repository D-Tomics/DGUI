package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

/**This event is fired when the gui looses its focus.
 *
 * @author Abdul Kareem
 */
public class D_GuiFocusLooseEvent extends D_GuiEvent {
    public D_GuiFocusLooseEvent(D_Gui gui) {
        super(gui);
    }
}
