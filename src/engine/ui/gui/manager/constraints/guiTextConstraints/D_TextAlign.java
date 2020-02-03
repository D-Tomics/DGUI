package engine.ui.gui.manager.constraints.guiTextConstraints;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_Constraint;
import engine.ui.gui.manager.constraints.D_TextConstraint;
import engine.ui.gui.text.D_TextBox;

public class D_TextAlign extends D_TextConstraint {

    private float padLeft;
    private float padTop;
    private float padRight;
    private float padBottom;

    public D_TextAlign(D_TextBox source, float padLeft, float padTop, float padRight, float padBottom) {
        super(source);
        this.padLeft = padLeft;
        this.padTop = padTop;
        this.padRight = padRight;
        this.padBottom = padBottom;
    }

    @Override
    protected void init(D_Gui gui) {
        super.getSource().setBoxSize(
                super.getSource().getBoxWidth() - padLeft - padRight,
                super.getSource().getBoxHeight() - padTop - padBottom
        );
    }

    @Override
    protected void update(D_Gui gui) {
        var text = getSource();
        var style = gui.getStyle();

        text.setPosition(
                style.getX() + padLeft,
                style.getY() - padTop
        );
    }

}
