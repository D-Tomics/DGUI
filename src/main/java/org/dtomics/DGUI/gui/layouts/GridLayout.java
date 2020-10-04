package org.dtomics.DGUI.gui.layouts;

import org.dtomics.DGUI.gui.components.D_Container;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_LayoutConstraint;
import org.dtomics.DGUI.gui.manager.constraints.layout_constraints.GridConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * This layout adds the children in a grid fashion on the parent.
 * This layout creates grid of fixed rows and columns to which on each addition the children
 * are added from left to right. The cell in which the children gets end up in and its alignment in the cell can be controlled
 * via <Code>GridConstraint</Code>.
 *
 * @author Abdul Kareem
 * @see Layout
 * @see org.dtomics.DGUI.gui.manager.constraints.layout_constraints.GridConstraint
 * @see Alignment
 */
public class GridLayout extends Layout {

    private boolean init;
    private int rows;
    private int columns;
    private float[] cellWidths;
    private float[] cellHeights;
    private GridConstraint defaultConstraint = new GridConstraint(0, 0, 0, 0, Alignment.CENTER);
    private Hashtable<D_Gui, D_LayoutConstraint> compTable;

    public GridLayout(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cellWidths = new float[columns];
        this.cellHeights = new float[rows];
    }

    @Override
    public void update(D_Container parent) {
        if (!parent.isVisible()) return;
        if (parent.getChildList() == null) return;

        initCellSizes(parent);
        setUpCellSizes(parent, parent.getChildList());

        int rIndex = 0;
        int cIndex = 0;

        boolean[][] occupied = new boolean[rows][columns];
        childLoop:
        for (D_Gui child : parent.getChildList()) {
            GridConstraint constraint = compTable != null ? (GridConstraint) (compTable.get(child)) : null;
            if (constraint == null) constraint = defaultConstraint;
            if (constraint != defaultConstraint) {
                cIndex = constraint.x();
                rIndex = constraint.y();
            }

            while (occupied[rIndex][cIndex]) {
                cIndex++;
                if (cIndex >= columns) {
                    cIndex = 0;
                    rIndex++;
                    if (rIndex >= rows)
                        break childLoop;
                }
            }

            float cellX = parent.getStyle().getX() + parent.getStyle().getPaddingLeft() + getWidthUptoCol(cIndex);
            float cellY = parent.getStyle().getY() - parent.getStyle().getPaddingTop() - getHeightUptoRow(rIndex);
            float cellWidth = getWidthFromTo(cIndex, cIndex + constraint.w());
            float cellHeight = getHeightFromTo(rIndex, rIndex + constraint.h());

            constraint.fill().setComponentSize(cellWidth - parent.getStyle().getPaddingWidth(), cellHeight - parent.getStyle().getPaddingHeight(), child, true);
            constraint.alignment().setComponentPosition(cellX, cellY, cellWidth, cellHeight, child, true);

            for (int j = rIndex; j < rows && j <= rIndex + constraint.h(); j++)
                for (int i = cIndex; i < columns && i <= cIndex + constraint.w(); i++)
                    occupied[j][i] = true;

            cIndex += constraint.w() + 1;
            if (cIndex >= columns) {
                cIndex = 0;
                rIndex++;
                if (rIndex >= rows)
                    rIndex = 0;
            }
        }
    }

    private void initCellSizes(D_Container parent) {
        if (init) return;
        Arrays.fill(cellWidths, parent.getStyle().getWidth() / columns);
        Arrays.fill(cellHeights, parent.getStyle().getHeight() / rows);
        init = true;
    }

