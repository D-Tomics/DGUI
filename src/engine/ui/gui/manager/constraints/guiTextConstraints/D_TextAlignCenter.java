package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_TextConstraint;
import engine.ui.gui.text.D_TextBox;

/**This class aligns a text on the center of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignCenter extends D_TextConstraint {

    public D_TextAlignCenter(D_TextBox source) {
        super(source);
    }

    @Override
    public void update(D_Gui gui) {
        if(getSource() != null) {
            getSource().setPosition(
                    gui.getStyle().getCenterX() - getSource().getBoxWidth() / 2.0f,
                    gui.getStyle().getCenterY() + getSource().getBoxHeight() / 2.0f
            );
        }
    }
}
