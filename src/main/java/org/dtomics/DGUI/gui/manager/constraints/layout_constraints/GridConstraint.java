package org.dtomics.DGUI.gui.manager.constraints.layout_constraints;

import lombok.AllArgsConstructor;
import org.dtomics.DGUI.gui.layouts.Alignment;
import org.dtomics.DGUI.gui.layouts.CellFill;
import org.dtomics.DGUI.gui.layouts.CellSize;
import org.dtomics.DGUI.gui.manager.constraints.D_LayoutConstraint;

/**
 * This constraint represents the position and size of the cell were a gui occupies in a grid layout.
 * This also specifies the alignment of the gui in the cell
 *
 * @author Abdul Kareem
 * @see org.dtomics.DGUI.gui.layouts.GridLayout
 */

@AllArgsConstructor
public class GridConstraint extends D_LayoutConstraint {

    private int x;
    private int y;
    private int w;
    private int h;
    private Alignment alignment = Alignment.CENTER;
    private CellFill fill = CellFill.NO_FILL;
    private CellSize cellSize = CellSize.DEFAULT;

    public GridConstraint() {
        this(0, 0, 0, 0, Alignment.CENTER);
    }

    public GridConstraint(int x, int y, int w, int h) {
        this(x, y, w, h, Alignment.CENTER);
    }

    public GridConstraint(int x, int y, int w, int h, Alignment alignment) {
        this(x, y, w, h, alignment, CellFill.NO_FILL);
    }

    public GridConstraint(int x, int y, int w, int h, Alignment alignment, CellFill fill) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.alignment = alignment;
        this.fill = fill;
    }

    public static GridConstraintBuilder builder() {
        return new GridConstraintBuilder();
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int w() {
        return w;
    }

    public int h() {
        return h;
    }

    public CellFill fill() {
        return fill;
    }

    public CellSize size() {
        return cellSize;
    }

    public Alignment alignment() {
        return alignment;
    }

    public GridConstraint gridX(int gridX) {
        return setPos(gridX, y);
    }

    public GridConstraint gridY(int gridY) {
        return setPos(x, gridY);
    }

    public GridConstraint gridWidth(int gridW) {
        return setSize(gridW, 0);
    }

    public GridConstraint gridHeight(int gridH) {
        return setSize(0, gridH);
    }

    public GridConstraint girdAlign(Alignment align) {
        return set(x, y, w, h, align, fill);
    }

    public GridConstraint gridFill(CellFill fill) {
        return set(x, y, w, h, alignment, fill);
    }

    public GridConstraint gridCellSize(CellSize cellSize) {
        return set(x, y, w, h, alignment, fill, cellSize);
    }

    public GridConstraint setPos(int gridX, int gridY) {
        return set(gridX, gridY, 0, 0);
    }

    public GridConstraint setSize(int gridW, int gridH) {
        return set(x, y, gridW, gridH);
    }

    public GridConstraint set(int gridX, int gridY, int gridWidth, int gridHeight) {
        return set(gridX, gridY, gridWidth, gridHeight, alignment, fill);
    }

    public GridConstraint set(int gridX, int gridY, int gridWidth, int gridHeight, Alignment alignment) {
        return set(gridX, gridY, gridWidth, gridHeight, alignment, fill);
    }

    public GridConstraint set(int gridX, int gridY, int gridWidth, int gridHeight, Alignment alignment, CellFill fill) {
        return set(gridX, gridY, gridWidth, gridHeight, alignment, fill, cellSize);
    }

    public GridConstraint set(int gridX, int gridY, int gridWidth, int gridHeight, Alignment alignment, CellFill fill, CellSize size) {
        x = gridX;
        y = gridY;
        w = gridWidth;
        h = gridHeight;
        this.fill = fill;
        this.alignment = alignment;
        this.cellSize = size;
        return this;
    }

    public static final class GridConstraintBuilder {
        private int x;
        private int y;
        private int w;
        private int h;
        private Alignment alignment = Alignment.CENTER;
        private CellFill fill = CellFill.NO_FILL;
        private CellSize cellSize = CellSize.DEFAULT;

        public GridConstraintBuilder x(int x) {
            this.x = x;
            return this;
        }

        public GridConstraintBuilder y(int y) {
            this.y = y;
            return this;
        }

        public GridConstraintBuilder w(int w) {
            this.w = w;
            return this;
        }

        public GridConstraintBuilder h(int h) {
            this.h = h;
            return this;
        }

        public GridConstraintBuilder alignment(Alignment alignment) {
            this.alignment = alignment;
            return this;
        }

        public GridConstraintBuilder fill(CellFill fill) {
            this.fill = fill;
            return this;
        }

        public GridConstraintBuilder cellSize(CellSize cellSize) {
            this.cellSize = cellSize;
            return this;
        }

        public GridConstraint build() {
            return new GridConstraint(this.x, this.y, this.w, this.h, this.alignment, this.fill, this.cellSize);
        }
    }
}
