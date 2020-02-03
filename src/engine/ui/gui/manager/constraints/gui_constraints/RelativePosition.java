package engine.ui.gui.manager.constraints.gui_constraints;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;

public class RelativePosition extends D_Constraint {

    private float relativeX;
    private float relativeY;

    public RelativePosition(String xPercent, String yPercent) {
        this(Float.parseFloat(xPercent) / 100.0f, Float.parseFloat(yPercent) / 100.0f);
    }

    public RelativePosition(float relativeX, float relativeY) {
        this.relativeX = relativeX - 0.5f;
        this.relativeY = 0.5f - relativeY;
    }

    @Override
    public void update(D_Gui gui) {
        gui.getStyle().setPosition(relativeX * Window.INSTANCE.getWidth(), relativeY * Window.INSTANCE.getHeight(), false);
    }

}
