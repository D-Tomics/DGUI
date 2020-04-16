package engine.ui.gui.components;

import engine.ui.gui.manager.events.D_GuiFocusLooseEvent;
import engine.ui.gui.manager.events.D_GuiKeyPressEvent;
import engine.ui.gui.manager.events.D_GuiMousePressEvent;
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

    public D_List(T...items) {
        this.style.setBounds(0,0,CELL_WIDTH,CELL_HEIGHT);
        this.container = new D_GuiQuad(CELL_WIDTH,CELL_HEIGHT * items.length);
        this.container.setVisible(false);
        this.container.setHoverable(false);
        this.addQuad(container);
        this.setSelectable(true);
        addItem(items);

        this.addEventListener(D_GuiKeyPressEvent.class,this::onKeyPress);
        this.addEventListener(D_GuiFocusLooseEvent.class, e -> this.setSelected(false));
    }

    public T getSelectedItem() {
        return items.get(selected.getText());
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
            float y = style.getY() - 3 * CELL_HEIGHT / 2.0f;
            for(D_GuiQuad cell : getQuads()) {
                if(cell == container) continue;
                cell.style.setCenter(this.style.getCenterX(), y);
                y -= CELL_HEIGHT;
                cell.setVisible(this.isSelected());
            }
        }

        if(selected != null) {
            if(!this.isSelected()) {
                selected.setVisible(true);
                selected.style.setCenter(style.getCenter());
                selected.setHoverable(false);
                container.setVisible(false);
            } else {
                container.style.setPosition(style.getX(), style.getY() - CELL_HEIGHT);
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
    private void onKeyPress(D_Event e) {
        int key = ((D_GuiKeyPressEvent)e).getKey();
        if(key == GLFW_KEY_ESCAPE)
            this.setSelected(false);
        else if(key == GLFW_KEY_DOWN && isSelected()) {
            if(index == 0) index = 1;
            getQuads().get(index).style.setBorderSize(0);
            index++;
            if(index > items.size()) index = 1;
            getQuads().get(index).style.setBorderSize(1);
        } else if(key == GLFW_KEY_UP && isSelected()) {
            if(index == 0) index = 1;
            getQuads().get(index).style.setBorderSize(0);
            index--;
            if(index <= 0) index = items.size();
            getQuads().get(index).style.setBorderSize(1);
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

}
