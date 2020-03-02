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

    public void set( int gridX, int gridY, int gridWidth, int gridHeight) {
        x = gridX;
        y = gridY;
        w = gridWidth;
        h = gridHeight;
    }
}
