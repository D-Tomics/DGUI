package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.gui.manager.events.D_GuiCharEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMousePressEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiValueChangeEvent;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.utils.D_Event;
import org.dtomics.DGUI.utils.colors.Color;
import org.lwjgl.glfw.GLFW;

import static org.dtomics.DGUI.IO.Mouse.MOUSE_BUTTON_LEFT;

/**
 * This is an abstract class that represents components which lets the user edit its text graphically
 *
 * @author Abdul Kareem
 */
public abstract class D_TextComponent extends D_Component {

    private static final int ONE_COL_SIZE = 50;//pixels
    protected D_TextBox textBox;
    protected Cursor cursor;
    private float horizontalWindowStart;

    D_TextComponent(String text, int rows, int cols) {
        this.textBox = new D_TextBox(text, 65, cols * ONE_COL_SIZE, 100);
        this.textBox.setTextColor(Color.BLACK);
        this.textBox.setCharWidth(0.4f);
        this.textBox.setCharEdge(0.25f);
        this.addText(this.textBox);

        this.cursor = new Cursor(textBox);
        this.addQuad(cursor);

        this.style.setSize(cols * ONE_COL_SIZE, rows * textBox.getMeshData().getLineHeight());
        this.style.setBgColor(Color.WHITE);
        this.style.setBorderColor(Color.BLACK);

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

    public Color getTextColor() {
        return textBox.getTextColor();
    }

    public void setTextColor(Color color) {
        this.textBox.setTextColor(color);
        style.notifyObservers();
    }

    public void appendText(String text) {
        this.textBox.setText(textBox.getText() + text);
    }

    public void setFontSize(float fontSize) {
        this.textBox.setFontSize(fontSize);
        this.cursor.getStyle().setHeight(textBox.getMeshData().getLineHeight());
        style.notifyObservers();
    }

    protected void onKeyPress(int key) {
    }

    protected void onMousePress(int button) {
    }

    protected void insertCharAtCursor(char c) {
        int index = Math.min(getLengthUptoRow(cursor.getRow() - 1) + cursor.getCol(), textBox.getText().length());
        textBox.setText(textBox.getText().substring(0, index) + c + textBox.getText().substring(index));
    }

    private int getLengthUptoRow(int row) {
        if (row < 0 || row >= textBox.getNumOfLines()) return 0;
        int length = 0;
        for (int i = 0; i <= row; i++)
            length += textBox.getLine(i).length() + (textBox.getLine(i).contains("\n") ? 1 : 0);
        return length;
    }

    private void onCharEvent(D_Event e) {
        insertCharAtCursor((char) ((D_GuiCharEvent) e).getCodePoint());
        cursor.moveRight(1);
        updateHorizontalScroll();
        style.notifyObservers();
    }

    private void onKeyEvent(D_Event e) {
        cursor.blinkDelay();
        cursor.setVisible(true);
        if (((D_GuiKeyEvent) e).isAction(GLFW.GLFW_PRESS, GLFW.GLFW_REPEAT)) {
            switch (((D_GuiKeyEvent) e).getKey()) {
                case GLFW.GLFW_KEY_BACKSPACE: {
                    if (textBox.getText().length() == 0) break;
                    int index = Math.min(textBox.getText().length(), getLengthUptoRow(cursor.getRow() - 1) + cursor.getCol());
                    int curRowLength = textBox.getLineLength(cursor.getRow());
                    if (cursor.getCol() - 1 >= 0 || index - 1 >= 0 && textBox.getText().charAt(index - 1) == '\n') {
                        textBox.setText(textBox.getText().substring(0, Math.max(index - 1, 0)) + textBox.getText().substring(index));
                    }
                    cursor.moveLeft(-curRowLength);
                }
                break;
                case GLFW.GLFW_KEY_DELETE: {
                    int length = getLengthUptoRow(cursor.getRow() - 1);
                    if (length + cursor.getCol() <= textBox.getText().length())
                        textBox.setText(
                                textBox.getText().substring(0, length + cursor.getCol()) +
                                        textBox.getText().substring(Math.min(length + cursor.getCol() + 1, textBox.getText().length()))
                        );
                }
                break;

                case GLFW.GLFW_KEY_UP:
                    cursor.moveUp();
                    break;
                case GLFW.GLFW_KEY_DOWN:
                    cursor.moveDown();
                    break;
                case GLFW.GLFW_KEY_LEFT:
                    cursor.moveLeft(0);
                    break;
                case GLFW.GLFW_KEY_RIGHT:
                    cursor.moveRight(0);
                    break;
            }
            onKeyPress(((D_GuiKeyEvent) e).getKey());
        }

        updateHorizontalScroll();
        style.notifyObservers();
    }

    private void onMouseEvent(D_Event event) {
        cursor.blinkDelay();
        cursor.setVisible(true);
        if (((D_GuiMousePressEvent) event).isButton(MOUSE_BUTTON_LEFT)) {
            for (int i = 0; i < textBox.getLine(cursor.getRow()).length(); i++) {
                if (Mouse.getX() - textBox.getPosition().x <= textBox.getLine(cursor.getRow()).getWidth(i)) {
                    cursor.setCol(i + 1);//col = i + 1;
                    break;
                } else if (Mouse.getX() - textBox.getPosition().x > textBox.getLine(cursor.getRow()).getWidth(textBox.getLine(cursor.getRow()).length() - 1)) {
                    cursor.setCol(textBox.getLine(cursor.getRow()).length());//col = textBox.getLine(row).length();
                }
            }
        }
        onMousePress(((D_GuiMousePressEvent) event).getButton());
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
}
