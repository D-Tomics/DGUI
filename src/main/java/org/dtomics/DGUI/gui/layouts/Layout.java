package org.dtomics.DGUI.gui.layouts;

import org.dtomics.DGUI.gui.components.D_Container;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.constraints.D_LayoutConstraint;

/**
 * This class is an abstract representation of the layout in a container.
 *
 * @author Abdul Kareem
 */
public abstract class Layout {
    /**
     * This method update the state of layout or the position of children in a container.
     *
     * @param parent the container to which this layout belongs
     */
    public abstract void update(D_Container parent);

    /**
     * sub classes should implement this method if the layout requires some constraints.
     *
     * @param gui the gui to which this constraint is applied
     * @param constraint the constraint that is be applied
     */
    abstract void setConstraint(D_Gui gui, D_LayoutConstraint constraint);

    /**
     * this method is called when a constraint is to be removed
     *
     * @param gui the gui from which the constraint is to be removed
     */
    void removeConstraint(D_Gui gui) { }

    private float maxWidth;
    private float maxHeight;

    /**
     * This method gets max width occupied by children on the parent
     *
     * @return max width occupied by children
     */
    public float getMaxWidth() {
        return maxWidth;
    }

    /**
     * This method gets max height occupied by children on the parent
     *
     * @return max height occupied by children
     */
    public float getMaxHeight() {
        return maxHeight;
    }

    protected void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    protected void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * this method adds a constraint to a child
     * @param gui the child to which the constraint should be applied
     * @param constraint the constraint
     */
    public void addLayoutItem(D_Gui gui, D_LayoutConstraint constraint) {
        setConstraint(gui,constraint);
    }

    /**
     * this method removes the constraint for a child
     *
     * @param gui the child from which the constrain is to be removed
     */
    public void removeLayoutItem(D_Gui gui) {
        removeConstraint(gui);
    }

}
