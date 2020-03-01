package engine.ui.gui.layouts;

import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;
import engine.ui.gui.manager.constraints.constraints.GridConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class GridLayout extends Layout {

    private boolean init;
    private int rows;
    private int columns;
    private float[] cellWidths;
    private float[] cellHeights;

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
        float[] maxHeights = setUpCellSizes(parent,parent.getChildList());

        int rIndex = 0;
        int cIndex = 0;

        float curWidth = 0;
        float curHeight = getCellHeight(0);
        if(curHeight > getMaxHeight())
            setMaxHeight(curHeight);

        for(D_Gui child : parent.getChildList()) {
            GridConstraint constraint = null;//(GridConstraint)(compTable.get(child));

            int prevC = 0;
            int prevR = 0;
            if(constraint != null) {
                prevC = cIndex;
                prevR = rIndex;
                cIndex = constraint.x();
                rIndex = constraint.y();
            }
            child.getStyle().setPosition(
                    parent.getStyle().getX() + parent.getStyle().getPaddingLeft() + getWidthUptoCol(cIndex)
                            + (constraint != null && constraint.isCentered() ? getCellWidth(cIndex) / 2.0f - child.getStyle().getWidth() / 2.0f : 0),
                    parent.getStyle().getY() - parent.getStyle().getPaddingTop() - getHeightUptoRow(rIndex)
                            - (constraint != null && constraint.isCentered() ? getCellHeight(rIndex) / 2.0f - child.getStyle().getHeight() / 2.0f : 0)
            );

            if(constraint != null) {
                cIndex = prevC - 1;
                rIndex = prevR;
            }

            curWidth += getCellWidth(cIndex);
            cIndex ++;
            if(curWidth > getMaxWidth()) setMaxWidth(curWidth);
            if(cIndex >= columns) {
                cIndex = 0;
                rIndex++;

                curWidth = 0;
                curHeight += getCellHeight(rIndex);
                if(curHeight > getMaxHeight()) setMaxHeight(curHeight);

                if(rIndex >= rows)
                    break;
            }
        }

    }

    private void initCellSizes(D_Container parent) {
        if(init) return;
        Arrays.fill(cellWidths, parent.getStyle().getWidth() / columns);
        Arrays.fill(cellHeights, parent.getStyle().getHeight() / rows);
        init = true;
    }

    private float[] setUpCellSizes(D_Container parent, ArrayList<D_Gui> children) {

        float[] maxHeight = new float[rows];
        int rIndex = 0;
        int cIndex = 0;
        int prevRIndex = 0;
        int prevCIndex = 0;

        for(D_Gui child : children) {
            GridConstraint constraint = null;//(GridConstraint)compTable.get(child);
            if(constraint != null) {
                prevCIndex = cIndex;
                prevRIndex = rIndex;
                cIndex = constraint.x();
                rIndex = constraint.y();
            }

            if(child.getStyle().getHeight() + child.getStyle().getMarginTop() + child.getStyle().getMarginBottom() > maxHeight[rIndex])
                maxHeight[rIndex] = child.getStyle().getHeight() + child.getStyle().getMarginTop() + child.getStyle().getMarginBottom();

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

            if(constraint != null) {
                cIndex = prevCIndex - 1;
                rIndex = prevRIndex;
            }

            cIndex ++;
            if(cIndex >= columns) {
                cIndex = 0;
                rIndex++;
                if(rIndex >= rows) break;
            }

        }
        return maxHeight;
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


}
