package engine.ui.gui.layouts;

import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;
import engine.ui.gui.manager.constraints.layout_constraints.GridConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class GridLayout extends Layout {

    private boolean init;
    private int rows;
    private int columns;
    private float[] cellWidths;
    private float[] cellHeights;
    private GridConstraint defaultConstraint = new GridConstraint(0,0,0,0,true);
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

        float curWidth = 0;
        float curHeight = cellHeights[0];

        boolean[][] occupied = new boolean[rows][columns];
        for(D_Gui child : parent.getChildList()) {

            GridConstraint constraint = compTable != null ? (GridConstraint)(compTable.get(child)) : null;
            int prevC = 0;
            int prevR = 0;
            if(constraint != null) {
                prevC = cIndex;
                prevR = rIndex;
                cIndex = constraint.x();
                rIndex = constraint.y();
                curWidth += cIndex > prevC ? getWidthUptoCol(cIndex) - getWidthUptoCol(prevC) : 0;
                curHeight += rIndex > prevR ? getHeightUptoRow(rIndex) - getHeightUptoRow(prevR) : 0;
            }


            while(occupied[rIndex][cIndex]) {
                cIndex ++;
                if(cIndex >= columns) {
                    cIndex = 0;
                    rIndex++;
                    if(rIndex >= rows)
                        break;
                }
            }

            child.getStyle().setPosition(
                    parent.getStyle().getX() + parent.getStyle().getPaddingLeft() + getWidthUptoCol(cIndex) + child.getStyle().getMarginLeft()
                            + (constraint != null && constraint.isCentered() ? (getCellWidth(cIndex) - child.getStyle().getMarginWidth() - child.getStyle().getWidth()) / 2.0f : 0),
                    parent.getStyle().getY() - parent.getStyle().getPaddingTop() - getHeightUptoRow(rIndex) - child.getStyle().getMarginTop()
                            - (constraint != null && constraint.isCentered() ? (getCellHeight(rIndex) - child.getStyle().getMarginHeight() - child.getStyle().getHeight()) / 2.0f : 0)
            );
            curWidth += getCellWidth(cIndex);
            occupied[rIndex][cIndex] = true;
            if(constraint != null) {
                cIndex = cIndex != prevC ? prevC - 1 : prevC;
                rIndex = prevR;
            }

            cIndex ++;
            if(curWidth > getMaxWidth()) setMaxWidth(curWidth);
            if(cIndex >= columns) {
                curWidth = 0;
                curHeight += getCellHeight(rIndex);

                cIndex = 0;
                rIndex++;
                if(rIndex >= rows)
                    break;
            }
        }
        if(curHeight > getMaxHeight()) setMaxHeight(curHeight);
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
        int prevRIndex = 0;
        int prevCIndex = 0;

        boolean[][] occupied = new boolean[rows][columns];
        for(D_Gui child : children) {
            GridConstraint constraint = compTable != null ? (GridConstraint)compTable.get(child) : null;
            if(constraint != null) {
                prevCIndex = cIndex;
                prevRIndex = rIndex;
                cIndex = constraint.x();
                rIndex = constraint.y();
            }

            while(occupied[rIndex][cIndex]) {
                cIndex ++;
                if(cIndex >= columns) {
                    cIndex = 0;
                    rIndex++;
                    if(rIndex >= rows) break;
                }
            }

            if(child.getStyle().getWidth() + child.getStyle().getMarginLeft() + child.getStyle().getMarginRight() > getCellWidth(cIndex)) {
                float prevW = cellWidths[cIndex];
                cellWidths[cIndex] = child.getStyle().getWidth() + child.getStyle().getMarginLeft() + child.getStyle().getMarginRight();
                parent.getStyle().setWidth(parent.getStyle().getWidth() - prevW + cellWidths[cIndex]);
            }
            if(child.getStyle().getHeight() + child.getStyle().getMarginTop() + child.getStyle().getMarginBottom()> getCellHeight(rIndex)) {
                float prewH = cellHeights[rIndex];
                cellHeights[rIndex] = child.getStyle().getHeight() + child.getStyle().getMarginTop() + child.getStyle().getMarginBottom();
                parent.getStyle().setHeight(parent.getStyle().getHeight() - prewH + cellHeights[rIndex]);
            }
            occupied[rIndex][cIndex] = true;
            if(constraint != null) {
                cIndex = cIndex != prevCIndex ? prevCIndex - 1 : prevCIndex;
                rIndex = prevRIndex;
            }

            cIndex ++;
            if(cIndex >= columns) {
                cIndex = 0;
                rIndex++;
                if(rIndex >= rows) break;
            }

        }
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
