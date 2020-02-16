package engine.ui.gui.components;

import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;

public class D_Button extends D_Component{

    private static final float WIDTH = 100, HEIGHT = 30;

    private D_TextBox name;
    private Color pressedColor = new Color(0.1f,0.1f,0.1f);
    public D_Button(String name) {

        this.name = new D_TextBox(name,75,WIDTH,HEIGHT, true);
        this.name.setTextColor(Color.BLACK);
        this.addText(this.name);

        style.setBounds(0,0,WIDTH,HEIGHT);
    }

    @Override
    public void onUpdate() {
        if(this.isHovered()) {
            style.setColor(170,167,224);
        }
        if(this.isPressed()) {
            style.setColor(200,200,200);
            style.setWidth(style.getWidth() + 0.01f);
        }

        if(!isHovered() && !isPressed()) {
            style.setColor(170,187,204);
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