package engine.ui.gui.components;

import engine.ui.gui.renderer.Texture;
import engine.ui.utils.colors.Color;

public class D_Canvas extends D_Panel {

    public D_Canvas(int width, int height) {
        this.style.setSize(width, height);
        this.style.setBgColor(Color.WHITE);
    }

    public void attachTexture(Texture texture) {
        this.style.setBgTexture(texture);
    }

}
