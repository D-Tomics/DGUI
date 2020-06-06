package engine.ui.gui.components;

import engine.ui.gui.renderer.Loader;
import engine.ui.gui.renderer.Texture;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;

public class D_Icon extends D_Gui{

    public D_Icon(Loader loader, String path, float width, float height, boolean flipVertical) {
        this(loader.loadTexture(path, flipVertical), width, height);
    }

    public D_Icon(Texture image, float width, float height) {
        this.style
                .setBgTexture(image)
                .setSize(width, height)
                .setBorderWidth(0)
                .setAlpha(0)
                .setBgColor(Color.WHITE);
    }

    @Override
    protected void onUpdate() {}

    @Override
    protected void onStateChange(Observable o) {}
}
