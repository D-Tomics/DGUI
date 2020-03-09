package engine.ui.gui.manager.constraints.layout_constraints;

import engine.ui.gui.manager.constraints.D_LayoutConstraint;

public class GridConstraint extends D_LayoutConstraint {

    private int x;
    private int y;
    private int w;
    private int h;
    private boolean centered;

    public GridConstraint(int x, int y, int w, int h, boolean centered) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.centered = centered;
    }

    public GridConstraint(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int x() { return x; }
    public int y() { return y; }
    public int w() { return w; }
    public int h() { return h; }
    public boolean isCentered() { return centered; }

    public GridConstraint gridX(int gridX) {
        x = gridX;
        return this;
    }

    public GridConstraint gridY(int gridY) {
        y = gridY;
        return this;
    }

    public GridConstraint gridWidth(int gridW) {
        w = gridW;
        return this;
    }

    public GridConstraint gridHeight(int gridH) {
        h = gridH;
        return this;
    }

    public GridConstraint setPos(int gridX, int gridY) {
        x = gridX;
        y = gridY;
        return this;
    }

    public GridConstraint setSize(int gridW, int gridH) {
        w = gridW;
        h = gridH;
        return this;
    }

    public GridConstraint set( int gridX, int gridY, int gridWidth, int gridHeight) {
        return set(gridX, gridY, gridWidth, gridHeight,centered);
    }

    public GridConstraint set(int gridX, int gridY, int gridWidth, int gridHeight, boolean centered) {
        x = gridX;
        y = gridY;
        w = gridWidth;
        h = gridHeight;
        this.centered = centered;
        return this;
    }
}
