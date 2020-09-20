package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.utils.colors.Color;
import org.dtomics.DGUI.utils.observers.Observable;

/**
 * This represents a pressable button with a name in a gui system.
 *
 * @author Abdul Kareem
 */
public class D_Button extends D_Component{

    private static final float WIDTH = 100, HEIGHT = 30;

    private D_TextBox name;
    public D_Button(String name) {

        this.name = new D_TextBox(name,75,WIDTH,HEIGHT, false);
        this.name.setTextColor(Color.BLACK);
        this.addText(this.name);
        this.addConstraint(new D_TextAlignCenter(this.name));

        style.setBounds(0,0,WIDTH,HEIGHT);
    }

    @Override
    public void onUpdate() {
        if(this.isHovered()) {
            style.setBgColor(170,167,224);
        }
        if(this.isPressed()) {
            style.setBgColor(200,200,200);
        }

        if(!isHovered() && !isPressed()) {
            style.setBgColor(170,187,204);
        }

    }

    @Override
    public void onStateChange(Observable o) { }
}