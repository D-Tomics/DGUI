package engine.ui.gui.animation;

import engine.ui.gui.components.D_Gui;

public class D_GuiFadeIn extends D_GuiAnimation{

    private float alpha1;
    private float alpha2;
    private double duration;

    public D_GuiFadeIn(double duration) {
        this.duration = duration;
        alpha1 = 0;
        alpha2 = 1;
    }

    public D_GuiFadeIn(float alpha1, float alpha2, double duration) {
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
