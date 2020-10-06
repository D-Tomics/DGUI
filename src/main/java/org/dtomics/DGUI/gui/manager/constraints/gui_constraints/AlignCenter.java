package org.dtomics.DGUI.gui.manager.constraints.gui_constraints;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;

/**
 * This constraint aligns a gui to the center of another gui
 *
 * @author Abdul Kareem
 */
public class AlignCenter extends D_Constraint {

    private final D_Gui gui;
    private final boolean xCenter;
    private final boolean yCenter;
    private final D_Constraint constraint;

    /**
     * @param gui     the gui in relation to which another gui is aligned
     * @param xCenter whether or not to align on x axis
     * @param yCenter whether or not to align on y axis
     */
    public AlignCenter(D_Gui gui, boolean xCenter, boolean yCenter) {
        this(gui, xCenter, yCenter, null);
    }

    /**
     * @param gui        the gui in relation to which another gui is aligned
     * @param xCenter    whether or not to align on x axis
     * @param yCenter    whether or not to align on y axis
     * @param constraint any other constraints of type <code>D_Constraint</code>.
     *                   If it xCenter is enabled and this constraint modifies x value of the gui to be aligned,
     *                   then this constrain overwrites the current constraint. Similarly, if yCenter is enabled and this
     *                   constraint modifies y value, then it overwrites current constraint.
     */
    public AlignCenter(D_Gui gui, boolean xCenter, boolean yCenter, D_Constraint constraint) {
        this.gui = gui;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.constraint = constraint;
    }

    @Override
    protected void update(D_Gui gui) {
        if (gui == null) return;
        if (this.gui == null) return;

        if (xCenter) gui.getStyle().setCenterX(this.gui.getStyle().getCenterX(), false);
        if (yCenter) gui.getStyle().setCenterY(this.gui.getStyle().getCenterY(), false);

        if (constraint != null)
            constraint.run(gui);
    }
}
