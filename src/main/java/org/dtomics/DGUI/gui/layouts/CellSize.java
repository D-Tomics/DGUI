package org.dtomics.DGUI.gui.layouts;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.layout_constraints.GridConstraint;

public enum CellSize {

    COMPACT, COMPACT_WIDTH, COMPACT_HEIGHT, DEFAULT;


    protected float getCellWidth(GridConstraint constraint, float cellWidth, D_Gui component) {
        float width = (component.getStyle().getWidth() + component.getStyle().getMarginWidth()) / (constraint.w() + 1f);
        switch (this) {
            case COMPACT:
            case COMPACT_WIDTH:
                return width;
            case DEFAULT:
            default:
                return Math.max(width, cellWidth);
        }
    }

    protected float getCellHeight(GridConstraint constraint, float cellHeight, D_Gui component) {
        float height = (component.getStyle().getHeight() + component.getStyle().getMarginHeight()) / (constraint.h() + 1f);
        switch (this) {
            case COMPACT:
            case COMPACT_HEIGHT:
                return height;
            case DEFAULT:
            default:
                return Math.max(height, cellHeight);
        }
    }

}
