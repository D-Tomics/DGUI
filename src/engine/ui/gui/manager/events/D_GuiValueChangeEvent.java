package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

public class D_GuiValueChangeEvent<T> extends D_GuiEvent{

    private T prevValue;
    private T newValue;

    public D_GuiValueChangeEvent(D_Gui slider, T prevValue, T newValue) {
        super(slider);
        this.prevValue = prevValue;
        this.newValue = newValue;
    }

    public T getPrevValue() {
        return prevValue;
    }

    public T getNewValue() {
        return newValue;
    }
}
