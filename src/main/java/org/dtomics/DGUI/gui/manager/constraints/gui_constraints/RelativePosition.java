package org.dtomics.DGUI.gui.manager.constraints.gui_constraints;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;

/**
 * This class represents a position constraint that positions the component in relation to a windows position.
 * This overrides the position set by layouts of a container.
 *
 * @author kareem
 */
public class RelativePosition extends D_Constraint {

    private final float relativeX;
    private final float relativeY;
    private final Window window;

    /**
     * @param window   the window on which the position is set
     * @param xPercent x position in percentage (0 - 100)
     * @param yPercent y position in percentage (0 - 100)
     */
    public RelativePosition(Window window, String xPercent, String yPercent) {
        this(window, Float.parseFloat(xPercent) / 100.0f, Float.parseFloat(yPercent) / 100.0f);
    }

    /**
     * @param window   the window on which the position is set
     * @param xPercent x position (0 - 1)
     * @param yPercent y position (0 - 1)
     */
    public RelativePosition(Window window, float relativeX, float relativeY) {
        this.window = window;
        this.relativeX = relativeX - 0.5f;
        this.relativeY = 0.5f - relativeY;
    }

    @Override
    public void update(D_Gui gui) {
        gui.getStyle().setPosition(relativeX * window.getWidth(), relativeY * window.getHeight(), false);
    }

}
