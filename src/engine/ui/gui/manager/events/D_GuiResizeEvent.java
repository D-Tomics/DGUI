package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiResizeEvent extends D_GuiEvent{

    private float previousWidth;
    private float previousHeight;

    private float currentWidth;
    private float currentHeight;

    public D_GuiResizeEvent(D_Gui gui, float previousWidth, float previousHeight, float currentWidth, float currentHeight) {
        super(gui);
        this.previousWidth = previousWidth;
        this.previousHeight = previousHeight;
        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
    }

    public float getPreviousWidth() {
        return previousWidth;
    }

    public float getPreviousHeight() {
        return previousHeight;
    }

    public float getCurrentWidth() {
        return currentWidth;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }
}
