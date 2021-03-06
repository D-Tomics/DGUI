package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.animation.D_GuiAnimation;
import org.dtomics.DGUI.gui.manager.Style;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;
import org.dtomics.DGUI.gui.manager.events.D_GuiEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiEventListener;
import org.dtomics.DGUI.gui.manager.events.D_GuiMousePressEvent;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.text.D_TextMaster;
import org.dtomics.DGUI.gui.text.FontTextMap;
import org.dtomics.DGUI.utils.abstractions.Listener;
import org.dtomics.DGUI.utils.observers.Observable;
import org.dtomics.DGUI.utils.observers.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * This is an abstract representation of a gui. Every gui in this API extends this class.
 * This class is responsible for managing, assigning, updating  events, animations, texts, and its states.
 *
 * @author Abdul Kareem
 */
public abstract class D_Gui implements Observer {

    private static final D_GuiEventListener ON_PRESS_FOCUS_LISTENER = new D_GuiEventListener(D_GuiMousePressEvent.class) {
        @Override
        public void invokeEvent(D_GuiEvent event) {
            D_GuiMousePressEvent e = (D_GuiMousePressEvent) event;
            if (e.isButton(Mouse.MOUSE_BUTTON_LEFT)) {
                e.getGui().requestFocus(true);
            }
        }
    };

    private static final Consumer<? super ArrayList<D_GuiEventListener>> unstackEventConsumer = (listeners) -> {
        for (int i = 0; i < listeners.size(); i++) {
            D_GuiEventListener listener = listeners.get(i);
            listener.invokeEvents();
            listener.unstackEvents();
        }
    };

