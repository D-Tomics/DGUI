package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.Style;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.text.D_TextBox;

/** This class aligns text in relation to the bottom of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignBottom extends D_Constraint {

    private float padding;
    private D_TextBox source;

    public D_TextAlignBottom(D_TextBox source, float padding) {
        this.source = source;
        this.padding = padding;
    }

    @Override
    public void update(D_Gui gui) {
        D_TextBox text = source;
        if(text == null) return;
        Style style = gui.getStyle();
        text.getPosition().y = style.getY() + text.getMaxTextHeight() - style.getHeight() + padding;
    }
}
