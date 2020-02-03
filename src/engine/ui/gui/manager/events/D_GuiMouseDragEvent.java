package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiMouseDragEvent extends D_GuiEvent{

    private int button;

    public D_GuiMouseDragEvent(D_Gui gui, int button) {
        super(gui);
        this.button = button;
    }

    public int getButton() {
        return button;
    }


}
