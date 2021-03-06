package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignLeft;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignTop;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextFill;
import org.dtomics.DGUI.gui.manager.events.D_GuiResizeEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiScrollEvent;
import org.dtomics.DGUI.utils.D_Event;
import org.dtomics.DGUI.utils.observers.Observable;
import org.lwjgl.glfw.GLFW;

import static org.dtomics.DGUI.IO.Mouse.MOUSE_BUTTON_LEFT;

/**
 * This class lets the user type a text line by line in a specified area that spans over multiple lines.
 *
 * @author Abdul Kareem
 */
public class D_TextArea extends D_TextComponent {

    private int maxNumOfLines;
    private int verticalWindowStart;

    public D_TextArea(String text, int rows, int cols) {
        super(text, rows, cols);
        this.setScrollable(true);

        this.maxNumOfLines = (int) (style.getHeight() / textBox.getMeshData().getLineHeight());

        this.addConstraint(new D_TextAlignTop(this.textBox, 0, new D_TextAlignLeft(this.textBox, 5, new D_TextFill(this.textBox, 10, 0))));

        this.addEventListener(D_GuiScrollEvent.class, this::onScrollEvent);
        this.addEventListener(D_GuiResizeEvent.class, this::onResize);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onStateChange(Observable o) {
    }

    @Override
    protected void onMousePress(int button) {
        if (button == MOUSE_BUTTON_LEFT)
            cursor.setRow(
                    (int) Math.min(
                            textBox.getNumOfLines() - 1,
                            (style.getY() - Mouse.getY()) / textBox.getLineHeight() + verticalWindowStart
                    )
            );
        //row = (int) Math.min(textBox.getNumOfLines() - 1, (style.getY() - Mouse.getY()) / textBox.getLineHeight() + windowStart);
    }

    @Override
    protected void onKeyPress(int key) {
        switch (key) {
            case GLFW.GLFW_KEY_ENTER:
            case GLFW.GLFW_KEY_KP_ENTER:
                insertCharAtCursor('\n');
                cursor.moveDown();
                cursor.setCol(0);
                updateVerticalScrolling();
                break;
        }
        updateVerticalScrolling();
    }

    private void onResize(D_Event e) {
        this.maxNumOfLines = (int) (style.getHeight() / textBox.getMeshData().getLineHeight());
        style.notifyObservers();
    }

    private void onScrollEvent(D_Event event) {
        D_GuiScrollEvent e = (D_GuiScrollEvent) event;
        if (textBox.getMaxTextHeight() > getStyle().getHeight()) {
            float offset = textBox.getMeshData().getLineHeight() * (float) e.getYoffset();

            if (textBox.getOffset().y - offset < 0 || textBox.getOffset().y - offset >= textBox.getMaxTextHeight())
                offset = 0;
            else verticalWindowStart -= e.getYoffset();

            textBox.setOffset(textBox.getOffset().x, textBox.getOffset().y - offset);
        }
        style.notifyObservers();
    }

    private void updateVerticalScrolling() {
        if (cursor.getRow() < verticalWindowStart) {
            int drow = verticalWindowStart - cursor.getRow();
            verticalWindowStart -= drow; // window start - row would give offset rows with windowStart as a reference
            textBox.getOffset().sub(0, drow * textBox.getMeshData().getLineHeight());
        } else if (cursor.getRow() > verticalWindowStart + maxNumOfLines - 1) {
            int drow = cursor.getRow() - verticalWindowStart - maxNumOfLines + 1;
            verticalWindowStart += drow;
            textBox.getOffset().add(0, drow * textBox.getMeshData().getLineHeight());
        }
    }

}