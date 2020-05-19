package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;
import engine.ui.utils.Timer;

/**
 * This class represents an abstract representation of a ui animation.
 * Any type of animation required for a ui should extend this class and implement its abstract methods.
 * The animation progress is tracked via a <Code>Timer</Code> which tracks time passed since the start of
 * the animation. The subclasses can use this timer via {@code getTimer} method to update or transit the current
 * state of animation.
 *
 * @see engine.ui.gui.components.D_Gui#startAnimation
 * @author Abdul kareem
 */
public abstract class D_GuiAnimation implements Cloneable{

    /**
     * this method is implemented by sub classes. It is only executed once at the start of an animation.
     *
     * @see #start(D_Gui)
     * @param gui the gui on which the animation is applied
     */
    protected abstract void onStart(D_Gui gui);

    /**
     * this method is implemented by sub classes. It is only executed once at the end of an animation.
     *
     * @see #stop(D_Gui)
     * @param gui the gui on which the animation is applied
     */
    protected abstract void onStop(D_Gui gui);

    /**
     * this method is implemented by sub classes. This method is executed every frame during entire life time of
     * the animation.
     *
     * @see #update(D_Gui)
     * @param gui the gui on which the animation is applied
     * @return true when animation is stopped and false if animation is not started or is still running
     */
    protected abstract boolean run(D_Gui gui);

    private final String name;
    public Timer timer;
    public D_GuiAnimation(String name) {
        this.name = name;
        this.timer = new Timer();
    }

    /**
     * this method starts the animation
     *
     * @param gui gui on which this animation is applied
     */
    public final void start(D_Gui gui) {
        timer.start();
        onStart(gui);
    }


    /**
     * this method stops the animation
     *
     * @param gui gui on which this animation is applied
     */
    public final void stop(D_Gui gui) {
        timer.stop();
        onStop(gui);
    }

    /**
     * this method updates the animation
     *
     * @param gui gui on which this animation is applied
     */
    public final boolean update(D_Gui gui) {
        timer.update();
        return run(gui);
    }

    /**
     * this method restarts the animation
     *
     * @param gui gui on which this animation is applied
     */
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
