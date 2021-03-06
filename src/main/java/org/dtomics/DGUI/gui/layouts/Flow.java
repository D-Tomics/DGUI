package org.dtomics.DGUI.gui.layouts;

import org.dtomics.DGUI.gui.components.D_Container;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_LayoutConstraint;

import java.util.ArrayList;

/**
 * This is the default layout of container. This adds children to the container from the center and shifts the to left
 * on each addition. once the children occupy more than the width of the parent next child is added to next line.
 *
 * @author Abdul Kareeem
 * @see Layout
 */
public class Flow extends Layout {

    private final ArrayList<D_Gui> currentRow = new ArrayList<>();

    @Override
    public void update(D_Container parent) {
        if (parent == null) return;
        if (!parent.isVisible()) return;
        if (parent.getChildList() == null) return;

        float x = parent.getStyle().getX() + parent.getStyle().getPaddingLeft();
        float y = parent.getStyle().getY() - parent.getStyle().getPaddingTop();

        currentRow.clear();
        for (int i = 0; i < parent.getChildList().size(); i++) {
            D_Gui child = parent.getChildList().get(i);
            if (x + child.getStyle().getWidth() + child.getStyle().getMarginWidth() + parent.getStyle().getPaddingRight() > parent.getStyle().getX() + parent.getStyle().getWidth()) {

                float rowHeight = getRowHeight(currentRow);
                float rowWidth = getRowWidth(currentRow);
                if (getMaxHeight() < Math.abs(y - parent.getStyle().getY()) + rowHeight)
                    setMaxHeight(Math.abs(y - parent.getStyle().getY()) + rowHeight);
                if (getMaxWidth() < rowWidth)
                    setMaxWidth(rowWidth);

                positionComponents(parent, currentRow, y);
                x = parent.getStyle().getX() + parent.getStyle().getPaddingLeft();
                y -= rowHeight;
                currentRow.clear();
            }

            float height = Math.abs(y - parent.getStyle().getY()) + child.getStyle().getHeight() + child.getStyle().getMarginHeight();
            if (height > getMaxHeight()) setMaxHeight(height);

            if (height > parent.getStyle().getHeight())
                parent.getStyle().setHeight(height, false);

            currentRow.add(child);
            x += child.getStyle().getWidth() + child.getStyle().getMarginWidth();
        }

        float rowWidth = getRowWidth(currentRow);
        if (getMaxWidth() < rowWidth) setMaxWidth(rowWidth);

        positionComponents(parent, currentRow, y);
    }

    private void positionComponents(D_Container parent, ArrayList<D_Gui> row, float y) {
        float w = getRowWidth(row);
        float h = getRowHeight(row);

        float x = parent.getStyle().getCenterX() + parent.getStyle().getPaddingLeft() - w / 2.0f;
        for (int i = 0; i < row.size(); i++) {
            D_Gui gui = row.get(i);
            gui.getStyle().setPosition(x + gui.getStyle().getMarginLeft(), y - h / 2.0f + gui.getStyle().getHeight() / 2.0f);
            x += gui.getStyle().getWidth() + gui.getStyle().getMarginWidth();
        }
    }

    private float getRowHeight(ArrayList<D_Gui> row) {
        float max = 0;
        for (int i = 0; i < row.size(); i++) {
            D_Gui gui = row.get(i);
            if (max < gui.getStyle().getHeight() + gui.getStyle().getMarginHeight())
                max = gui.getStyle().getHeight() + gui.getStyle().getMarginHeight();
        }
        return max;
    }

    private float getRowWidth(ArrayList<D_Gui> row) {
        float w = 0;
        for (int i = 0; i < row.size(); i++) {
            D_Gui gui = row.get(i);
            w += gui.getStyle().getWidth() + gui.getStyle().getMarginWidth();
        }
        return w;
    }


    @Override
    void setConstraint(D_Gui gui, D_LayoutConstraint constraint) {
    }

}
