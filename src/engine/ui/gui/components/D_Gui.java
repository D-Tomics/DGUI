package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.animation.D_GuiAnimation;
import engine.ui.gui.manager.Style;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.manager.events.D_GuiEvent;
import engine.ui.gui.manager.events.D_GuiEventListener;
import engine.ui.gui.manager.events.D_GuiMousePressEvent;
import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.abstractions.Listener;
import engine.ui.utils.observers.Observable;
import engine.ui.utils.observers.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public abstract class D_Gui implements Observer {

    private static final D_GuiEventListener ON_PRESS_FOCUS_LISTENER = new D_GuiEventListener(D_GuiMousePressEvent.class) {
        @Override
        public void invokeEvent(D_GuiEvent event) {
            D_GuiMousePressEvent e = (D_GuiMousePressEvent)event;
            if(e.isButton(Mouse.MOUSE_BUTTON_LEFT)) {
                    e.getGui().requestFocus(true);
            }
        }
    };

    private int level;
    protected Style style;

    private boolean requestedFocus;
    private boolean requestedLooseFocus;
    private boolean focused;
    private boolean enabled;
    private boolean selected;
    private boolean visible;
    private boolean pressed;
    private boolean hovered;

    private boolean scrollable;
    private boolean selectable;
    private boolean hoverable;

    private D_Gui parent;
    private ArrayList<D_TextBox> texts;
    private ArrayList<D_GuiQuad> quads;
    private ArrayList<D_Constraint> constraints;
    private ArrayList<D_GuiAnimation> animations;
    private HashMap<Class<?>, ArrayList<D_GuiEventListener>> eventListeners;

    public D_Gui() {

        this.enabled = true;
        this.visible = true;
        this.hoverable = true;

        this.style = new Style();
        this.style.addObserver(this);
        if(!(this instanceof D_Label))
            this.addEventListener(ON_PRESS_FOCUS_LISTENER);
    }

    public void update() {
        updateAnimations();
        onUpdate();
    }

    @Override
    public void updateStates(Observable o) {
        runConstraints();
        onStateChange(o);
    }

    protected abstract void onUpdate();
    protected abstract void onStateChange(Observable o);

    public void addEventListener(Class<? extends D_GuiEvent> d_guiEventClass, Listener listener) {
        addEventListener(new D_GuiEventListener(d_guiEventClass) {
            @Override
            public void invokeEvent(D_GuiEvent event) {
                listener.invoke(event);
            }
        });
    }

    public void addEventListener(D_GuiEventListener listener) {
        if(listener == null) return;
        if(eventListeners == null)
            eventListeners = new HashMap<>();
        Class<? extends D_GuiEvent> eventClass = listener.getEventClass();
        ArrayList<D_GuiEventListener> listeners = eventListeners.get(eventClass);
        if(listeners == null) {
            listeners = new ArrayList<>();
            eventListeners.put(eventClass,listeners);
        }
        listeners.add(listener);
    }

    public void removeListener(D_GuiEventListener listener) {
        if (listener == null) return;
        if (eventListeners == null) return;
        Class<? extends D_GuiEvent> eventClass = listener.getEventClass();
        ArrayList<D_GuiEventListener> listeners = eventListeners.get(eventClass);
        if (listeners == null) return;
        listeners.remove(listener);
    }

    public void stackEvent(D_GuiEvent event) {
        if(!enabled) return;
        if(eventListeners == null)
            return;
        ArrayList<D_GuiEventListener> listeners = eventListeners.get(event.getClass());
        if(listeners == null) return;
        for(D_GuiEventListener listener : listeners) {
            listener.stackEvents(event);
        }
    }

    public void unstackEvents() {
        if(eventListeners == null) return;
        Set<Class<?>> eventClassSet = eventListeners.keySet();
        for(Class<?> eventClass : eventClassSet) {
            ArrayList<D_GuiEventListener> listeners = eventListeners.get(eventClass);
            if(listeners == null) continue;
            for(D_GuiEventListener listener : listeners) {
                listener.invokeEvents();
                listener.unstackEvents();
            }
        }
    }

    public void addAnimation(D_GuiAnimation animation) {
        if(!enabled) return;
        if(animation == null) return;
        else if(animations == null) animations = new ArrayList<>();
        if(animations.contains(animation)) return;
        animation.start(this);
        animations.add(animation);
    }

    public void removeAnimation(D_GuiAnimation animation) {
        if(animation == null) return;
        else if(animations == null) return;
        animation.stop(this);
        animations.remove(animation);
    }

    private void updateAnimations() {
        if(!enabled) return;
        if(animations == null) return;
        for(D_GuiAnimation animation : animations) {
            animation.update(this);
        }
    }

    public void addConstraint(D_Constraint constraint) {
        if(constraint == null) return;
        else if(constraints == null) constraints = new ArrayList<>();
        constraints.add(constraint);
        constraint.run(this);
        runConstraints();
    }

    public void addConstraints(D_Constraint...constraints) {
        for(D_Constraint constraint : constraints)
            addConstraint(constraint);
    }

    private void runConstraints() {
        if(constraints == null) return;
        for(D_Constraint constraint : constraints)
            constraint.run(this);
    }

    public void requestFocus(boolean focus) { this.requestedFocus = focus; }
    public void requestLooseFocus(boolean looseFocus) { this.requestedLooseFocus = looseFocus; }
    public void focus(boolean focus) { this.focused = focus; }

    public boolean requestedFocus() { return requestedFocus; }
    public boolean requestedLooseFocus() { return requestedLooseFocus; }

    public boolean isFocused() { return focused; }
    public boolean isEnabled() { return enabled; }
    public boolean isVisible() { return visible; }
    public boolean isScrollable() { return scrollable; }
    public boolean isHoverable() { return hoverable; }
    public boolean isSelectable() { return selectable; }
    public boolean isPressed() { return pressed; }

    public boolean isHovered() { return hovered; }
    public boolean isSelected() { return selected; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.style.notifyObservers();
    }
    public void setVisible(boolean visible) {
        if(!enabled) return;
        this.visible = visible;
        if(this.quads != null)
            for(D_GuiQuad quad : quads) quad.setVisible(visible);
        if(this.texts != null)
            this.texts.forEach(txt -> txt.setVisible(visible));
        this.style.notifyObservers();
    }
    public void setScrollable(boolean scrollable) { this.scrollable = scrollable; }
    public void setHoverable(boolean hoverable) { this.hoverable = hoverable; }
    public void setSelectable(boolean selectable) { this.selectable = selectable; }

    public void setPressed(boolean pressed) {
        if(!enabled) return;
        this.pressed = pressed;
        this.style.notifyObservers();
    }
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        this.style.notifyObservers();
    }
    public void setSelected(boolean selected) {
        if(!enabled) return;
        if(this.isSelectable())
            this.selected = selected;
        this.style.notifyObservers();
    }
    public void setStyle(Style style) { this.style.set(style); }

    public int getLevel() {
        return level;
    }
    public Style getStyle() { return style; }
    public D_Gui getParent() { return parent; }
    public ArrayList<D_GuiQuad> getQuads() { return quads; }

    public D_Gui getRoot() {
        if(this.parent != null)
            return parent.getRoot();
        return this;
    }

    protected void setParent(D_Gui parent) { this.parent = parent; }
    protected  void setLevel(int level) {
        this.level = level;
        for(int i = 0; quads != null && i < quads.size(); i++)
            quads.get(i).setLevel(level + 1);
    }
    protected  void addQuad(D_GuiQuad quad) {
        if(quads == null) quads = new ArrayList<>();
        Objects.requireNonNull(quad, "trying to add null Geometry");
        quads.add(quad);
        quad.setParent(this);
    }
    protected void removeQuad(D_GuiQuad quad) {
        quads.remove(quad);
        quad.setParent(null);
    }

    protected void addText(D_TextBox textBox) {
        if(textBox == null) return;
        if(texts == null) texts = new ArrayList<>();
        if(texts.contains(textBox)) return;
        texts.add(textBox);
        style.notifyObservers();
    }

    protected void removeText(D_TextBox textBox) {
        if(textBox == null) return;
        if(texts == null) return;
        texts.remove(textBox);
    }

}
