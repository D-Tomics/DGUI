package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignLeft;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignTop;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.utils.observers.Observable;

/**This class represents a text in a gui system.
 * If a text is required it is added via this class.
 *
 * @author Abdul Kareem
 *
 */
public class D_Label extends D_Component{

    private D_TextBox text;

    public D_Label() {
        this("");
    }

    public D_Label(String text) {
        this(0,0,text);
    }

    public D_Label(float x, float y, String text) {
        this.text = new D_TextBox(text,50, Window.getMonitorWidth(), Window.getMonitorHeight());
        this.text.setPosition(x,y);
        this.addText(this.text);

        style.setX(x).setY(y);
        style.setWidth(this.text.getMaxTextWidth());
        style.setHeight(this.text.getMaxTextHeight());
        style.setAlpha(0);
        style.setBorderWidth(0);
        this.addConstraint(new D_TextAlignLeft(this.text, 0, new D_TextAlignTop(this.text, 0)));
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
    public void onStateChange(Observable o) { }
}
