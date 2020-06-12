package engine.ui.gui.manager.constraints.gui_constraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;

/**
 * This constraint fills a gui box inside another gui
 *
 * @author Abdul Kareem
 */
public class Fill extends D_Constraint {

    private D_Gui gui;
    private boolean fillXAxis;
    private boolean fillYAxis;
    private float marginWidth;
    private float marginHeight;
    private D_Constraint constraint;

    /**
     * @param gui the gui to which another gui is filled
     */
    public Fill(D_Gui gui) {
        this(gui, true, true, null);
    }

    /**
     * @param gui       the gui to which another gui is filled
     * @param fillXAxis whether to fill on x axis
     * @param fillYAxis whether to fill on y axis
     */
    public Fill(D_Gui gui, boolean fillXAxis, boolean fillYAxis) {
        this(gui, fillXAxis, fillYAxis, null);
    }

    /**
     * @param gui        the gui to which another gui is filled
     * @param fillXAxis  whether to fill on x axis
     * @param fillYAxis  whether to fill on y axis
     * @param constraint any other constraints of type <code>D_Constraint</code>.
     *                   If {@code fillXAxis} is true and {@code constraint} modifies width of the gui then {@code constraint} overwrites this constraint.
     *                   Similarlly if {@code fillYAxis} is true and {@code constraint} modifies height of the gui then {@code constraint} overwrites this constraint.
     */
    public Fill(D_Gui gui, boolean fillXAxis, boolean fillYAxis, D_Constraint constraint) {
        this(gui, fillXAxis, fillYAxis, 0, 0, constraint);
    }

    /**
     * @param gui          the gui to which another gui is filled
     * @param fillXAxis    whether to fill on x axis
     * @param fillYAxis    whether to fill on y axis
     * @param marginWidth  the width that is not filled inside a gui
     * @param marginHeight the height that is not filled inside a gui
     */
    public Fill(D_Gui gui, boolean fillXAxis, boolean fillYAxis, float marginWidth, float marginHeight) {
        this(gui, fillXAxis, fillYAxis, marginWidth, marginHeight, null);
    }

    /**
     * @param gui          the gui to which another gui is filled
     * @param fillXAxis    whether to fill on x axis
     * @param fillYAxis    whether to fill on y axis
     * @param marginWidth  the width that is not filled inside a gui
     * @param marginHeight the height that is not filled inside a gui
     * @param constraint   any other constraints of type <code>D_Constraint</code>.
     *                     If {@code fillXAxis} is true and {@code constraint} modifies width of the gui then {@code constraint} overwrites this constraint.
     *                     Similarlly if {@code fillYAxis} is true and {@code constraint} modifies height of the gui then {@code constraint} overwrites this constraint.
     */
    public Fill(D_Gui gui, boolean fillXAxis, boolean fillYAxis, float marginWidth, float marginHeight, D_Constraint constraint) {
        this.gui = gui;
        this.fillXAxis = fillXAxis;
        this.fillYAxis = fillYAxis;
        this.marginWidth = marginWidth;
        this.marginHeight = marginHeight;
        this.constraint = constraint;
    }

    @Override
    protected void update(D_Gui gui) {
        if(this.gui == null) return;

        if(fillXAxis)
            gui.getStyle().setWidth(this.gui.getStyle().getWidth() - gui.getStyle().getMarginWidth() - this.gui.getStyle().getPaddingWidth() - marginWidth, false);
        if(fillYAxis)
            gui.getStyle().setHeight(this.gui.getStyle().getHeight() - gui.getStyle().getMarginHeight() - this.gui.getStyle().getPaddingHeight() - marginHeight, false);

        if(constraint != null)
            constraint.run(gui);
    }
}
