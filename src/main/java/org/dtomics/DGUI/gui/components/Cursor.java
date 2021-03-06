package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.utils.Delay;
import org.dtomics.DGUI.utils.colors.Color;

final class Cursor extends D_GuiQuad {

    private int row;
    private int col;
    private final Delay blinkDelay;
    private final D_TextBox textBox;

    Cursor(D_TextBox textBox) {
        this.textBox = textBox;
        this.blinkDelay = new Delay(500);

        this.style.setWidth(1);
        this.style.setHeight(this.textBox.getMeshData().getLineHeight());
        this.style.setBgColor(Color.BLACK);
        this.style.setBorderWidth(0);
        this.setVisible(false);
    }

    @Override
    protected void onUpdate() {
        if (this.getParent().isFocused()) {
            float cursorX = textBox.getPosition().x + (textBox.getLine(row) != null ? textBox.getLine(row).getWidth(col - 1) : 0);
            float cursorY = textBox.getPosition().y - row * textBox.getMeshData().getLineHeight();

            this.getStyle().setPosition(cursorX + textBox.getOffset().x, cursorY + textBox.getOffset().y, false);

            if (blinkDelay.over()) this.setVisible(!this.isVisible());
        } else
            this.setVisible(false);
    }

    int getCol() {
        return col;
    }

    void setCol(int col) {
        this.col = col;
    }

    int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    void moveLeft(int offset) {
        int rTemp = row;
        row = col - 1 < 0 ? Math.max(0, row - 1) : row;
        col = rTemp != row ? textBox.getLineLength(row) + offset : Math.max(col - 1, 0);
    }

    void moveRight(int offset) {
        int rTemp = row;
        row = col + 1 > textBox.getLineLength(row) ? Math.min(row + 1, textBox.getLines().size() - 1) : row;
        col = rTemp != row ? offset : Math.min(col + 1, textBox.getLineLength(row));
    }

    void moveUp() {
        row = Math.max(0, row - 1);
        col = Math.min(textBox.getLineLength(row), col);
    }

    void moveDown() {
        row = Math.min(textBox.getNumOfLines() - 1, row + 1);
        col = Math.min(textBox.getLineLength(row), col);
    }

    void blinkDelay() {
        blinkDelay.reset();
    }

}
