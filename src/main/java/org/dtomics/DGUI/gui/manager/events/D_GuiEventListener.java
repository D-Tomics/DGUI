package org.dtomics.DGUI.gui.manager.events;

import java.util.ArrayList;

/**
 * Implementation of this class is passed onto a gui to handle a particular event.
 * It stacks an event of particular type when that event occurs and handles them via the implementation of abstract method
 *
 * @author Abdul Kareem
 * @see #invokeEvent(D_GuiEvent). Once the events in the stack are handled they are cleared from the stack.
 * @see org.dtomics.DGUI.gui.components.D_Gui#addEventListener(D_GuiEventListener)
 */
public abstract class D_GuiEventListener {

    private final ArrayList<D_GuiEvent> events;
    private final Class<? extends D_GuiEvent> eventClass;

    /**
     * @param eventClass represents the class of event that this listener should stack
     */
    public D_GuiEventListener(Class<? extends D_GuiEvent> eventClass) {
        this.eventClass = eventClass;
        this.events = new ArrayList<>();
    }

    public Class<? extends D_GuiEvent> getEventClass() {
        return eventClass;
    }

    /**
     * This method stacks events of the type eventClass
     *
     * @param event the event that has occurred and yet to be handled
     */
    public void stackEvents(D_GuiEvent event) {
        if (event.getClass() != eventClass) return;
        this.events.add(event);
    }

    /**
     * This method clears the stack
     */
    public void unstackEvents() {
        events.clear();
        events.trimToSize();
    }

    /**
     * This method calls the handling function on every events that has been stacked
     */
    public void invokeEvents() {
        for (D_GuiEvent event : events)
            this.invokeEvent(event);
    }

    /**
     * This method is an event handling function. All the stacked events are handled through this method
     *
     * @param event This is the event that has been occurred
     */
    public abstract void invokeEvent(D_GuiEvent event);

}
