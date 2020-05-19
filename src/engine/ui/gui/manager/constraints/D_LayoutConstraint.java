package engine.ui.gui.manager.constraints;

/**
 * This class is a super class for all the layout constraints.
 *
 * @author Abdul Kareem
 */
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
