package org.dtomics.DGUI.gui.animation;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This class represents a Fade In effect that changes alpha of the gui linearly from low to high value w.r.t
 * the life time of the animation.
 *
 * @see D_GuiAnimation
 * @author Abdul kareem
 */
public class D_GuiFadeIn extends D_GuiAnimation{

    private float alpha1;
    private float alpha2;
    private double duration;

    public D_GuiFadeIn(String name,double duration) {
        super(name);
        this.duration = duration;
        alpha1 = 0;
        alpha2 = 1;
    }

    public D_GuiFadeIn(String name,float alpha1, float alpha2, double duration) {
        super(name);
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
        this.duration = duration;
    }

    @Override
    public boolean run(D_Gui gui) {
        gui.getStyle().setAlpha((float)(getTime()/duration) * Math.abs(alpha2 - alpha1));
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
