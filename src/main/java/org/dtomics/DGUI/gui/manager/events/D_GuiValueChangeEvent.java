package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This event is fired on particular gui's such as a slider , list when their values changes
 *
 * @param <T> this is the class of the value that changes
 * @author Abdul Kareem
 */
public class D_GuiValueChangeEvent<T> extends D_GuiEvent {

    private final T prevValue;
    private final T newValue;

    /**
     * Will be called when value of gui is changed
     *
     * @param gui       the gui that received the event
     * @param prevValue value before the change
     * @param newValue  value after the change
     */
    public D_GuiValueChangeEvent(D_Gui gui, T prevValue, T newValue) {
        super(gui);
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
