package engine.ui.gui.manager.constraints.layout_constraints;

import engine.ui.gui.layouts.Alignment;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;

/**
 * This constraint represents the position and size of the cell were a gui occupies in a grid layout.
 * This also specifies the alignment of the gui in the cell
 *
 * @see engine.ui.gui.layouts.GridLayout
 *
 * @author Abdul Kareem
 */
public class GridConstraint extends D_LayoutConstraint {

    private int x;
    private int y;
    private int w;
    private int h;
    private Alignment alignment;

    public GridConstraint()                           { this(0,0,0,0,Alignment.CENTER); }
    public GridConstraint(int x, int y, int w, int h) { this(x, y, w, h, Alignment.CENTER); }

    public GridConstraint(int x, int y, int w, int h, Alignment alignment) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.alignment = alignment;
    }

    public int x() { return x; }
    public int y() { return y; }
    public int w() { return w; }
    public int h() { return h; }
    public Alignment alignment() { return alignment; }

    public GridConstraint gridX(int gridX) { return setPos(gridX,y); }
    public GridConstraint gridY(int gridY) { return setPos(x,gridY); }
    public GridConstraint gridWidth(int gridW) { return setSize(gridW,0); }
    public GridConstraint gridHeight(int gridH) { return setSize(0,gridH); }
    public GridConstraint girdAlign(Alignment align) { return set(x,y,w,h,alignment); }

    public GridConstraint setPos(int gridX, int gridY) { return set(gridX, gridY, 0, 0); }

    public GridConstraint setSize(int gridW, int gridH) { return set(x,y,gridW,gridH); }

    public GridConstraint set( int gridX, int gridY, int gridWidth, int gridHeight) { return set(gridX, gridY, gridWidth, gridHeight, alignment); }

    public GridConstraint set(int gridX, int gridY, int gridWidth, int gridHeight, Alignment alignment) {
        x = gridX;
        y = gridY;
        w = gridWidth;
        h = gridHeight;
        this.alignment = alignment;
        return this;
    }
}
