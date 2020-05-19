package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.text.D_TextBox;

/** This class aligns text in relation to the top of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignTop extends D_Constraint {

    private float padding;
    private D_TextBox source;

    public D_TextAlignTop(D_TextBox source, float padding) {
        this.source = source;
        this.padding = padding;
    }

    @Override
    public void update(D_Gui gui) {
        D_TextBox text = source;
        if(text == null) return;
        text.getPosition().y = gui.getStyle().getY() - padding;
    }
}
