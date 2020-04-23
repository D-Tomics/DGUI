package engine.ui.gui.components;

import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import engine.ui.gui.manager.events.D_GuiMouseMoveEvent;
import engine.ui.gui.manager.events.D_GuiMousePressEvent;
import engine.ui.gui.manager.events.D_GuiResizeEvent;
import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;
import opengl.opengl.OpenGL;
import opengl.opengl.primitives.Rect;

import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;

public class D_GuiQuad extends D_Component {

    private D_TextBox textBox;
    D_GuiQuad() { }
    D_GuiQuad(float width, float height)                               { this.init(width, height, null, null);}
    D_GuiQuad(float width, float height, String text)                  { this.init(width, height, text, Color.BLACK);        }
    D_GuiQuad(float width, float height, String text, Color textColor) { this.init(width, height, text, textColor);          }

    private void setText(String text, Color color) {
        if(text == null) return;
        if(textBox == null)
            textBox = new D_TextBox(text,60,style.getWidth(),style.getHeight(), true);
        else
            textBox.setText(text);
        textBox.setTextColor(color);
        this.addText(textBox);
    }

    public String    getText()    { return textBox.getText(); }
    public D_TextBox getTextBox() { return textBox; }

    private void init(float width, float height, String text, Color textColor) {
        this.style.setSize(width,height);
        this.setText(text,textColor);
        this.addConstraint(new D_TextAlignCenter(textBox));
        this.addEventListener(D_GuiResizeEvent.class, event -> {
            D_GuiResizeEvent e = (D_GuiResizeEvent)event;
            textBox.setBoxSize(e.getCurrentWidth(), e.getCurrentHeight());
        });
    }

    @Override
    protected void onUpdate() { }

    @Override
    public void onStateChange(Observable o) { }

}
