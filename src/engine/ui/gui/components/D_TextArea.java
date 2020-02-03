package engine.ui.gui.components;

import engine.ui.IO.Mouse;
import engine.ui.gui.manager.events.*;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.font.Fonts;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlign;
import engine.ui.utils.D_Event;
import engine.ui.utils.Delay;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;
import org.lwjgl.glfw.GLFW;

import static engine.ui.IO.Mouse.MOUSE_BUTTON_LEFT;

public class D_TextArea extends D_Component {

    private Color textColor = Color.BLACK;

    private int row = 0;
    private int col = 0;
    private int maxNumOfLines;

    private Delay halfSecond;
    private D_TextBox textBox;
    private D_Geometry cursor;

    public D_TextArea(String text, float width, float height) {
        this.setScrollable(true);
        this.style.setSize(width, height);
        this.style.setColor(Color.WHITE);
        this.style.setBorderColor(Color.BLACK);

        this.halfSecond = new Delay(500);
        this.textBox = new D_TextBox(text, 60, width, height, Fonts.Candara.getFont(), false);
        this.textBox.setTextColor(textColor);
        this.textBox.setCharWidth(0.4f);
        this.textBox.setCharEdge(0.25f);

        this.maxNumOfLines = (int) (style.getHeight() / textBox.getMesh().getData().getLineHeight());

        this.cursor = new D_Geometry();
        this.cursor.style.setSize(1, textBox.getMesh().getData().getLineHeight());
        this.cursor.style.setBorderSize(0);
        this.cursor.style.setColor(Color.BLACK);
        this.cursor.setVisible(false);
        this.addGeometry(cursor);


        this.addConstraint(new D_TextAlign(textBox, 5, 5, 5, 5));


        this.addEventListener(D_GuiCharEvent.class, this::onCharEvent);
        this.addEventListener(D_GuiKeyEvent.class, this::onKeyEvent);
        this.addEventListener(D_GuiScrollEvent.class, this::onScrollEvent);
        this.addEventListener(D_GuiMousePressEvent.class, this::onMousePress);
        this.addEventListener(D_GuiResizeEvent.class, this::onResize);

    }

    public String getText() { return textBox.toString(); }
    public Color getTextColor() { return textColor; }
    public D_TextBox getTextBox() { return textBox; }

    public void appendText(String text) { this.textBox.setText(textBox.getText() + text); }

    public void setFontSize(float fontSize) {
        this.textBox.setFontSize(fontSize);
        this.cursor.getStyle().setHeight(textBox.getMesh().getData().getLineHeight());
    }
    public void setText(String text) { this.textBox.setText(text); }
    public void setTextColor(Color color) { this.textColor.set(color); }

    @Override
    protected void onUpdate() {

        System.out.println("l : "+textBox.getText().length() + " col : "+col + " length : "+getLengthUptoRow(row - 1) + " : "+(getLengthUptoRow(row - 1) + col));
        if(this.isFocused()) {
            float cursorX = textBox.getPosition().x + (textBox.getLine(row) != null ? textBox.getLine(row).getWidth(col - 1) : 0 );
            float cursorY = textBox.getPosition().y - Math.min(row, maxNumOfLines) * textBox.getMesh().getData().getLineHeight();

            cursor.getStyle().setPosition(cursorX, cursorY + textBox.getOffset().y , false);

            if(halfSecond.over()) cursor.setVisible(!cursor.isVisible());
        } else
            cursor.setVisible(false);
    }

    @Override
    public void onStateChange(Observable o) { }

    private static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
    private void moveCursor(int dir) {
        if(dir == LEFT) {
            int rTemp = row;
            row = col - 1 < 0 ? Math.max(0,row - 1) : row;
            col = rTemp != row ? textBox.getLine(row).length() : Math.max(col - 1, 0);
        } else if(dir == RIGHT) {
            int rTemp = row;
            row = textBox.getLine(row + 1) != null && col + 1 > textBox.getLine(row).length() ? row + 1 : row;
            col = rTemp != row ?  textBox.getLine(row).length() : Math.min(col + 1, textBox.getLine(row).length());
        } else if(dir == UP) {
            row = Math.max(0, row - 1 );
            col = Math.min(textBox.getLine(row).getCharacters().size(), col);
        } else if(dir == DOWN) {
            row = Math.min(textBox.getNumOfLines() - 1, row + 1);
            col = Math.min(textBox.getLine(row).getCharacters().size(), col);
        }
    }

