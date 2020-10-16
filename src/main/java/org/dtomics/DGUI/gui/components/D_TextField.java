package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignLeft;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignTop;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextFill;
import org.dtomics.DGUI.gui.text.meshCreator.TextAlignment;
import org.dtomics.DGUI.utils.observers.Observable;

/**
 * This class lets the user type a text on a line that spans over multiple columns.
 *
 * @author Abdul Kareem
 */
public class D_TextField extends D_TextComponent {

    public D_TextField(String text, int width) {
        super(text, width, 25);
        this.textBox.setTextAlignment(TextAlignment.LEFT);
        this.addConstraint(new D_TextAlignCenter(this.textBox, new D_TextAlignLeft(this.textBox, 5, new D_TextFill(this.textBox, 10, 0))));
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onStateChange(Observable o) {

    }
}
