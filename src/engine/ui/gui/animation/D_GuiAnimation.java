package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;
import engine.ui.utils.Timer;

public abstract class D_GuiAnimation implements Cloneable{

    protected abstract void onStart(D_Gui gui);
    protected abstract void onStop(D_Gui gui);
    protected abstract boolean run(D_Gui gui);

    private final String name;
    public Timer timer;
    public D_GuiAnimation(String name) {
        this.name = name;
        this.timer = new Timer();
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

    public boolean equals(Object object) {
        if(object == null) return false;
        if(object == this) return true;
        if(!(object instanceof D_GuiAnimation)) return false;
        return ((D_GuiAnimation)object).name.equalsIgnoreCase(this.name);
    }

    public D_GuiAnimation clone() {
        try {
            D_GuiAnimation clone = (D_GuiAnimation) super.clone();
            clone.timer = new Timer();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
