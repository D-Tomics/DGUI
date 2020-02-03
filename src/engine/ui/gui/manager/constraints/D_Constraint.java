package engine.ui.gui.manager.constraints;

import engine.ui.gui.components.D_Gui;

public abstract class D_Constraint {

    private boolean init;
    protected void init(D_Gui gui) { }
    protected abstract void update(D_Gui gui);

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
