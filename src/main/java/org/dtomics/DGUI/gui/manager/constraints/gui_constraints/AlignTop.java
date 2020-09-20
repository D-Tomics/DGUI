package org.dtomics.DGUI.gui.manager.constraints.gui_constraints;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;

/**
 * This constraint aligns a gui to the top of another gui
 *
 * @author Abdul Kareem
 */
public class AlignTop extends D_Constraint {

    private D_Gui gui;
    private float padding;
    private D_Constraint constraint;

    /**
     * @param gui        the gui in relation to which another gui is aligned
     * @param padding    the offset from Top of the gui from which another gui is aligned
     */
    public AlignTop(D_Gui gui, float padding) {
        this(gui, padding, null);
    }

    /**
     * @param gui        the gui in relation to which another gui is aligned
     * @param padding    the offset from Top of the gui from which another gui is aligned
     * @param constraint any other constraints of type <code>D_Constraint</code>. This param could be any other constraints,
     *                   but if it is a constraint that modifies y value then this constraint overwrites the current constraint.
     */
    public AlignTop(D_Gui gui, float padding, D_Constraint constraint) {
        this.gui = gui;
        this.padding = padding;
        this.constraint = constraint;
    }

    @Override
    protected void update(D_Gui gui) {
        if (gui == null) return;
        if (this.gui == null) return;

        gui.getStyle().setY(this.gui.getStyle().getY() - this.gui.getStyle().getPaddingTop() - gui.getStyle().getMarginTop() - padding, false);

        if (constraint != null)
            constraint.run(gui);
    }
}
