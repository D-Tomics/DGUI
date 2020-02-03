package engine.ui.gui.manager.events;

import java.util.ArrayList;

public abstract class D_GuiEventListener {

    private Class<? extends D_GuiEvent> eventClass;
    private final ArrayList<D_GuiEvent> events;

    public D_GuiEventListener(Class<? extends D_GuiEvent> eventClass) {
        this.eventClass = eventClass;
        this.events = new ArrayList<>();
    }

    public Class<? extends D_GuiEvent> getEventClass() {
        return eventClass;
    }

    public void stackEvents(D_GuiEvent event) {
        this.events.add(event);
    }

    public void unstackEvents() {
        events.clear();
        events.trimToSize();
    }

    public void invokeEvents() {
        for(D_GuiEvent event : events)
            this.invokeEvent(event);
    }

    public abstract void invokeEvent(D_GuiEvent event);

}
