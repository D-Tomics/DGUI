package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;
import engine.ui.utils.D_Event;

public abstract class D_GuiEvent extends D_Event<D_Gui> {

    public D_GuiEvent(D_Gui gui) {
        super(gui);
    }

    public final D_Gui getGui() {
        return super.getSource();
    }

}
