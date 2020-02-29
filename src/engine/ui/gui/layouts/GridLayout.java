package engine.ui.gui.layouts;

import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.Style;

import java.util.ArrayList;

public class GridLayout extends Layout {

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

        Style style = parent.getStyle();
        if(parent.getChildList() == null) return;

        float currentX = style.getX() + style.getPaddingLeft();
        float currentY = style.getY() - style.getPaddingTop();
        int rIndex = 0;
        int cIndex = 0;

        setUpCellSizes(parent.getChildList());
        for(D_Gui child : parent.getChildList()) {
            if(rIndex >= rows) break;
            if(!child.isVisible()){
                cIndex++;
                continue;
            }
            child.getStyle().setPosition(currentX,currentY);
            currentX += getColumnWidth(cIndex);
            cIndex++;

            if(cIndex >= columns) {
                cIndex = 0;
                currentY -= getRowHeight(rIndex);
                currentX = style.getX() + style.getMarginLeft();
                rIndex++;
            }
        }
        if(!parent.isMinimized())
            style.setSize(getRowWidth(0) + style.getPaddingRight(), getColumnHeight(0) + style.getPaddingBottom() ,false);
    }

    private void setUpCellSizes(ArrayList<D_Gui> childList) {
        int rIndex = 0;
        int cIndex = 0;
        float maxHeight = 0;
        for(D_Gui child : childList) {
            if(rIndex >= rows) break;
            if(child.getStyle().getHeight() + child.getStyle().getMarginTop() > maxHeight) maxHeight = child.getStyle().getHeight() + child.getStyle().getMarginTop();
            if(getColumnWidth(cIndex) < child.getStyle().getWidth() + child.getStyle().getMarginLeft())
                setColumnWidth(cIndex,child.getStyle().getWidth() + child.getStyle().getMarginLeft());
            if(getRowHeight(rIndex) < child.getStyle().getHeight() + child.getStyle().getMarginTop())
                setRowHeight(rIndex,child.getStyle().getHeight() + child.getStyle().getMarginTop());
            cIndex ++;
            if(cIndex >= columns) {
                cIndex = 0;
                if(getRowHeight(rIndex) > maxHeight)
                    setRowHeight(rIndex,maxHeight);
                rIndex++;
            }
        }
    }

    private float getColumnWidth(int column) {
        if(column >= cellWidths.length) return 0;
        return cellWidths[column];
    }

    private void setColumnWidth(int column, float width) {
        if(column >= cellWidths.length) return;
        cellWidths[column] = width;
    }

    private float getRowHeight(int row) {
        if(row >= cellHeights.length) return 0;
        return cellHeights[row];
    }

    private void setRowHeight(int row, float height) {
        if(row >= cellHeights.length) return;
        cellHeights[row] = height;
    }

    private float getRowWidth(int row) {
        if(row >= cellWidths.length) return 0;
        float width = 0;
        for (float cellWidth : cellWidths) width += cellWidth;
        return width;
    }

    private float getColumnHeight(int column) {
        if(column >= cellHeights.length) return 0;
        float height = 0;
        for(float cellHeight : cellHeights) height += cellHeight;
        return height;
    }


}