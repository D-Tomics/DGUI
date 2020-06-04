package engine.ui.gui.components;

import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;

/**
 * This represents a pressable button with a name in a gui system.
 *
 * @author Abdul Kareem
 */
public class D_Button extends D_Component{

    private static final float WIDTH = 100, HEIGHT = 30;

    private D_TextBox name;
    public D_Button(String name) {

        this.name = new D_TextBox(name,75,WIDTH,HEIGHT, true);
        this.name.setTextColor(Color.BLACK);
        this.addText(this.name);

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
    public void onStateChange(Observable o) {
        name.setPosition(
                style.getX(),
                style.getY()
        );
    }
}