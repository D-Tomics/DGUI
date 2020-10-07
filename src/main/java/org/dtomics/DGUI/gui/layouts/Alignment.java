package org.dtomics.DGUI.gui.layouts;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This constants represent how an element is positioned in a cell
 *
 * @author kareem
 */
public enum Alignment {

    CENTER, LEFT, RIGHT, TOP, BOTTOM,
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

    /**
     * this method sets the position of a component on a cell based
     * on what type of alignment calls this method.
     *
     * @param cellX      represents x position of cell in the window
     * @param cellY      represents y position of cell in the window
     * @param cellWidth  represents width of the cell in which component is placed
     * @param cellHeight represents height of the cell in which component is placed
     * @param component  represents the component which is to be positioned
     * @param notify     represents whether or not to notify component's position changes
     */
    protected void setComponentPosition(float cellX, float cellY, float cellWidth, float cellHeight, D_Gui component, boolean notify) {
        float x = cellX;
        float y = cellY;
        switch (this) {
            case CENTER:
                x = cellX + component.getStyle().getMarginLeft() + (cellWidth - component.getStyle().getMarginWidth() - component.getStyle().getWidth()) / 2.0f;
                y = cellY - component.getStyle().getMarginTop() - (cellHeight - component.getStyle().getMarginHeight() - component.getStyle().getHeight()) / 2.0f;
                break;
            case LEFT:
                x = cellX + component.getStyle().getMarginLeft();
                y = cellY - component.getStyle().getMarginTop() - (cellHeight - component.getStyle().getMarginHeight() - component.getStyle().getHeight()) / 2.0f;
                break;
            case RIGHT:
                x = cellX + cellWidth - component.getStyle().getMarginRight() - component.getStyle().getWidth();
                y = cellY - component.getStyle().getMarginTop() - (cellHeight - component.getStyle().getMarginHeight() - component.getStyle().getHeight()) / 2.0f;
                break;
            case TOP:
                x = cellX + component.getStyle().getMarginLeft() + (cellWidth - component.getStyle().getMarginWidth() - component.getStyle().getWidth()) / 2.0f;
                y = cellY - component.getStyle().getMarginTop();
                break;
            case BOTTOM:
                x = cellX + component.getStyle().getMarginLeft() + (cellWidth - component.getStyle().getMarginWidth() - component.getStyle().getWidth()) / 2.0f;
                y = cellY - cellHeight + component.getStyle().getMarginBottom() + component.getStyle().getHeight();
                break;
            case TOP_LEFT:
                x = cellX + component.getStyle().getMarginLeft();
                y = cellY - component.getStyle().getMarginTop();
                break;
            case TOP_RIGHT:
                x = cellX + cellWidth - component.getStyle().getMarginRight() - component.getStyle().getWidth();
                y = cellY - component.getStyle().getMarginTop();
                break;
            case BOTTOM_LEFT:
                x = cellX + component.getStyle().getMarginLeft();
                y = cellY - cellHeight + component.getStyle().getMarginBottom() + component.getStyle().getHeight();
                break;
            case BOTTOM_RIGHT:
                x = cellX + cellWidth - component.getStyle().getMarginRight() - component.getStyle().getWidth();
                y = cellY - cellHeight + component.getStyle().getMarginBottom() + component.getStyle().getHeight();
                break;
        }

        component.getStyle().setPosition(x, y, notify);
    }

}
