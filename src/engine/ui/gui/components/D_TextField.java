package engine.ui.gui.components;

import engine.ui.gui.manager.constraints.guiTextConstraints.D_TextAlign;
import engine.ui.utils.observers.Observable;

/**
 * This class lets the user type a text on a line that spans over multiple columns.
 *
 * @author Abdul Kareem
 */
public class D_TextField extends D_TextComponent{

    public D_TextField(String text, int cols) {
        super(text, 1, cols);
        this.addConstraint(new D_TextAlign(this.textBox,5,0,5,0));
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onStateChange(Observable o) {

    }
}
