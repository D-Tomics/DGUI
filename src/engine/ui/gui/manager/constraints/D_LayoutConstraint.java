package engine.ui.gui.manager.constraints;

public abstract class D_LayoutConstraint implements Cloneable{
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
