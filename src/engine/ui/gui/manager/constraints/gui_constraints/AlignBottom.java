package engine.ui.gui.manager.constraints.gui_constraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;

/**
 * This constraint aligns a gui to the Bottom of another gui
 *
 * @author Abdul Kareem
 */
public class AlignBottom extends D_Constraint {

    private D_Gui gui;
    private float padding;
    private D_Constraint constraint;

    /**
     * @param gui        the gui in relation to which another gui is aligned
     * @param padding    the offset from Bottom of the gui from which another gui is aligned
     */
    public AlignBottom(D_Gui gui, float padding) {
        this(gui, padding, null);
    }

    /**
     *
     * @param gui        the gui in relation to which another gui is aligned
     * @param padding    the offset from Bottom of the gui from which another gui is aligned
     * @param constraint any other constraints of type <code>D_Constraint</code>. This param could be any other constraints,
     *                   but if it is a constraint that modifies y value then this constraint overwrites the current constraint
     */
    public AlignBottom(D_Gui gui, float padding, D_Constraint constraint) {
        this.gui = gui;
        this.padding = padding;
        this.constraint = constraint;
    }

    @Override
    protected void update(D_Gui gui) {
        if(gui == null) return;
        if(this.gui == null) return;

        gui.getStyle().setY(this.gui.getStyle().getY() - this.gui.getStyle().getHeight() + this.gui.getStyle().getPaddingBottom() + gui.getStyle().getMarginBottom() + gui.getStyle().getHeight() + padding, false);
        if(constraint != null)
            constraint.run(gui);
    }
}
