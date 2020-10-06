package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.gui.renderer.Loader;
import org.dtomics.DGUI.gui.renderer.Texture;
import org.dtomics.DGUI.utils.colors.Color;
import org.dtomics.DGUI.utils.observers.Observable;

public class D_Icon extends D_Gui {

    public D_Icon(Loader loader, String path, float width, float height, boolean flipVertical) {
        this(loader.loadTexture(path, flipVertical), width, height);
    }

    public D_Icon(Texture image, float width, float height) {
        this.style
                .setBgTexture(image)
                .setSize(width, height)
                .setBorderWidth(0)
                .setAlpha(0)
                .setBgColor(Color.WHITE)
                .setMargin(0)
                .setPadding(0);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onStateChange(Observable o) {
    }
}