    protected Style style;
    private int level;
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
    private ArrayList<D_Icon> icons;
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
        if (!(this instanceof D_Label))
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
        if (icons != null)
            for (D_Icon icon : icons)
                icon.updateStates(icon.getStyle());
    }

    protected abstract void onUpdate();

    protected abstract void onStateChange(Observable o);

    public D_GuiEventListener addEventListener(Class<? extends D_GuiEvent> d_guiEventClass, Listener listener) {
        return addEventListener(new D_GuiEventListener(d_guiEventClass) {
            @Override
            public void invokeEvent(D_GuiEvent event) {
                listener.invoke(event);
            }
        });
    }

    public D_GuiEventListener addEventListener(D_GuiEventListener listener) {
        if (listener == null) return null;
        if (eventListeners == null)
            eventListeners = new HashMap<>();
        Class<? extends D_GuiEvent> eventClass = listener.getEventClass();
        ArrayList<D_GuiEventListener> listeners = eventListeners.get(eventClass);
        if (listeners == null) {
            listeners = new ArrayList<>();
            eventListeners.put(eventClass, listeners);
        }
        listeners.add(listener);
        return listener;
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
        if (!enabled) return;
        if (eventListeners == null)
            return;
        ArrayList<D_GuiEventListener> listeners = eventListeners.get(event.getClass());
        if (listeners == null) return;
        for (int i = 0; i < listeners.size(); i++) {
            D_GuiEventListener listener = listeners.get(i);
            listener.stackEvents(event);
        }
    }

    public void unstackEvents() {
        if (eventListeners == null) return;
        eventListeners.values().forEach(unstackEventConsumer);
    }


    public void addIcon(D_Icon icon, D_Constraint constraint) {
        if (icons == null) icons = new ArrayList<>();
        icons.add(icon);
        icon.addConstraint(constraint);
        icon.setLevel(this.level + 1);
        style.notifyObservers();
    }

    public void removeIcon(D_Icon icon) {
        if (icons == null) return;
        icons.remove(icon);
        style.notifyObservers();
    }

    public void startAnimation(D_GuiAnimation animation) {
        if (!enabled) return;
        if (animation == null) return;
        else if (animations == null) animations = new ArrayList<>();
        if (animations.contains(animation)) return;
        D_GuiAnimation clone = animation.clone();
        clone.start(this);
        animations.add(clone);
    }

    public void stopAnimation(D_GuiAnimation animation) {
        if (animation == null) return;
        else if (animations == null) return;
        if (!animations.contains(animation)) return;
        animations.get(animations.indexOf(animation)).stop(this);
        animations.remove(animation);
    }

    private void updateAnimations() {
        if (!enabled) return;
        if (animations == null) return;
        for (int i = 0; i < animations.size(); i++)
            if (animations.get(i).update(this))
                animations.get(i).stop(this);
    }

    public void addConstraint(D_Constraint constraint) {
        if (constraint == null) return;
        else if (constraints == null) constraints = new ArrayList<>();
        constraints.add(constraint);
        constraint.run(this);
        runConstraints();
    }

    public void addConstraints(D_Constraint... constraints) {
        for (D_Constraint constraint : constraints)
            addConstraint(constraint);
    }

    private void runConstraints() {
        if (constraints == null) return;
        for (int i = 0; i < constraints.size(); i++) {
            D_Constraint constraint = constraints.get(i);
            constraint.run(this);
        }
    }

    public void requestFocus(boolean focus) {
        this.requestedFocus = focus;
    }

    public void requestLooseFocus(boolean looseFocus) {
        this.requestedLooseFocus = looseFocus;
    }

    public void focus(boolean focus) {
        this.focused = focus;
    }

    public boolean requestedFocus() {
        return requestedFocus;
    }

    public boolean requestedLooseFocus() {
        return requestedLooseFocus;
    }

    public boolean isFocused() {
        return focused;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.style.notifyObservers();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (!enabled) return;
        this.visible = visible;
        if (this.quads != null)
            for (D_GuiQuad quad : quads) quad.setVisible(visible);
        if (this.icons != null)
            for (D_Icon icon : icons) icon.setVisible(visible);
        if (getTextMap() != null) {
            List<FontTextMap> textMap = getTextMap();
            for (int i = 0; i < textMap.size(); i++) {
                FontTextMap fontTextMap = textMap.get(i);
                if (fontTextMap.getTextBoxes() != null) {
                    for (int j = 0; j < fontTextMap.getTextBoxes().size(); j++) {
                        fontTextMap.getTextBoxes().get(j).setVisible(visible);
                    }
                }
            }
        }
        this.style.notifyObservers();
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public boolean isHoverable() {
        return hoverable;
    }

    public void setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        if (!enabled) return;
        this.pressed = pressed;
        this.style.notifyObservers();
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        this.style.notifyObservers();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (!enabled) return;
        if (this.isSelectable())
            this.selected = selected;
        this.style.notifyObservers();
    }

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
        for (int i = 0; quads != null && i < quads.size(); i++)
            quads.get(i).setLevel(level + 1);
        for (int i = 0; icons != null && i < icons.size(); i++)
            icons.get(i).setLevel(level + 1);
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style.set(style);
    }

    public D_Gui getParent() {
        return parent;
    }

    protected void setParent(D_Gui parent) {
        this.parent = parent;
    }

    public ArrayList<D_Icon> getIcons() {
        return icons;
    }

    public ArrayList<D_GuiQuad> getQuads() {
        return quads;
    }

    public List<FontTextMap> getTextMap() {
        return D_TextMaster.getTextMap(Window.get(), this);
    }

    public D_Gui getRoot() {
        if (this.parent != null)
            return parent.getRoot();
        return this;
    }

    protected void addQuad(D_GuiQuad quad) {
        if (quads == null) quads = new ArrayList<>();
        Objects.requireNonNull(quad, "trying to add null Geometry");
        quads.add(quad);
        quad.setLevel(this.level + 1);
        quad.setParent(this);
    }

    protected void removeQuad(D_GuiQuad quad) {
        quads.remove(quad);
        quad.setParent(null);
    }

    protected void addText(D_TextBox textBox) {
        D_TextMaster.load(Window.get(), this, textBox);
        style.notifyObservers();
    }

    protected void removeText(D_TextBox textBox) {
        D_TextMaster.remove(Window.get(), this, textBox);
    }

}
