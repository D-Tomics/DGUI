package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.text.D_TextBox;

/**This class aligns a text on the center of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignCenter extends D_Constraint {

    private D_TextBox source;

    public D_TextAlignCenter(D_TextBox source) {
        this.source = source;
    }

    @Override
    public void update(D_Gui gui) {
        if(source != null) {
            source.setPosition(
                    gui.getStyle().getCenterX() - source.getBoxWidth() / 2.0f,
                    gui.getStyle().getCenterY() + source.getBoxHeight() / 2.0f
            );
        }
    }
}
