package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.gui.renderer.Texture;
import org.dtomics.DGUI.utils.colors.Color;

public class D_Canvas extends D_Panel {

    public D_Canvas(int width, int height) {
        this.style.setSize(width, height);
        this.style.setBgColor(Color.WHITE);
    }

    public void attachTexture(int texture, int width, int height) {
        this.style.setBgTexture(new Texture(texture, width, height));
    }

    public void attachTexture(Texture texture) {
        this.style.setBgTexture(texture);
    }

}