    private void insertCharAtCursor(char c) {
        int index = Math.min(getLengthUptoRow(row - 1)  + col, textBox.getText().length());

        textBox.setText(
                textBox.getText().substring(0,index) +
                c+
                textBox.getText().substring(index)
        );
    }

    private void removeCharAtCursor(int leftorRight) {
        if (textBox.getText().length() > 0) {
            int length = getLengthUptoRow(row - 1);
            if (leftorRight == LEFT ) {
                int index = Math.min(textBox.getText().length(), length + col);
                textBox.setText(
                        textBox.getText().substring(0, Math.max(index - 1, 0)) +
                        textBox.getText().substring(index)
                );
            } else if (leftorRight == RIGHT && length + col <= textBox.getText().length()) {
                textBox.setText(
                        textBox.getText().substring(0, length + col) +
                        textBox.getText().substring(Math.min(length + col + 1, textBox.getText().length() ))
                );
            }
        }
    }

    private int getLengthUptoRow(int row) {
        if(row < 0 || row >= textBox.getLines().size()) return 0;
        int length = 0;
        for(int i = 0 ; i <= row; i++) length += textBox.getLine(i).length() + 1; // 1 is for \n
        return length;
    }

    private void onKeyPress(int key) {
        switch (key) {
            case GLFW.GLFW_KEY_ENTER:
            case GLFW.GLFW_KEY_KP_ENTER:
                insertCharAtCursor('\n');
                moveCursor(DOWN);
                break;
            case GLFW.GLFW_KEY_BACKSPACE:
                removeCharAtCursor(LEFT);
                moveCursor(LEFT);
                break;
            case GLFW.GLFW_KEY_DELETE:
                removeCharAtCursor(RIGHT);
                break;

            case GLFW.GLFW_KEY_UP:
                moveCursor( UP);
                break;
            case GLFW.GLFW_KEY_DOWN:
                moveCursor(DOWN);
                break;
            case GLFW.GLFW_KEY_LEFT:
                moveCursor(LEFT);
                break;
            case GLFW.GLFW_KEY_RIGHT:
                moveCursor(RIGHT);
                break;
            default:
        }
    }

    private void onResize(D_Event e) {
        this.maxNumOfLines = (int)(style.getHeight() / textBox.getMesh().getData().getLineHeight());
    }

    private void onCharEvent(D_Event e) {
        insertCharAtCursor((char)((D_GuiCharEvent)e).getCodePoint());
        moveCursor(RIGHT);

        style.notifyObservers();
    }

    private void onKeyEvent(D_Event e) {
        halfSecond.reset();
        cursor.setVisible(true);
        if (((D_GuiKeyEvent) e).isAction(GLFW.GLFW_PRESS, GLFW.GLFW_REPEAT))
            onKeyPress(((D_GuiKeyEvent) e).getKey());

        style.notifyObservers();
    }

    private void onScrollEvent(D_Event event) {
        var e = (D_GuiScrollEvent)event;
        if(textBox.getMaxTextHeight() > getStyle().getHeight()) {
            float offset = textBox.getMesh().getData().getLineHeight() * (float) e.getYoffset();

            if(textBox.getOffset().y - offset < 0 ) offset = 0;
            textBox.setOffset(0, textBox.getOffset().y - offset);
        }
    }

    private void onMousePress(D_Event event) {
        if (((D_GuiMousePressEvent) event).isButton(MOUSE_BUTTON_LEFT)) {
            row = (int) Math.min(textBox.getNumOfLines() - 1, (style.getY() - Mouse.getY()) / textBox.getLineHeight());
            for(int i = 0; i < textBox.getLine(row).length(); i++) {
                if(Mouse.getX() - textBox.getPosition().x <= textBox.getLine(row).getWidth(i)) {
                    col = i + 1;
                    break;
                } else if(Mouse.getX() - textBox.getPosition().x > textBox.getLine(row).getWidth(textBox.getLine(row).length() - 1)) {
                    col = textBox.getLine(row).length();
                }
            }
        }
    }
}