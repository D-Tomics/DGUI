package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;
import engine.ui.utils.Timer;

public abstract class D_GuiAnimation {

    protected abstract void onStart(D_Gui gui);
    protected abstract void onStop(D_Gui gui);
    protected abstract boolean run(D_Gui gui);

    private Timer timer;
    public D_GuiAnimation() {
        timer = new Timer();
    }

    public final void start(D_Gui gui) {
        timer.start();
        onStart(gui);
    }

    public final void stop(D_Gui gui) {
        timer.stop();
        onStop(gui);
    }

    public final boolean update(D_Gui gui) {
        timer.update();
        return run(gui);
    }

    public final void restart(D_Gui gui) {
        this.timer.restart();
        start(gui);
    }

    protected double getTime() { return timer.getTime(); }
}
