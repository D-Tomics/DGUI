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
 * @see Layout
 * @see org.dtomics.DGUI.gui.manager.constraints.layout_constraints.GridConstraint
 * @see Alignment
 *
 * @author Abdul Kareem
 */
public class GridLayout extends Layout {

    private boolean init;
    private int rows;
    private int columns;
    private float[] cellWidths;
    private float[] cellHeights;
    private GridConstraint defaultConstraint = new GridConstraint(0,0,0,0,Alignment.CENTER);
    private Hashtable< D_Gui, D_LayoutConstraint> compTable;

    public GridLayout(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cellWidths = new float[columns];
        this.cellHeights = new float[rows];
    }

    @Override
    public void update(D_Container parent) {
        if(!parent.isVisible()) return;
        if(parent.getChildList() == null) return;

        initCellSizes(parent);
        setUpCellSizes(parent,parent.getChildList());

        int rIndex = 0;
        int cIndex = 0;

        boolean[][] occupied = new boolean[rows][columns];
        childLoop : for(D_Gui child : parent.getChildList()) {
            GridConstraint constraint = compTable != null ? (GridConstraint)(compTable.get(child)) : null;
            if(constraint == null) constraint = defaultConstraint;
            if(constraint != defaultConstraint) {
                cIndex = constraint.x();
                rIndex = constraint.y();
            }

            while(occupied[rIndex][cIndex]) {
                cIndex ++;
                if(cIndex >= columns) {
                    cIndex = 0;
                    rIndex++;
                    if(rIndex >= rows)
                        break childLoop;
                }
            }

            constraint.alignment().setComponentPosition(
                    parent.getStyle().getX() + parent.getStyle().getPaddingLeft() + getWidthUptoCol(cIndex),
                    parent.getStyle().getY() - parent.getStyle().getPaddingTop() - getHeightUptoRow(rIndex),
                    getWidthFromTo(cIndex,cIndex + constraint.w()),
                    getHeightFromTo(rIndex, rIndex + constraint.h()),
                    child, true
            );
            for(int j = rIndex; j < rows && j <= rIndex + constraint.h(); j++)
                for(int i = cIndex; i < columns && i <= cIndex + constraint.w(); i++)
                    occupied[j][i] = true;

            cIndex += constraint.w() + 1;
            if(cIndex >= columns) {
                cIndex = 0;
                rIndex++;
                if(rIndex >= rows)
                    rIndex = 0;
            }
        }
    }

    private void initCellSizes(D_Container parent) {
        if(init) return;
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
        for(D_Gui child : children) {
            GridConstraint constraint = compTable != null ? (GridConstraint)compTable.get(child) : null;
            if(constraint == null) constraint = defaultConstraint;
            if(constraint != defaultConstraint) {
                cIndex = constraint.x();
                rIndex = constraint.y();
            }

            while(occupied[rIndex][cIndex]) {
                cIndex ++;
                if(cIndex >= columns) {
                    cIndex = 0;
                    rIndex++;
                    if(rIndex >= rows)
                        throw new IllegalStateException("trying to add elements to a row that does'nt exist");
                }
            }

            if(child.getStyle().getWidth() / (constraint.w() + 1) + child.getStyle().getMarginWidth() > getCellWidth(cIndex)) {
                for(int i = cIndex; i < columns && i <= cIndex + constraint.w(); i++) {
                    float prevW = cellWidths[i];
                    cellWidths[i] = child.getStyle().getWidth() / (constraint.w() + 1) + child.getStyle().getMarginWidth();
                    parent.getStyle().setWidth(parent.getStyle().getWidth() - prevW + cellWidths[i], false);
                }
            }
            if(child.getStyle().getHeight() / (constraint.h() + 1) + child.getStyle().getMarginHeight()> getCellHeight(rIndex)) {
                for(int i = rIndex; i < rows && i <= rIndex + constraint.h(); i++) {
                    float prewH = cellHeights[i];
                    cellHeights[i] = child.getStyle().getHeight() / (constraint.h() + 1) + child.getStyle().getMarginHeight();
                    parent.getStyle().setHeight(parent.getStyle().getHeight() - prewH + cellHeights[i], false);
                }

            }
            for(int j = 0; j < rows && j <= rIndex + constraint.h(); j++)
                for(int i = cIndex; i < columns && i <= cIndex + constraint.w(); i++)
                    occupied[j][i] = true;

            if(lastOccupiedCol < cIndex) lastOccupiedCol = cIndex;
            if(lastOccupiedRow < rIndex) lastOccupiedRow = rIndex;

            cIndex ++;
            if(cIndex >= columns) {
                cIndex = 0;
                rIndex++;
                if(rIndex >= rows)
                    rIndex = 0;
            }

        }

        setMaxWidth(getWidthUptoCol(lastOccupiedCol + 1));
        setMaxHeight(getHeightUptoRow(lastOccupiedRow + 1));
    }

    private float getCellWidth(int col) {
        if(col > cellWidths.length || col < 0) return 0;
        return cellWidths[col];
    }

    private float getCellHeight(int row) {
        if(row > cellHeights.length || row < 0) return 0;
        return cellHeights[row];
    }

    private float getWidthUptoCol(int cIndex) {
        float w = 0;
        for( int i = 0; i<cIndex && i < columns; i++) w += getCellWidth(i);
        return w;
    }

    private float getHeightUptoRow(int row) {
        float h = 0;
        for(int i = 0; i < row && i<rows; i++) h += getCellHeight(i);
        return h;
    }

    private float getWidthFromTo(int from , int to ) {
        if(from < 0 || from > to || to > cellWidths.length) return 0;
        float w = 0;
        for(int i = from; i <= to; i++) w+= getCellWidth(i);
        return w;
    }

    private float getHeightFromTo(int from, int to) {
        if(from < 0 || from > to || to >= cellHeights.length) return 0;
        float h = 0;
        for(int i = from; i <= to; i++) h += getCellHeight(i);
        return h;
    }

    @Override
    void setConstraint(D_Gui gui, D_LayoutConstraint constraint) {
        if(compTable == null) compTable = new Hashtable<>();
        if(constraint instanceof GridConstraint)
            compTable.put(gui, (GridConstraint) constraint.clone());
        else if(constraint == null)
            compTable.put(gui,defaultConstraint);
        else
            throw new IllegalStateException("cannot add "+constraint+"to gridLayout");
    }

}