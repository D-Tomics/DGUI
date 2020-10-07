package org.dtomics.DGUI.gui.manager.constraints.gui_constraints;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;

/**
 * This constraint aligns a gui to the right side of another gui
 *
 * @author Abdul Kareem
 */
public class AlignRight extends D_Constraint {

    private final D_Gui gui;
    private final float padding;
    private final D_Constraint constraint;

    /**
     * @param gui     the gui in relation to which another gui is aligned
     * @param padding the offset from right of the gui from which another gui is aligned
     */
    public AlignRight(D_Gui gui, float padding) {
        this(gui, padding, null);
    }

    /**
     * @param gui        the gui in relation to which another gui is aligned
     * @param padding    the offset from right of the gui from which another gui is aligned
     * @param constraint any other constraints of type <code>D_Constraint</code>. This param could be any other constraints,
     *                   but if it is a constraint that modifies x value then this constraint overwrites the current constraint.
     */
    public AlignRight(D_Gui gui, float padding, D_Constraint constraint) {
        this.gui = gui;
        this.padding = padding;
        this.constraint = constraint;
    }

    @Override
    protected void update(D_Gui gui) {
        if (gui == null) return;
        if (this.gui == null) return;

        gui.getStyle().setX(this.gui.getStyle().getX() + this.gui.getStyle().getWidth() - this.gui.getStyle().getPaddingRight() - gui.getStyle().getMarginRight() - gui.getStyle().getWidth() - padding, false);

        if (constraint != null)
            constraint.run(gui);
    }
}
