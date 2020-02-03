package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_TextConstraint;
import engine.ui.gui.text.D_TextBox;

public class D_TextAlignBottom extends D_TextConstraint {

    private float padding;

    public D_TextAlignBottom(D_TextBox source, float padding) {
        super(source);
        this.padding = padding;
    }

    @Override
    public void update(D_Gui gui) {
        var text = getSource();
        var style = gui.getStyle();
        text.getPosition().y = style.getY() + text.getMaxTextHeight() - style.getHeight() + padding;
    }
}
