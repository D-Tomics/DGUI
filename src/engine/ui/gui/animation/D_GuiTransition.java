package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;

import java.util.function.BiConsumer;

public class D_GuiTransition extends D_GuiAnimation{
    private float duration;
    private float start;
    private float stop;
    private BiConsumer<D_Gui,Float> transition;
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
