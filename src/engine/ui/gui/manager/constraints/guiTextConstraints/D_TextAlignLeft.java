package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_TextConstraint;
import engine.ui.gui.text.D_TextBox;

public class D_TextAlignLeft extends D_TextConstraint {

    private float padding;
    public D_TextAlignLeft(D_TextBox text, float padding) {
        super(text);
        this.padding = padding;
    }

    @Override
    public void update(D_Gui gui) {
        if(getSource() == null) return;
        super.getSource().getPosition().x = gui.getStyle().getX() + padding;
    }
}
