package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import org.dtomics.DGUI.gui.manager.events.D_GuiResizeEvent;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.text.meshCreator.TextAlignment;
import org.dtomics.DGUI.utils.D_Event;
import org.dtomics.DGUI.utils.observers.Observable;

/**
 * This class represents a text in a gui system.
 * If a text is required it is added via this class.
 *
 * @author Abdul Kareem
 */
public class D_Label extends D_Component {

    private static final int WIDTH = 75;
    private static final int HEIGHT = 30;

    private final D_TextBox text;

    public D_Label() {
        this("");
    }

    public D_Label(String text) {
        this(0, 0, text);
    }

    public D_Label(float x, float y, String text) {
        this.text = new D_TextBox(text, 50, Window.getMonitorWidth(), Window.getMonitorHeight());
        this.text.setPosition(x, y);
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.addText(this.text);


        this.addEventListener(D_GuiResizeEvent.class, this::onSizeChange);
        style.setBounds(x,y,this.text.getMaxTextWidth(),this.text.getMaxTextHeight())
             .setAlpha(0)
             .setBorderWidth(0);

        this.addConstraint(new D_TextAlignCenter(this.text));
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
        this.style.setSize(this.text.getMaxTextWidth(), this.text.getMaxTextHeight());
        if (this.getParent() != null) {
            this.getParent().getStyle().notifyObservers();
        }
    }

    public D_TextBox getTextObject() {
        return text;
    }

    public void setFontSize(float fontSize) {
        text.setFontSize(fontSize);
    }

    public void setTextColor(float r, float g, float b) {
        this.text.setTextColor(r, g, b);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    public void onStateChange(Observable o) {
    }

    private void onSizeChange(D_Event<D_Gui> event) {
        D_GuiResizeEvent e = (D_GuiResizeEvent) event;
        text.setBoxSize(e.getCurrentWidth(), e.getCurrentHeight());
        style.notifyObservers();
    }
}
