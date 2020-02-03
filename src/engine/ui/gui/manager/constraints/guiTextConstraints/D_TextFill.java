package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_TextConstraint;
import engine.ui.gui.text.D_TextBox;

public class D_TextFill extends D_TextConstraint {

    public D_TextFill(D_TextBox source) {
        super(source);
    }

    @Override
    public void update(D_Gui gui) {
        var text = getSource();
        var style = gui.getStyle();
        text.setPosition(style.getCenterX(),style.getCenterY());
    }
}
