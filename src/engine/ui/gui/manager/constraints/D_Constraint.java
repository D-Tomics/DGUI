package engine.ui.gui.manager.constraints;

import engine.ui.gui.components.D_Gui;

/**
 * This is an abstract representation of a constraint that needs to be checked every frame like the position and size a component
 *
 * @author Abdul Kareem
 */
public abstract class D_Constraint {

    private boolean init;
    protected void init(D_Gui gui) { }
    protected abstract void update(D_Gui gui);

    /**
     * This method is called every frame
     *
     * @param gui gui to which this constraint is applied
     */
    public void run(D_Gui gui) {
        if(!init) {
            init(gui);
            init = true;
        }
        this.update(gui);
    }

    public void reset() {
        init = false;
    }

}
