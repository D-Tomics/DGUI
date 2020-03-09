package engine.ui.gui.components;

import engine.ui.gui.layouts.Flow;
import engine.ui.gui.layouts.Layout;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;
import engine.ui.utils.observers.Observable;

import java.util.ArrayList;

public abstract class D_Container extends D_Gui{

    private boolean minimizable;
    private boolean resizable;
    private boolean resizing;
    private boolean minimized;

    private ArrayList<D_Gui> childList;
    private Layout layout;
    D_Container() {
        layout = new Flow();
    }

    @Override
    protected void onStateChange(Observable o) {
        if(layout != null)
            layout.update(this);
    }

    @Override
    protected void setLevel(int level) {
        super.setLevel(level);
        if(childList == null) return;
        for(D_Gui child : childList)
            child.setLevel(level + 1);
    }

    public void pack() {
        this.style.setSize(layout.getMaxWidth(), layout.getMaxHeight());
        this.style.notifyObservers();
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(childList == null) return;
        for(D_Gui child : childList)
            child.setVisible(visible);
    }

    public boolean isResizing() { return resizing; }
    public void setResizing(boolean resizing) { this.resizing = resizing; }
    public boolean isResizable() { return resizable; }
    public void setResizable(boolean resizable) { this.resizable = resizable; }
    public boolean isMinimizable() { return minimizable; }
    public void setMinimizable(boolean minimizable) { this.minimizable = minimizable; }
    public boolean isMinimized() { return minimized; }
    public void setMinimized(boolean minimized) { this.minimized = minimized; }

    public void add(D_Gui gui, D_LayoutConstraint constraint) {
        if(childList == null) childList = new ArrayList<>();
        if(gui == null) return;
        if(gui == this) return;

        layout.addLayoutGui(gui,constraint);
        childList.add(gui);

        gui.setParent(this);
        gui.setLevel(this.getLevel() + 1);
        gui.setVisible(true);

        this.style.notifyObservers();
        if(getParent() != null)
            getParent().getStyle().notifyObservers();

    }

    public void add(D_Gui gui) {
        add(gui,null);
    }

    public void add(D_Gui...childArray) {
        for(D_Gui child : childArray)
            add(child);
    }

    public ArrayList<D_Gui> getChildList() { return childList;}

    public void setLayout(Layout layout) {
        this.layout = layout;
        this.style.notifyObservers();
    }

}
