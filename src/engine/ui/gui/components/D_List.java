package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.animation.D_GuiAnimation;
import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.observers.Observable;

import java.util.HashMap;

public class D_List<T> extends D_Component{

    private static final float CELL_WIDTH = 100;
    private static final float CELL_HEIGHT = 30f;

    private HashMap<D_Geometry, Object> items;

    private D_TextBox selectedText;
    private T selectedItem;

    public D_List(T...items) {
        this.setSelectable(true);

        this.selectedText = new D_TextBox("", 0.2f, CELL_WIDTH, CELL_HEIGHT);
        this.selectedText.setPosition(this.getStyle().getCenter());
        this.selectedText.setTextColor(0,0,0);

        this.getStyle().setBounds(0,0,CELL_WIDTH,CELL_HEIGHT);

        addItem(items);
    }

    public void addItem(T...items) {
        for(T item : items)
            addItem(item);
    }

    public void addItem(T item) {
        if(items == null) items = new HashMap<>();
        if(item == null) return;

        D_Geometry cell = new D_Geometry();
        cell.setSelectable(true);
        cell.getStyle().setBounds(0,0,CELL_WIDTH,CELL_HEIGHT);
        cell.getStyle().setColor(200,200,200);
        cell.setVisible(false);

        cell.addAnimation(new D_GuiAnimation() {
            @Override
            protected boolean run(D_Gui gui) {
                if(cell.isHovered()) {
                    cell.getStyle().setColor(0x11AA00);
                } else {
                    cell.getStyle().setColor(0xAABBCC);
                }
                return false;
            }
        });

        cell.setText(item.toString());
        cell.getText().setFontSize(0.2f);
        cell.getText().setTextColor(0,0,0);
        super.addGeometry(cell);

        items.put(cell,item);
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void onStateChange(Observable o) {

    }

    @Override
    protected void onUpdate() {

        selectedText.setVisible(!this.isSelected());
        if(getGeometries() != null) {
            boolean hover = false;

            float y = this.style.getY() - CELL_HEIGHT;
            for(var cell : getGeometries()) {
                cell.getStyle().setPosition(this.style.getX(), y);
                cell.setVisible(this.isSelected());
                if(cell.isPressed() && cell.isVisible()) {
                    selectedText.setText(cell.getText().getText());
                    selectedItem = (T)items.get(cell);
                    this.setSelected(false);
                }

                y -= cell.getStyle().getHeight();
                hover |= cell.isHovered();
            }

            if(Mouse.pressed() ) {
                if(!hover && !this.isHovered()) this.setSelected(false);
            }
        }

    }

}