    private void setUpCellSizes(D_Container parent, ArrayList<D_Gui> children) {

        int rIndex = 0;
        int cIndex = 0;

        int lastOccupiedRow = 0;
        int lastOccupiedCol = 0;

        boolean[][] occupied = new boolean[rows][columns];
        for (int i = 0; i < children.size(); i++) {
            D_Gui child = children.get(i);
            GridConstraint constraint = getConstraint(child);
            if (constraint != defaultConstraint) {
                cIndex = constraint.x();
                rIndex = constraint.y();
            }

            while (occupied[rIndex][cIndex]) {
                cIndex++;
                if (cIndex >= columns) {
                    cIndex = 0;
                    rIndex++;
                    if (rIndex >= rows)
                        throw new IllegalStateException("trying to add elements to a row that does'nt exist");
                }
            }

            float cellWidth = getCellWidth(cIndex);
            float newCellWidth = constraint.size().getCellWidth(constraint, cellWidth, child);
            float dw = newCellWidth - cellWidth;
            if (dw != 0) {
                for (int x = cIndex; x < columns && x <= cIndex + constraint.w(); x++) {
                    float prevW = cellWidths[x];
                    cellWidths[x] = newCellWidth;
                    parent.getStyle().setWidth(parent.getStyle().getWidth() + cellWidths[x] - prevW, false);
                }

                int nextColumn = cIndex + 1 + constraint.w();
                if (nextColumn < columns) {
                    cellWidth = cellWidths[nextColumn];
                    cellWidths[nextColumn] = cellWidth - dw < 0 ? 0 : cellWidth - dw;
                    parent.getStyle().setWidth(parent.getStyle().getWidth() - cellWidth + cellWidths[nextColumn], false);
                }
            }

            float cellHeight = getCellHeight(rIndex);
            float newCellHeight = constraint.size().getCellHeight(constraint, cellHeight, child);
            float dh = newCellHeight - cellHeight;
            if (dh != 0) {
                for (int y = rIndex; y < rows && y <= rIndex + constraint.h(); y++) {
                    cellHeights[y] = newCellHeight;
                    parent.getStyle().setHeight(parent.getStyle().getHeight() + dh, false);
                }

                int nextRow = rIndex + 1 + constraint.h();
                if (nextRow < rows) {
                    cellHeight = cellHeights[nextRow];
                    cellHeights[nextRow] -= dh;
                    parent.getStyle().setHeight(parent.getStyle().getHeight() - cellHeight + cellHeights[nextRow], false);
                }
            }

            for (int y = 0; y < rows && y <= rIndex + constraint.h(); y++)
                for (int x = cIndex; x < columns && x <= cIndex + constraint.w(); x++)
                    occupied[y][x] = true;

            if (lastOccupiedCol < cIndex) lastOccupiedCol = cIndex;
            if (lastOccupiedRow < rIndex) lastOccupiedRow = rIndex;

            cIndex++;
            if (cIndex >= columns) {
                cIndex = 0;
                rIndex++;
                if (rIndex >= rows)
                    rIndex = 0;
            }

        }

        setMaxWidth(getWidthUptoCol(lastOccupiedCol + 1));
        setMaxHeight(getHeightUptoRow(lastOccupiedRow + 1));
    }

    private float getCellWidth(int col) {
        if (col > cellWidths.length || col < 0) return 0;
        return cellWidths[col];
    }

    private float getCellHeight(int row) {
        if (row > cellHeights.length || row < 0) return 0;
        return cellHeights[row];
    }

    private float getWidthUptoCol(int cIndex) {
        float w = 0;
        for (int i = 0; i < cIndex && i < columns; i++) w += getCellWidth(i);
        return w;
    }

    private float getHeightUptoRow(int row) {
        float h = 0;
        for (int i = 0; i < row && i < rows; i++) h += getCellHeight(i);
        return h;
    }

    private float getWidthFromTo(int from, int to) {
        if (from < 0 || from > to || to > cellWidths.length) return 0;
        float w = 0;
        for (int i = from; i <= to; i++) w += getCellWidth(i);
        return w;
    }

    private float getHeightFromTo(int from, int to) {
        if (from < 0 || from > to || to >= cellHeights.length) return 0;
        float h = 0;
        for (int i = from; i <= to; i++) h += getCellHeight(i);
        return h;
    }

    private GridConstraint getConstraint(D_Gui child) {
        return compTable != null ? (GridConstraint) compTable.get(child) : defaultConstraint;
    }

    @Override
    void setConstraint(D_Gui gui, D_LayoutConstraint constraint) {
        if (compTable == null) compTable = new Hashtable<>();
        if (constraint instanceof GridConstraint)
            compTable.put(gui, (GridConstraint) constraint.clone());
        else if (constraint == null)
            compTable.put(gui, defaultConstraint);
        else
            throw new IllegalStateException("cannot add " + constraint + "to gridLayout");
    }

}
