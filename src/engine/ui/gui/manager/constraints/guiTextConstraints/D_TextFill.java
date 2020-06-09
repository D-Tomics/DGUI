package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.text.D_TextBox;

/**
 * This constraint fills the text box inside a gui
 *
 * @author Abdul Kareem
 */
public class D_TextFill extends D_Constraint {

    private D_TextBox source;
    private float marginWidth, marginHeight;
    private D_Constraint constraint;

    /**
     * @param source       the text box to be filled inside a gui
     * @param marginWidth  the width that is not filled inside a gui
     * @param marginHeight the height that is not filled inside a gui
     */
    public D_TextFill(D_TextBox source, float marginWidth, float marginHeight) {
        this(source, marginWidth, marginHeight, null);
    }

    /**
     * @param source       the text box to be filled inside a gui
     * @param marginWidth  the width that is not filled inside a gui
     * @param marginHeight the height that is not filled inside a gui
     * @param constraint   any other constraints of type <Code>D_Constraint</Code>. If this modifies width or height
     *                     then it overwrites current constraint.
     */
    public D_TextFill(D_TextBox source, float marginWidth, float marginHeight, D_Constraint constraint) {
        this.source = source;
        this.marginWidth = marginWidth;
        this.marginHeight = marginHeight;
        this.constraint = constraint;
    }

    @Override
    protected void update(D_Gui gui) {
        if(source == null) return;
        source.setBoxSize(
                gui.getStyle().getWidth() - marginWidth,
                gui.getStyle().getHeight() - marginHeight
        );
        if(constraint != null)
            constraint.run(gui);
    }
}
