package org.dtomics.DGUI.gui.text.meshCreator;

public enum TextAlignment {

    CENTER,

    LEFT, RIGHT, TOP, BOTTOM,

    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;


    float cursorX(float boxWidth, float maxLineWidth) {
        if(maxLineWidth > boxWidth) return 0;
        switch (this) {
            case TOP:
            case BOTTOM:
            case CENTER: return (boxWidth - maxLineWidth) * 0.5f;

            case RIGHT:
            case BOTTOM_RIGHT:
            case TOP_RIGHT:
                return boxWidth - maxLineWidth;
        }
        return 0;
    }

    float cursorY(float boxHeight, float maxHeight, float padding) {
        switch (this) {
            case BOTTOM:
            case BOTTOM_RIGHT:
            case BOTTOM_LEFT:
                return - boxHeight + maxHeight + padding;

            case LEFT:
            case RIGHT:
            case CENTER:
                return (-boxHeight + maxHeight) * 0.5f + padding;
        }
        return 0;
    }

}
