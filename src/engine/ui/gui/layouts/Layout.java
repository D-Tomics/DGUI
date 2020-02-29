package engine.ui.gui.layouts;

import engine.ui.gui.components.D_Container;

public abstract class Layout {
    public abstract void update(D_Container parent);

    private float maxWidth;
    private float maxHeight;

    public float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

}
