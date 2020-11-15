package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Cursors;
import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.manager.constraints.gui_constraints.Fill;
import org.dtomics.DGUI.gui.manager.events.D_GuiFocusLooseEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseEnterEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseExitEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMousePressEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiResizeEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiScrollEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiValueChangeEvent;
import org.dtomics.DGUI.utils.D_Event;
import org.dtomics.DGUI.utils.abstractions.Listener;
import org.dtomics.DGUI.utils.observers.Observable;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

/**
 * This class represents a drop down list component
 *
 * @param <T> The class of data contained in a list
 */
public class D_List<T> extends D_Component {

    private static final Listener ON_CELL_MOUSE_EXIT = e -> ((D_GuiQuad) e.getSource()).getStyle().setBorderWidth(0);
    private static final Listener ON_CELL_MOUSE_ENTER = e -> ((D_GuiQuad) e.getSource()).getStyle().setBorderWidth(1);
    private static final Listener ON_CELL_RESIZE = D_List::onQuadResize;

    private final Listener ON_CELL_SELECT = e -> onSelect((D_GuiQuad) e.getSource());
    private final D_GuiQuad container;

    private int windowSize = 5;
    private int windowStart = 1;
    private int windowStop = windowStart + windowSize - 1;
    private int index = 1;

    private D_GuiQuad selected;
    private HashMap<String, T> items;

    public D_List(T... items) {
        this.style.setBounds(0, 0, 100, 30, false).setCursor(Cursors.HAND.get());

        this.container = new D_GuiQuad(this.getStyle().getWidth(), this.style.getHeight() * Math.min(items.length, windowSize));
        this.container.setVisible(false);
        this.container.setHoverable(false);
        this.addQuad(container);

        this.setScrollable(true);
        this.setSelectable(true);
        addItem(items);

        this.addEventListener(D_GuiKeyEvent.class, this::onKeyPress);
        this.addEventListener(D_GuiResizeEvent.class, this::onSizeChange);
        this.addEventListener(D_GuiFocusLooseEvent.class, e -> this.setSelected(false));
        this.addEventListener(D_GuiScrollEvent.class, this::onScroll);
    }

    public T getSelectedItem() { return items.get(selected.getText()); }

    public void setScrollWindowSize(int length) {
        windowSize = length;
        windowStop = windowStart + windowSize - 1;
    }

    public void addItem(T... items) {
        for (T item : items)
            addItem(item);
    }

    public D_List<T> addItem(T item) { return this.addItem(item.toString(), item); }

    public D_List<T> addItem(String text, T item) {
        if (items == null) items = new HashMap<>();
        if (items.containsKey(item.toString())) return this;

        items.put(text, item);

        D_GuiQuad quad = new D_GuiQuad(this.getStyle().getWidth() - 2, this.style.getHeight() - 2, text);
        quad.style.setBorderWidth(0).setCursor(Cursors.HAND.get());
        quad.setVisible(false);

        quad.addConstraint(new Fill(this, true, true, 2, 2));
        quad.addEventListener(D_GuiMousePressEvent.class, ON_CELL_SELECT);
        quad.addEventListener(D_GuiMouseEnterEvent.class, ON_CELL_MOUSE_ENTER);
        quad.addEventListener(D_GuiMouseExitEvent.class, ON_CELL_MOUSE_EXIT);
        quad.addEventListener(D_GuiResizeEvent.class, ON_CELL_RESIZE);
        addQuad(quad);

        if (selected == null) {
            selected = quad;
            selected.setVisible(true);
            selected.style.setBorderWidth(0);
            selected.style.setCenter(style.getCenter());
        }
        this.container.getStyle().setHeight(this.style.getHeight() * Math.min(items.size(), windowSize));
        return this;
    }

