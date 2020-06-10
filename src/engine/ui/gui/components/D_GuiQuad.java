package engine.ui.gui.components;

import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextFill;
import engine.ui.gui.manager.events.D_GuiResizeEvent;
import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;

/**
 * This class is used when a gui requires additional geometry.
 * A quad can be added to a gui via {@code gui.addQuad} method
 *
 * @author Abdul Kareem
 */
public class D_GuiQuad extends D_Component {

    private D_TextBox textBox;
    public D_GuiQuad() { }
    public D_GuiQuad(float width, float height)                               { this.init(width, height, null, null);}
    public D_GuiQuad(float width, float height, String text)                  { this.init(width, height, text, Color.BLACK);        }
    public D_GuiQuad(float width, float height, String text, Color textColor) { this.init(width, height, text, textColor);          }

    public void setText(String text, Color color) {
        if(text == null) return;
        if(textBox == null)
            textBox = new D_TextBox(text,60,style.getWidth(),style.getHeight(), false);
        else
            textBox.setText(text);
        textBox.setTextColor(color);
        this.addText(textBox);
    }

    public String    getText()    { return textBox != null ? textBox.getText() : null; }
    public D_TextBox getTextBox() { return textBox; }

    private void init(float width, float height, String text, Color textColor) {
        this.style.setSize(width,height);
        this.setText(text,textColor);
        this.addConstraint(new D_TextAlignCenter(textBox, new D_TextFill(this.textBox,0,0)));
        this.addEventListener(D_GuiResizeEvent.class, event -> this.style.notifyObservers());
    }

    @Override
    protected void onUpdate() { }

    @Override
    public void onStateChange(Observable o) { }

}
