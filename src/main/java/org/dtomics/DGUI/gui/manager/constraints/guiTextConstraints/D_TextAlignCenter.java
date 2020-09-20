package org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;
import org.dtomics.DGUI.gui.text.D_TextBox;

/**This class aligns a text on the center of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignCenter extends D_Constraint {

    private D_TextBox source;
    private boolean xCenter;
    private boolean yCenter;
    private D_Constraint constraint;

    /**
     * @param source     the text that should be aligned
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
     * @param source     the text that should be aligned
     * @param xCenter    whether or not to align on x axis
     * @param yCenter    whether or not to align on y axis
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
        if(source == null) return;

        if(xCenter) source.getPosition().x = gui.getStyle().getCenterX() - Math.min(source.getBoxWidth(), source.getMaxTextWidth()) / 2.0f;
        if(yCenter) source.getPosition().y = gui.getStyle().getCenterY() + Math.min(source.getBoxHeight(), source.getMaxTextHeight()) / 2.0f;

        if(constraint != null)
            constraint.run(gui);
    }
}
