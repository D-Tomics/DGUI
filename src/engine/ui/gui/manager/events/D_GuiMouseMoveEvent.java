package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiMouseMoveEvent extends D_GuiEvent {

    private double xPos;
    private double yPos;

    public D_GuiMouseMoveEvent(D_Gui gui, double xPos, double yPos) {
        super(gui);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
}
