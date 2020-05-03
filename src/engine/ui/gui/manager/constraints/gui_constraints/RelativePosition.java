package engine.ui.gui.manager.constraints.gui_constraints;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;

public class RelativePosition extends D_Constraint {

    private float relativeX;
    private float relativeY;
    private Window window;

    public RelativePosition(Window window, String xPercent, String yPercent) {
        this(Float.parseFloat(xPercent) / 100.0f, Float.parseFloat(yPercent) / 100.0f);
    }

    @Deprecated
    public RelativePosition(float relativeX, float relativeY) {
        this(Window.INSTANCE,relativeX, relativeY);
    }

    public RelativePosition(Window window,float relativeX, float relativeY) {
        this.window = window;
        this.relativeX = relativeX - 0.5f;
        this.relativeY = 0.5f - relativeY;
    }

    @Override
    public void update(D_Gui gui) {
        gui.getStyle().setPosition(relativeX * window.getWidth(), relativeY * window.getHeight(), false);
    }

}
