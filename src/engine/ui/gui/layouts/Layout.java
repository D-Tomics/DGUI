package engine.ui.gui.layouts;

import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;

public abstract class Layout {
    public abstract void update(D_Container parent);
    abstract void setConstraint(D_Gui gui, D_LayoutConstraint constraint);

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

    public void addLayoutGui(D_Gui gui, D_LayoutConstraint constraint) {
        setConstraint(gui,constraint);
    }

}
