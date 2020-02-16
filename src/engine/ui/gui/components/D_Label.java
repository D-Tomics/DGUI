package engine.ui.gui.components;

import engine.ui.IO.Window;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignTop;
import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.observers.Observable;

public class D_Label extends D_Component{

    private D_TextBox text;

    public D_Label() {
        this("");
    }

    public D_Label(String text) {
        this(0,0,text);
    }

    public D_Label(float x, float y, String text) {
        this.text = new D_TextBox(text,50, Window.INSTANCE.getWidth(), Window.INSTANCE.getHeight());
        this.text.setPosition(x,y);
        this.addText(this.text);

        style.setX(x).setY(y);
        style.setWidth(this.text.getMaxTextWidth());
        style.setHeight(this.text.getMaxTextHeight());
        style.setAlpha(0);
        style.setBorderSize(0);
        this.addConstraint(new D_TextAlignTop( this.text,-2));
    }

    public String getText() { return text.getText(); }
    public D_TextBox getTextObject() { return text; }

    public void setFontSize(float fontSize) {
        text.setFontSize(fontSize);
    }

    public void setText(String text) {
        this.text.setText(text);
        this.style.setSize(this.text.getMaxTextWidth(), this.text.getMaxTextHeight());
        if (this.getParent() != null) {
            this.getParent().getStyle().notifyObservers();
        }
    }

    public void setTextColor(float r, float g, float b) {
        this.text.setTextColor(r,g,b);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    public void onStateChange(Observable o) {
        text.setPosition(style.getX(),style.getY());
    }
}
