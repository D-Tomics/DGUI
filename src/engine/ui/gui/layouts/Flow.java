package engine.ui.gui.layouts;

import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;

import java.util.ArrayList;

public class Flow extends Layout {

    private ArrayList<D_Gui> currentRow = new ArrayList<>();

    @Override
    public void update(D_Container parent) {
        if(parent == null) return;
        if(!parent.isVisible()) return;
        if(parent.getChildList() == null) return;

        float x = parent.getStyle().getX() + parent.getStyle().getPaddingLeft();
        float y = parent.getStyle().getY() - parent.getStyle().getPaddingTop();

        currentRow.clear();
        for(D_Gui child : parent.getChildList()){
            if(x + child.getStyle().getWidth() + child.getStyle().getMarginWidth() + parent.getStyle().getPaddingRight() > parent.getStyle().getX() + parent.getStyle().getWidth()) {

                float rowHeight = getRowHeight(currentRow);
                float rowWidth = getRowWidth(currentRow);
                if(getMaxHeight() < Math.abs(y - parent.getStyle().getY()) + rowHeight)
                    setMaxHeight(Math.abs(y - parent.getStyle().getY()) + rowHeight);
                if(getMaxWidth() < rowWidth)
                    setMaxWidth(rowWidth);

                positionComponents(parent,currentRow,y);
                x = parent.getStyle().getX() + parent.getStyle().getPaddingLeft();
                y -=  rowHeight;
                currentRow.clear();
            }

            float height = Math.abs(y - parent.getStyle().getY()) + child.getStyle().getHeight() + child.getStyle().getMarginHeight();
            if( height > getMaxHeight()) setMaxHeight(height);

            if(height > parent.getStyle().getHeight())
                parent.getStyle().setHeight(height, false);

            currentRow.add(child);
            x += child.getStyle().getWidth() + child.getStyle().getMarginWidth();
        }

        float rowWidth = getRowWidth(currentRow);
        if(getMaxWidth() < rowWidth) setMaxWidth(rowWidth);

        positionComponents(parent,currentRow,y);
    }

    private void positionComponents(D_Container parent,ArrayList<D_Gui> row, float y) {
        float w = getRowWidth(row);
        float h = getRowHeight(row);

        float x = parent.getStyle().getCenterX() + parent.getStyle().getPaddingLeft() - w / 2.0f;
        for(D_Gui gui : row) {
            gui.getStyle().setPosition(x + gui.getStyle().getMarginLeft(),y - h / 2.0f + gui.getStyle().getHeight() / 2.0f );
            x += gui.getStyle().getWidth() + gui.getStyle().getMarginWidth();
        }
    }

    private float getRowHeight(ArrayList<D_Gui> row) {
        float max = 0;
        for(D_Gui gui : row)
            if(max < gui.getStyle().getHeight() + gui.getStyle().getMarginHeight())
                max = gui.getStyle().getHeight() + gui.getStyle().getMarginHeight();
        return max;
    }

    private float getRowWidth(ArrayList<D_Gui> row) {
        float w = 0;
        for(D_Gui gui : row)
            w += gui.getStyle().getWidth() + gui.getStyle().getMarginWidth();
        return w;
    }



    @Override
    void setConstraint(D_Gui gui, D_LayoutConstraint constraint) { }

}
