package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Slider;

public class D_GuiValueChangeEvent extends D_GuiEvent{

    private float prevValue;
    private float newValue;

    public D_GuiValueChangeEvent(D_Slider slider, float prevValue, float newValue) {
        super(slider);
        this.prevValue = prevValue;
        this.newValue = newValue;
    }

    public float getPrevValue() {
        return prevValue;
    }

    public float getNewValue() {
        return newValue;
    }
}
