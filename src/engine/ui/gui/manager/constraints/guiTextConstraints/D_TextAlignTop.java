package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_TextConstraint;
import engine.ui.gui.text.D_TextBox;

public class D_TextAlignTop extends D_TextConstraint {

    private float padding;

    public D_TextAlignTop(D_TextBox source, float padding) {
        super(source);
        this.padding = padding;
    }

    @Override
    public void update(D_Gui gui) {
        D_TextBox text = getSource();
        if(text == null) return;
        text.getPosition().y = gui.getStyle().getY() - padding;
    }
}
