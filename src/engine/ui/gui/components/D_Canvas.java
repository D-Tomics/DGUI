package engine.ui.gui.components;

import engine.ui.gui.renderer.Texture;
import engine.ui.utils.observers.Observable;

public class D_Canvas extends D_Gui{

    public D_Canvas(int width, int height) {
        this.style.setSize(width, height);
    }

    public void attachTexture(Texture texture) {
        this.style.setBgTexture(texture);
    }

    @Override
    protected void onUpdate() { }

    @Override
    protected void onStateChange(Observable o) { }
}
