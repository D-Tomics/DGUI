package engine.ui.gui.components;

import engine.ui.gui.manager.events.D_GuiMousePressEvent;
import engine.ui.utils.observers.Observable;

import java.util.HashMap;

public class D_List<T> extends D_Component{

    private static final float CELL_WIDTH = 100;
    private static final float CELL_HEIGHT = 30f;

    private HashMap<String, T> items;

    private D_Geometry selected;
    public D_List(T...items) {
        this.style.setBounds(0,0,CELL_WIDTH,CELL_HEIGHT);
        this.setSelectable(true);
        addItem(items);
    }

    public T getSelectedItem() {
        return items.get(selected.getText().getText());
    }

    public void addItem(T...items) {
        for(T item : items)
            addItem(item);
    }

    public void addItem(T item) {
        if(items == null) items = new HashMap<>();
        if(items.containsKey(item.toString())) return;
        items.put(item.toString(),item);

        D_Geometry geometry = new D_Geometry(CELL_WIDTH, CELL_HEIGHT, item.toString());
        addGeometry(geometry);
        geometry.addEventListener(D_GuiMousePressEvent.class, e -> {
            selected.setHoverable(true);
            selected = (D_Geometry) e.getSource();
            this.setSelected(false);
        });

        if(selected == null) {
            selected = geometry;
            selected.setVisible(true);
            selected.style.setPosition(style.getX(),style.getY());
        }
    }

    @Override
    public void onStateChange(Observable o) {
        if(this.getGeometries() != null) {
            float y = style.getY() - CELL_HEIGHT;
            for(D_Geometry cell : getGeometries()) {
                cell.style.setPosition(this.style.getX(), y);
                y -= CELL_HEIGHT;
                cell.setVisible(this.isSelected());
            }
        }

        if(selected != null) {
            if(!this.isSelected()) {
                selected.setVisible(true);
                selected.style.setPosition(style.getX(),style.getY());
                selected.setHoverable(false);
            } else
                selected.setHoverable(true);
        }
    }

    @Override
    protected void onUpdate() {
    }

}
