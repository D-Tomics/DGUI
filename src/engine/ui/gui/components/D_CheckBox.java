package engine.ui.gui.components;

import engine.ui.utils.observers.Observable;

/**
 * This class represents a gui that is selectable
 *
 * @author Abdul Kareem
 */
public class D_CheckBox extends D_Component {


    public D_CheckBox() {
        this(false);
    }

    public D_CheckBox(boolean selected) {
        this.style.setSize(10,10,false);
        this.setSelectable(true);
        this.setSelected(selected);
    }
    @Override
    public void onUpdate() {
        if(this.isSelected()) {
            style.setBgColor(0x11AA00); // 17 170 0
        } else {
            style.setBgColor(0xAABBCC); // 170 187 204
        }
    }

    @Override
    public void onStateChange(Observable o) {
    }
}
