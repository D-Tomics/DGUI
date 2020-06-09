package engine.ui.gui.components;

import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignLeft;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlignTop;
import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextFill;
import engine.ui.utils.observers.Observable;

/**
 * This class lets the user type a text on a line that spans over multiple columns.
 *
 * @author Abdul Kareem
 */
public class D_TextField extends D_TextComponent{

    public D_TextField(String text, int cols) {
        super(text, 1, cols);
        this.addConstraint(new D_TextAlignTop(this.textBox,0,new D_TextAlignLeft(this.textBox,5, new D_TextFill(this.textBox,10,0))));
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onStateChange(Observable o) {

    }
}
