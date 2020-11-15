package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Cursors;
import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.gui.manager.events.D_GuiCharEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMousePressEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiValueChangeEvent;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.utils.D_Event;
import org.dtomics.DGUI.utils.colors.Color;

import static org.dtomics.DGUI.IO.Mouse.MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

/**
 * This is an abstract class that represents components which lets the user edit its text graphically
 *
 * @author Abdul Kareem
 */
public abstract class D_TextComponent extends D_Component {

    protected D_TextBox textBox;
    protected Cursor cursor;
    private float horizontalWindowStart;

    D_TextComponent(String text, int width, int height) {
        this.textBox = new D_TextBox(text, 50, width, height);
        this.textBox.setTextColor(Color.BLACK);
        this.textBox.setCharWidth(0.4f);
        this.textBox.setCharEdge(0.25f);
        this.addText(this.textBox);

        this.cursor = new Cursor(textBox);
        this.addQuad(cursor);

        this.style.setSize(width, height);
        this.style.setBgColor(Color.WHITE);
        this.style.setBorderColor(Color.BLACK);
        this.style.setCursor(Cursors.I_BEAM.get());

        this.textBox.setBoxHeight(this.style.getHeight());

        this.addEventListener(D_GuiCharEvent.class, this::onCharEvent);
        this.addEventListener(D_GuiKeyEvent.class, this::onKeyEvent);
        this.addEventListener(D_GuiMousePressEvent.class, this::onMouseEvent);
    }

    public String getText() {
        return textBox.getText();
    }

    public void setText(String text) {
        this.stackEvent(new D_GuiValueChangeEvent<String>(this, this.textBox.getText(), text));
        this.textBox.setText(text);
        style.notifyObservers();
    }

    public void appendText(String text) {
        String oldText = this.textBox.getText();
        this.textBox.appendText(text);
        this.stackEvent(new D_GuiValueChangeEvent<String>(this, oldText, this.textBox.getText()));
        style.notifyObservers();
    }

    public Color getTextColor() {
        return textBox.getTextColor();
    }

    public void setTextColor(Color color) {
        this.textBox.setTextColor(color);
        style.notifyObservers();
    }

    public void setFontSize(float fontSize) {
        this.textBox.setFontSize(fontSize);
        this.cursor.getStyle().setHeight(textBox.getMeshData().getLineHeight());
        style.notifyObservers();
    }

    protected void onKeyPress(int key) { }
    protected void onMousePress(int button) { }

    protected void insertCharAtCursor(char c) {
        int index = Math.min(getLengthUptoRow(cursor.getRow() - 1) + cursor.getCol(), textBox.getText().length());
        setText(textBox.getText().substring(0, index) + c + textBox.getText().substring(index));
    }

    private void onCharEvent(D_Event<D_Gui> e) {
        insertCharAtCursor((char) ((D_GuiCharEvent) e).getCodePoint());
        cursor.moveRight(1);
        updateHorizontalScroll();
        style.notifyObservers();
    }

    private void onKeyEvent(D_Event<D_Gui> e) {
        D_GuiKeyEvent event = (D_GuiKeyEvent) e;

        cursor.blinkDelay();
        cursor.setVisible(true);
        if(!event.isAction(GLFW_PRESS, GLFW_REPEAT)) return;
        switch (event.getKey()) {
            case GLFW_KEY_BACKSPACE: onBackSpace();     break;
            case GLFW_KEY_DELETE:    onDelete();        break;
            case GLFW_KEY_UP:        cursor.moveUp();   break;
            case GLFW_KEY_DOWN:      cursor.moveDown(); break;
            case GLFW_KEY_LEFT:      cursor.moveLeft(0);break;
            case GLFW_KEY_RIGHT:     cursor.moveRight(0);break;
        }
        onKeyPress(event.getKey());

        updateHorizontalScroll();
        style.notifyObservers();
    }

    private void onMouseEvent(D_Event<D_Gui> event) {
        D_GuiMousePressEvent e = (D_GuiMousePressEvent) event;

        cursor.blinkDelay();
        cursor.setVisible(true);
        if (e.isButton(MOUSE_BUTTON_LEFT)) {
            for (int i = 0; i < textBox.getLine(cursor.getRow()).length(); i++) {
                if (Mouse.getX() - textBox.getPosition().x <= textBox.getLine(cursor.getRow()).getWidth(i)) {
                    cursor.setCol(i + 1);//col = i + 1;
                    break;
                } else if (Mouse.getX() - textBox.getPosition().x > textBox.getLine(cursor.getRow()).getWidth(textBox.getLine(cursor.getRow()).length() - 1)) {
                    cursor.setCol(textBox.getLine(cursor.getRow()).length());//col = textBox.getLine(row).length();
                }
            }
        }
        onMousePress(e.getButton());
        this.style.notifyObservers();
    }

    private void updateHorizontalScroll() {
        if (!textBox.isWrapped()) {
            float width = textBox.getLine(cursor.getRow()).getWidth(cursor.getCol() - 1);
            if (width > horizontalWindowStart + textBox.getBoxWidth()) {
                float dw = width - horizontalWindowStart - textBox.getBoxWidth();
                horizontalWindowStart += dw;
                textBox.getOffset().sub(dw, 0);
            } else if (width < horizontalWindowStart) {
                float dw = horizontalWindowStart - width;
                horizontalWindowStart -= dw;
                textBox.getOffset().add(dw, 0);
            }
        }
    }

    private void onBackSpace() {
        if (textBox.getText().length() == 0) return;
        int index = Math.min(textBox.getText().length(), getLengthUptoRow(cursor.getRow() - 1) + cursor.getCol());
        int curRowLength = textBox.getLineLength(cursor.getRow());
        if (cursor.getCol() - 1 >= 0 || index - 1 >= 0 && textBox.getText().charAt(index - 1) == '\n') {
            setText(textBox.getText().substring(0, Math.max(index - 1, 0)) + textBox.getText().substring(index));
        }
        cursor.moveLeft(-curRowLength);
    }

    private void onDelete() {
        int length = getLengthUptoRow(cursor.getRow() - 1);
        if (length + cursor.getCol() <= textBox.getText().length()) {
            String beginning = textBox.getText().substring(0, length + cursor.getCol());
            String ending    = textBox.getText().substring(Math.min(length + cursor.getCol() + 1, textBox.getText().length()));
            setText(beginning.concat(ending));
        }
    }

    private int getLengthUptoRow(int row) {
        if (row < 0 || row >= textBox.getNumOfLines()) return 0;
        int length = 0;
        for (int i = 0; i <= row; i++)
            length += textBox.getLine(i).length() + (textBox.getLine(i).contains("\n") ? 1 : 0);
        return length;
    }
}
