package org.dtomics.DGUI.gui.components;

public abstract class D_Component extends D_Gui {

    private static final int DEFAULT_FILL_COLOR = 0xFFAABBCC; // 170 187 204
    private static final int DEFAULT_STROKE_SIZE = 1;
    private static final int DEFAULT_STROKE_COLOR = 0;

    public D_Component() {
        style.setBorderColor(DEFAULT_STROKE_COLOR);
        style.setBorderWidth(DEFAULT_STROKE_SIZE);
        style.setBgColor(170, 187, 204);
        //style.setDefaultColor(DEFAULT_FILL_COLOR);
    }

}
