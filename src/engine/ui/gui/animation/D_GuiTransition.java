package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;

import java.util.function.BiConsumer;

public class D_GuiTransition extends D_GuiAnimation{
    private float duration;
    private float start;
    private float stop;
    private BiConsumer<D_Gui,Float> transition;

    /**
     * @param duration : time duration in Seconds for which transition takes place
     * @param start : start value of the state the user tries to animate, This value is given to transition BiConsumers accept function
     *              when the animation starts
     * @param stop : stop value of the state the user tries to animate, This value is given to transition BiConsumers accept function
     *              when the animation stops
     * @param transition : a BiConsumer implementation that gets executed on start , stop and during the life time of transition,
     *                   this function represents the state of the gui which changes over time. This function provides two arguments,
     *                   the gui in which the animation is started and a float value that represents the change from start to end of
     *                   animation. This value lies between start and stop value and changes linearly during the entire duration
     */
    public D_GuiTransition(float duration, float start, float stop, BiConsumer<D_Gui,Float> transition) {
        this.duration   = duration;
        this.start      = start;
        this.stop       = stop;
        this.transition = transition;
    }

    @Override
    public void onStart(D_Gui gui) { this.transition.accept(gui,start); }

    @Override
    public void onStop(D_Gui gui) { this.transition.accept(gui,stop); }

    @Override
    public boolean run(D_Gui gui) {
        if(getTime() < duration) {
            this.transition.accept(gui,start + (float)(getTime()/duration) * (stop - start));
            return false;
        }
        return true;
    }

}
