package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.manager.LayoutManager;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignLeft;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignTop;
import engine.ui.utils.observers.Observable;

public class D_Panel extends D_Container {

    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_HEADER_HEIGHT = 20;

    private float offsetX;
    private float offsetY;

    private float headerHeight = DEFAULT_HEADER_HEIGHT;

    private D_TextBox headerText;
    private D_Geometry minimize;

    public D_Panel(String title) {
        this.minimize = new D_Geometry();
        this.minimize.setSelectable(true);
        this.minimize.style.setWidth(10).setHeight(10);
        this.minimize.style.setColor(200,200,200);
        this.minimize.setLevel(1);
        this.addGeometry(minimize);

        super.setMinimizable(true);

        this.headerText = new D_TextBox(title,0.25f,DEFAULT_WIDTH,headerHeight);
        headerText.setPosition(style.getX(),style.getY());
        headerText.setTextColor(0.7f,0.5f,0.7f);
        headerText.setVisible(false);

        this.addConstraint(new D_TextAlignLeft(headerText, minimize.style.getWidth() + 4));
        this.addConstraint(new D_TextAlignTop(headerText,0));

        this.style.setBounds(0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.style.setBorderSize(1f);
        this.style.setName(title);
        this.style.setColor(0.1f,0.1f,0.1f);
        this.style.setAlpha(0.8f);
    }

    public D_Panel(String title, LayoutManager layout) {
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

        if(minimize.isSelected()) {
            minimize();
        } else {
            maximize();
        }
    }

    @Override
    public void onStateChange(Observable o) {
        minimize.style.setPosition(style.getPosition(), false);
        this.getLayout().update(this);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        headerText.setVisible(visible);
    }

    @Override
    protected void setLevel(int level) {
        super.setLevel(level);
        this.minimize.setLevel(level + 1);
    }

    private void minimize() {
        if(isMinimized()) return;

        minimize.style.setColor(100,100,100);

        if(getChildList() != null) {
            for(var child : getChildList())
                child.setVisible(false);
        }

        setMinimized(true);
        this.style.notifyObservers();
        this.style.setHeight(headerHeight);
        if(getParent() != null)
            getParent().getStyle().notifyObservers();
    }

    private void maximize() {
        if(!super.isMinimized()) return;

        minimize.style.setColor(200,200,200);

        if(childList != null) {
            for(var child : childList) {
                child.setVisible(true);
                if(child instanceof D_Container) {
                    ((D_Container) child).hideChildren(((D_Container) child).isMinimized());
                }
            }
        }

        super.setMinimized(false);
        style.notifyObservers();
        if(getParent() != null)
            getParent().getStyle().notifyObservers();
    }

    @Override
    public String toString() { return style.getName(); }

}
