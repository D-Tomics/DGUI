package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiScrollEvent extends D_GuiEvent {

    private double xoffset;
    private double yoffset;

    public D_GuiScrollEvent(D_Gui gui, double xoffset, double yoffset) {
        super(gui);
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public double getXoffset() {
        return xoffset;
    }

    public double getYoffset() {
        return yoffset;
    }
}
