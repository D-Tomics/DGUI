package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.text.D_TextBox;

/** This class aligns text in relation to the left side of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignLeft extends D_Constraint {

    private float padding;
    private D_TextBox source;
    public D_TextAlignLeft(D_TextBox text, float padding) {
        this.source = text;
        this.padding = padding;
    }

    @Override
    public void update(D_Gui gui) {
        if(source == null) return;
        source.getPosition().x = gui.getStyle().getX() + padding;
    }
}
