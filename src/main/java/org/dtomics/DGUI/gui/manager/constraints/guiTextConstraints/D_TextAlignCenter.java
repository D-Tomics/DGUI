package org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;
import org.dtomics.DGUI.gui.text.D_TextBox;

/**
 * This class aligns a text box to the center of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignCenter extends D_Constraint {

    private final D_TextBox source;
    private final boolean xCenter;
    private final boolean yCenter;
    private final D_Constraint constraint;

    /**
     * @param source the text that should be aligned
     */
    public D_TextAlignCenter(D_TextBox source) {
        this(source, null);
    }

    /**
     * @param source     the text that should be aligned
     * @param constraint any other constraints of type <code>D_Constraint</code>.
     *                   If this constraint modifies x position of the text,
     *                   then this constrain overwrites the current constraint. Similarly, if this
     *                   constraint modifies y position, then it overwrites current constraint.
     */
    public D_TextAlignCenter(D_TextBox source, D_Constraint constraint) {
        this(source, true, true, constraint);
    }

    /**
     * @param source  the text that should be aligned
     * @param xCenter whether or not to align on x axis
     * @param yCenter whether or not to align on y axis
     */
    public D_TextAlignCenter(D_TextBox source, boolean xCenter, boolean yCenter) {
        this(source, xCenter, yCenter, null);
    }

    /**
     * @param source     the text that should be aligned
     * @param xCenter    whether or not to align on x axis
     * @param yCenter    whether or not to align on y axis
     * @param constraint any other constraints of type <code>D_Constraint</code>.
     *                   If it xCenter is enabled and this constraint modifies x position of the text,
     *                   then this constrain overwrites the current constraint. Similarly, if yCenter is enabled and this
     *                   constraint modifies y position, then it overwrites current constraint.
     */
    public D_TextAlignCenter(D_TextBox source, boolean xCenter, boolean yCenter, D_Constraint constraint) {
        this.source = source;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.constraint = constraint;
    }

    @Override
    public void update(D_Gui gui) {
        if (source == null) return;

        if (xCenter) source.getPosition().x = gui.getStyle().getCenterX() - source.getBoxWidth() * 0.5f;
        if (yCenter) source.getPosition().y = gui.getStyle().getCenterY() + source.getBoxHeight() * 0.5f;

        if (constraint != null)
            constraint.run(gui);
    }
}
