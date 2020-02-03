package engine.ui.gui.manager.layouts;

import engine.ui.gui.components.D_Container;
import engine.ui.gui.manager.LayoutManager;
import engine.ui.gui.manager.Style;

public class Flow extends LayoutManager {

    @Override
    public void update(D_Container parent) {
        if(!parent.isVisible()) return;
        Style style = parent.getStyle();
        var childList = parent.getChildList();
        if(childList == null) return;

        float curX = style.getX() + getPaddingLeft() + getMarginLeft();
        float curY = style.getY() - (getPaddingTop() + getMarginTop());

        for(var gui : childList) {
            if(gui.isVisible()) continue;
            gui.getStyle().setPosition(curX, curY);

            curX += gui.getStyle().getWidth() + getPaddingLeft();
        }
        parent.getStyle().setWidth(curX -parent.getStyle().getX(), false);

        /*if(!parent.isVisible()) return;

        Style style = parent.getStyle();
        var pNode = parent.getChildList();
        if(pNode == null) return;

        float currentX = style.getX() + getPaddingLeft() + getMarginLeft();
        float currentY = style.getY() - (getPaddingTop() + getMarginTop());
        float width = 0;
        float height = 0;

        float prevMaxRowHeight = 0;
        for(var gui : pNode) {
            if(!gui.isVisible()) continue;
            var cStyle = gui.getStyle();

            cStyle.setX(currentX);
            cStyle.setY(currentY);

            if(currentX + cStyle.getWidth() + getPaddingRight() >= style.getX() + style.getWidth()) {
                currentY -= prevMaxRowHeight + getPaddingTop();
                width = currentX + cStyle.getWidth() + getPaddingRight() - style.getX();
                height = style.getY() - (currentY - cStyle.getHeight() - getPaddingBottom());
                currentX = style.getX() + getPaddingRight() + getMarginLeft();
                prevMaxRowHeight = cStyle.getHeight();
            } else {
                currentX += cStyle.getWidth() + getPaddingLeft();
            }

            if(cStyle.getHeight() > prevMaxRowHeight)
                prevMaxRowHeight = cStyle.getHeight();

        }

        style.setWidth(width);
        style.setHeight(height);*/
    }

}
