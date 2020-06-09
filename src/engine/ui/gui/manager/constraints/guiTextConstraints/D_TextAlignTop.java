package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.text.D_TextBox;

/** This class aligns text in relation to the top of a gui
 *
 * @author Abdul Kareem
 */
public class D_TextAlignTop extends D_Constraint {

    private D_TextBox source;
    private float padding;
    private D_Constraint constraint;

    /**
     * @param source     the text that should be aligned
     * @param padding    the offset from top of the gui from which the text is aligned
     */
    public D_TextAlignTop(D_TextBox source, float padding) {
        this(source, padding, null);
    }

    /**
     * @param source     the text that should be aligned
     * @param padding    the offset from top of the gui from which the text is aligned
     * @param constraint any other constraints of type <code>D_Constraint</code>. This param could be any other constraints,
     *                   but if it is a constraint that modifies y position of the text then it overwrites this constraint.
     */
    public D_TextAlignTop(D_TextBox source, float padding, D_Constraint constraint) {
        this.source = source;
        this.padding = padding;
        this.constraint = constraint;
    }

    @Override
    public void update(D_Gui gui) {
        if(source == null) return;
        source.getPosition().y = gui.getStyle().getY() - gui.getStyle().getPaddingTop() - padding;
        if(constraint != null)
            constraint.run(gui);
    }
}
