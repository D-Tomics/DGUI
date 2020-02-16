package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlign;
import engine.ui.gui.manager.events.D_GuiResizeEvent;
import engine.ui.gui.manager.events.D_GuiScrollEvent;
import engine.ui.utils.D_Event;
import engine.ui.utils.observers.Observable;
import org.lwjgl.glfw.GLFW;

import static engine.ui.IO.Mouse.MOUSE_BUTTON_LEFT;

public class D_TextArea extends D_TextComponent {

    private int maxNumOfLines;
    private int verticalWindowStart;

    public D_TextArea(String text, int rows, int cols) {
        super(text, rows, cols);
        this.setScrollable(true);

        this.maxNumOfLines = (int) (style.getHeight() / textBox.getMesh().getData().getLineHeight());

        this.addConstraint(new D_TextAlign(textBox, 5, 5, 5, 5));

        this.addEventListener(D_GuiScrollEvent.class, this::onScrollEvent);
        this.addEventListener(D_GuiResizeEvent.class, this::onResize);
    }

    @Override
    protected void onUpdate() { }

    @Override
    protected void onStateChange(Observable o) { }

    @Override
    protected void onMousePress(int button) {
        if(button == MOUSE_BUTTON_LEFT)
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
        this.maxNumOfLines = (int)(style.getHeight() / textBox.getMesh().getData().getLineHeight());
        style.notifyObservers();
    }

    private void onScrollEvent(D_Event event) {
        var e = (D_GuiScrollEvent)event;
        if(textBox.getMaxTextHeight() > getStyle().getHeight()) {
            float offset = textBox.getMesh().getData().getLineHeight() * (float) e.getYoffset();

            if(textBox.getOffset().y - offset < 0 || textBox.getOffset().y - offset >= textBox.getMaxTextHeight()) offset = 0;
            else verticalWindowStart -= e.getYoffset();

            textBox.setOffset(textBox.getOffset().x, textBox.getOffset().y - offset);
        }
        style.notifyObservers();
    }

    private void updateVerticalScrolling() {
        if(cursor.getRow() < verticalWindowStart) {
            int drow = verticalWindowStart - cursor.getRow();
            verticalWindowStart -= drow; // window start - row would give offset rows with windowStart as a reference
            textBox.getOffset().sub(0, drow * textBox.getMesh().getData().getLineHeight());
        } else if (cursor.getRow() > verticalWindowStart + maxNumOfLines - 1) {
            int drow = cursor.getRow() - verticalWindowStart - maxNumOfLines + 1;
            verticalWindowStart += drow;
            textBox.getOffset().add(0, drow * textBox.getMesh().getData().getLineHeight());
        }
    }

}