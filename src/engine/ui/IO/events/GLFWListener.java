package engine.ui.IO.events;

public abstract class GLFWListener {

    private Class<? extends GLFWEvent> eventClass;

    public GLFWListener(Class<? extends GLFWEvent> eventClass) {
        this.eventClass = eventClass;
    }

    public Class<? extends GLFWEvent> getEventClass() {
        return eventClass;
    }

    public abstract void invoke(GLFWEvent event);
}
