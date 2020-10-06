package org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_Constraint;
import org.dtomics.DGUI.gui.text.D_TextBox;

/**
 * This class aligns text in relation to the bottom of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignBottom extends D_Constraint {

    private final D_TextBox source;
    private final float padding;
    private final D_Constraint constraint;

    /**
     * @param source  the text that should be aligned
     * @param padding the offset from top of the gui from which the text is aligned
     */
    public D_TextAlignBottom(D_TextBox source, float padding) {
        this(source, padding, null);
    }

    /**
     * @param source     the text that should be aligned
     * @param padding    the offset from bottom of the gui from which the text is aligned
     * @param constraint any other constraints of type <code>D_Constraint</code>. This param could be any other constraints,
     *                   but if it is a constraint that modifies y position of the text then it overwrites this constraint.
     */
    public D_TextAlignBottom(D_TextBox source, float padding, D_Constraint constraint) {
        this.source = source;
        this.padding = padding;
        this.constraint = constraint;
    }

    @Override
    public void update(D_Gui gui) {
        if (source == null) return;
        if (gui == null) return;
        source.getPosition().y = gui.getStyle().getY() - gui.getStyle().getHeight() + gui.getStyle().getPaddingBottom() + source.getBoxHeight() + padding;
        if (constraint != null)
            constraint.run(gui);
    }
}
