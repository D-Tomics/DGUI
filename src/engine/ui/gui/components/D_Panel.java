package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.layouts.Layout;
import engine.ui.utils.observers.Observable;

public class D_Panel extends D_Container {

    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 100;

    private float offsetX;
    private float offsetY;

    public D_Panel(String title) {
        this.style.setBounds(0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.style.setBorderSize(1f);
        this.style.setName(title);
        this.style.setColor(0.1f,0.1f,0.1f);
        this.style.setAlpha(0.8f);
    }

    public D_Panel(String title, Layout layout) {
        this(title);
        this.setLayout(layout);
    }

    @Override
    public void onUpdate() {
        if(isResizable())
            onResize();
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

    @Override
    public void onStateChange(Observable o) {
        this.getLayout().update(this);
    }

    @Override
    public String toString() { return style.getName(); }

}
