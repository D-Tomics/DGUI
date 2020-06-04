package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.layouts.Layout;

/**D_Panel represents a generic container can hold components specified by the user
 *
 * @author Abdul Kareem
 */
public class D_Panel extends D_Container {

    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 100;

    private float offsetX;
    private float offsetY;

    public D_Panel() {
        this.style.setBounds(0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.style.setBorderSize(1f);
        this.style.setBgColor(0.1f,0.1f,0.1f);
        this.style.setAlpha(0.8f);
    }

    public D_Panel(Layout layout) {
        this();
        this.setLayout(layout);
    }

    @Override
    public void onUpdate() {
        if (getParent() == null  && this.isPressed())
        {
            if(offsetX == 0) offsetX =  style.getX() - Mouse.getX();
            if(offsetY == 0) offsetY = style.getY() - Mouse.getY();

            this.style.setX(Mouse.getX() +  offsetX);
            this.style.setY(Mouse.getY() + offsetY);
        }
        else
        {
            offsetX = 0;
            offsetY = 0;
        }
    }

}
