package engine.ui.gui.components;

import engine.ui.IO.Window;
import engine.ui.gui.manager.events.*;
import engine.ui.utils.D_Event;
import engine.ui.utils.abstractions.Listener;
import engine.ui.utils.observers.Observable;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public class D_List<T> extends D_Component{

    private static final float CELL_WIDTH = 100;
    private static final float CELL_HEIGHT = 30f;

    private final Listener ON_CELL_SELECT = e -> onSelect((D_GuiQuad) e.getSource());

    private HashMap<String, T> items;

    private D_GuiQuad container;
    private D_GuiQuad selected;

    private int windowSize = 5;
    private int windowStart = 1;
    private int windowStop = windowStart + windowSize - 1;

    public D_List(T...items) {
        this.setScrollable(true);
        this.style.setBounds(0,0,CELL_WIDTH,CELL_HEIGHT, false);
        this.container = new D_GuiQuad(CELL_WIDTH,CELL_HEIGHT * Math.min(items.length, windowSize));
        this.container.setVisible(false);
        this.container.setHoverable(false);
        this.addQuad(container);
        this.setSelectable(true);
        addItem(items);

        this.addEventListener(D_GuiKeyEvent.class,this::onKeyPress);
        this.addEventListener(D_GuiFocusLooseEvent.class, e -> this.setSelected(false));
        this.addEventListener(D_GuiScrollEvent.class, this::onScroll);
    }

    public T getSelectedItem() {
        return items.get(selected.getText());
    }
    public void setScrollWindowSize(int length) {
        windowSize = length;
        windowStop = windowStart + windowSize - 1;
    }

    public void addItem(T...items) {
        for(T item : items)
            addItem(item);
    }

    public void addItem(T item) {
        if(items == null) items = new HashMap<>();
        if(items.containsKey(item.toString())) return;
        items.put(item.toString(),item);

        D_GuiQuad quad = new D_GuiQuad(CELL_WIDTH - 2, CELL_HEIGHT - 2, item.toString());
        quad.addEventListener(D_GuiMousePressEvent.class, ON_CELL_SELECT);
        quad.style.setBorderSize(0);
        quad.setVisible(false);
        addQuad(quad);

        if(selected == null) {
            selected = quad;
            selected.setVisible(true);
            selected.style.setBorderSize(0);
            selected.style.setCenter(style.getCenter());
        }
    }

    @Override
    public void onStateChange(Observable o) {
        if(this.getQuads() != null) {
            container.style.setPosition(style.getX(), style.getY() - CELL_HEIGHT);
            if(container.style.getY() - container.style.getHeight() <= -Window.INSTANCE.getHeight() / 2.0f) {
                float dy = container.style.getY() - container.style.getHeight() + Window.INSTANCE.getHeight() / 2.0f;
                container.style.setPosition(style.getX(), style.getY() - dy);
            }

            if(container.style.getY() >= Window.INSTANCE.getHeight() / 2.0f) {
                float dy = container.style.getY() - Window.INSTANCE.getHeight() / 2.0f;
                container.style.setPosition(style.getX(), style.getY() - dy);
            }

            float y = container.style.getY() - CELL_HEIGHT / 2.0f;
            for(int i = 1; i <= items.size(); i++) {
                D_GuiQuad cell = getQuads().get(i);
                if(i >= windowStart && i <= windowStop) {
                    cell.style.setCenter(this.style.getCenterX(), y);
                    y -= CELL_HEIGHT;
                    cell.setVisible(this.isSelected());
                } else {
                    cell.setVisible(false);
                }
            }
        }

        if(selected != null) {
            if(!this.isSelected()) {
                selected.setVisible(true);
                selected.style.setCenter(style.getCenter());
                selected.setHoverable(false);
                container.setVisible(false);
            } else {
                container.setVisible(true);
                selected.setHoverable(true);
            }
        }
    }

    @Override
    protected void onUpdate() {
        if(getQuads() != null) {
            for(D_GuiQuad quad : getQuads()) {
                if(quad == container) continue;
                if(quad.isHovered()) {
                    index = 0;
                    quad.style.setBorderSize(1);
                }
                else if(index == 0)
                    quad.style.setBorderSize(0);
            }
        }
    }

    private int index = 1;
    private void onKeyPress(D_Event event) {
        D_GuiKeyEvent e = (D_GuiKeyEvent)event;
        if(!e.isAction(GLFW_PRESS,GLFW_REPEAT)) return;
        int key = e.getKey();
        if(key == GLFW_KEY_ESCAPE)
            this.setSelected(false);
        else if(key == GLFW_KEY_DOWN && isSelected()) {
            if(index == 0) index = 1;
            getQuads().get(index).style.setBorderSize(0);
            index++;
            if(index > items.size()) index = items.size();
            getQuads().get(index).style.setBorderSize(1);
            if(index >= windowStop)
                updateScrollWindow(1);
        } else if(key == GLFW_KEY_UP && isSelected()) {
            if(index == 0) index = 1;
            getQuads().get(index).style.setBorderSize(0);
            index--;
            if(index <= 0) index = 1;
            getQuads().get(index).style.setBorderSize(1);
            if(index <= windowStart)
                updateScrollWindow(-1);
        } else if(key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) {
            if(this.isSelected())
                onSelect(getQuads().get(index));
            else
                this.setSelected(true);
        }
    }

    private void onSelect(D_GuiQuad gui) {
        selected.setHoverable(true);
        selected = gui;
        selected.style.setBorderSize(0);
        this.setSelected(false);
    }

    private void onScroll(D_Event event) {
        if(items.size() < windowSize) return;
        D_GuiScrollEvent e = (D_GuiScrollEvent)event;
        updateScrollWindow(-(int) e.getYoffset());
    }

    private void updateScrollWindow(int offset) {
        windowStart += offset;
        windowStop = windowStart + windowSize - 1;

        if(windowStop >= items.size()) {
            windowStop = items.size();
            windowStart = windowStop - Math.min(items.size(), windowSize - 1);
        }

        if(windowStart <= 1) {
            windowStart = 1;
            windowStop = windowStart + Math.min(items.size(), windowSize - 1);
        }
        style.notifyObservers();
    }

}
