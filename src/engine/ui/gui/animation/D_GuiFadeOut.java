package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;

/**
 * This class represents an Fade Out effect that changes alpha of the gui linearly form a high to low value w.r.t
 * life time of the animation.
 *
 * @see D_GuiAnimation
 * @author Abdul Kareem
 */

public class D_GuiFadeOut extends D_GuiAnimation{

    private float alpha1;
    private float alpha2;
    private double duration;

    public D_GuiFadeOut(String name,double duration) {
        super(name);
        this.duration = duration;
        this.alpha1 = 1;
        this.alpha2 = 0;
    }

    public D_GuiFadeOut(String name,float alpha1, float alpha2, double duration) {
        super(name);
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
        this.duration = duration;
    }

    @Override
    public boolean run(D_Gui gui) {
        gui.getStyle().setAlpha((1.0f - (float) (getTime()/duration)) * Math.abs(alpha1 - alpha2));
        return getTime() >= duration;
    }

    @Override
    public void onStart(D_Gui gui) {
        gui.getStyle().setAlpha(alpha1);
    }

    @Override
    public void onStop(D_Gui gui) {
        gui.getStyle().setAlpha(alpha2);
    }
}