    @Override
    public void onStateChange(Observable o) {
        if (this.getQuads() != null) {
            container.style.setPosition(style.getX(), style.getY() - this.style.getHeight());
            if (container.style.getY() - container.style.getHeight() <= -Window.get().getHeight() / 2.0f) {
                float dy = container.style.getY() - container.style.getHeight() + Window.get().getHeight() / 2.0f;
                container.style.setPosition(style.getX(), style.getY() - dy);
            }

            if (container.style.getY() >= Window.get().getHeight() / 2.0f) {
                float dy = container.style.getY() - Window.get().getHeight() / 2.0f;
                container.style.setPosition(style.getX(), style.getY() - dy);
            }

            float y = container.style.getY() - this.style.getHeight() / 2.0f;
            for (int i = 1; items != null && i <= items.size(); i++) {
                D_GuiQuad cell = getQuads().get(i);
                if (i >= windowStart && i <= windowStop) {
                    cell.style.setCenter(this.style.getCenterX(), y);
                    y -= this.style.getHeight();
                    cell.setVisible(this.isSelected());
                } else {
                    cell.setVisible(false);
                }
            }
        }

        if (selected != null) {
            if (!this.isSelected()) {
                selected.setVisible(this.isVisible());
                selected.style.setCenter(style.getCenter());
                selected.setHoverable(false);
                container.setVisible(false);
            } else {
                container.setVisible(this.isVisible());
                selected.setHoverable(this.isVisible());
            }
        }
    }

    @Override
    protected void onUpdate() { }

    private void onKeyPress(D_Event event) {
        D_GuiKeyEvent e = (D_GuiKeyEvent) event;
        if (!e.isAction(GLFW_PRESS, GLFW_REPEAT)) return;
        int key = e.getKey();
        if (key == GLFW_KEY_ESCAPE)
            this.setSelected(false);
        else if (key == GLFW_KEY_DOWN && isSelected()) {
            if (index == 0) index = 1;
            getQuads().get(index).style.setBorderWidth(0);
            index++;
            if (index > items.size()) index = items.size();
            getQuads().get(index).style.setBorderWidth(1);
            if (index >= windowStop)
                updateScrollWindow(1);
        } else if (key == GLFW_KEY_UP && isSelected()) {
            if (index == 0) index = 1;
            getQuads().get(index).style.setBorderWidth(0);
            index--;
            if (index <= 0) index = 1;
            getQuads().get(index).style.setBorderWidth(1);
            if (index <= windowStart)
                updateScrollWindow(-1);
        } else if (key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) {
            if (this.isSelected())
                onSelect(getQuads().get(index));
            else
                this.setSelected(true);
        }
    }

    private void onSelect(D_GuiQuad gui) {
        D_GuiQuad previous = selected;
        selected.setHoverable(true);
        selected = gui;
        selected.style.setBorderWidth(0);
        this.setSelected(false);
        this.stackEvent(new D_GuiValueChangeEvent<>(this, items.get(previous.getText()), items.get(selected.getText())));
    }

    private void onScroll(D_Event<D_Gui> event) {
        if (items.size() < windowSize) return;
        D_GuiScrollEvent e = (D_GuiScrollEvent) event;
        updateScrollWindow(-(int) e.getYoffset());
    }

    private void updateScrollWindow(int offset) {
        windowStart += offset;
        windowStop = windowStart + windowSize - 1;

        if (windowStop >= items.size()) {
            windowStop = items.size();
            windowStart = windowStop - Math.min(items.size(), windowSize - 1);
        }

        if (windowStart <= 1) {
            windowStart = 1;
            windowStop = windowStart + Math.min(items.size(), windowSize - 1);
        }
        style.notifyObservers();
    }

    private void onSizeChange(D_Event<D_Gui> d_event) {
        this.container.getStyle().setSize(this.getStyle().getWidth(), this.style.getHeight() * Math.min(items.size(), windowSize));
    }

    private static void onQuadResize(D_Event<D_Gui> e) {
        D_GuiResizeEvent event = (D_GuiResizeEvent) e;
        D_GuiQuad quad = (D_GuiQuad) e.getSource();
        if(quad.getTextBox() != null) {
            float fontSize = quad.getTextBox().getFontSize();
            float prevHeight = event.getPreviousHeight();
            prevHeight = prevHeight == 0 ? 1 : prevHeight;
            float fontToHeightRatio = fontSize / prevHeight;

            quad.getTextBox().setFontSize(fontToHeightRatio * event.getCurrentHeight());
        }
    }
}
