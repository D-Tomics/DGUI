package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;
import engine.ui.utils.Timer;

public abstract class D_GuiAnimation {

    private boolean started;
    private Timer timer;
    public D_GuiAnimation() {
        timer = new Timer();
    }

    public final void start(D_Gui gui) {
        if(started) return;
        started = true;
        timer.start();
        onStart(gui);
    }

    public final void stop(D_Gui gui) {
        if(!started) return;
        timer.stop();
        started = false;
        onStop(gui);
    }

    public final void update(D_Gui gui) {
        if(!started) return;
        start(gui);
        timer.update();
        if(run(gui)) {
            stop(gui);
        }
    }

    public final void restart() {
        this.timer.restart();
    }

    public final Timer getTimer() {
        return timer;
    }

    protected abstract boolean run(D_Gui gui);

    protected void onStart(D_Gui gui){};
    protected void onStop(D_Gui gui){};

}
