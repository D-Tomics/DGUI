package org.dtomics.DGUI.gui.layouts;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.Style;

public enum CellFill {

    FILL, HORIZONTAL,VERTICAL, NO_FILL;

    protected Style setComponentSize(float cellWidth, float cellHeight, D_Gui component, boolean notify) {
        final float width = cellWidth - component.getStyle().getMarginWidth();
        final float height = cellHeight - component.getStyle().getMarginHeight();
        switch (this) {
            case FILL:       return component.getStyle().setSize(width, height, notify);
            case VERTICAL:   return component.getStyle().setHeight(height, notify);
            case HORIZONTAL: return component.getStyle().setWidth(width, notify);
            case NO_FILL:
            default:         return component.getStyle();
        }
    }

}
