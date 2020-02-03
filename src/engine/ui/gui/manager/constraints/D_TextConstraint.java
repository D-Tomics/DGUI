package engine.ui.gui.manager.constraints;

import engine.ui.gui.text.D_TextBox;

public abstract class D_TextConstraint extends D_Constraint {

    private D_TextBox source;
    public D_TextConstraint(D_TextBox source) {
        this.source = source;
    }

    protected D_TextBox getSource() {
        return source;
    }

}
