package engine.ui.gui.manager;

import engine.ui.IO.GLFWCursor;
import engine.ui.IO.Mouse;
import engine.ui.IO.Window;
import engine.ui.IO.events.*;
import engine.ui.gui.components.*;
import engine.ui.gui.manager.events.*;
import engine.ui.gui.manager.events.D_GuiFocusGainEvent;
import engine.ui.gui.manager.events.D_GuiFocusLooseEvent;
import engine.ui.utils.Maths;
import engine.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static engine.ui.IO.GLFWCursor.standardCursors.H_RESIZE;
import static org.lwjgl.glfw.GLFW.*;

public final class D_GuiEventManager {

    private D_Gui focusedGui;
    private D_Gui topGui;

    private GLFWListener keyListener;
    private GLFWListener charListener;
    private GLFWListener scrollListener;

    public void init() {
        this.keyListener = new GLFWListener(GLFWKeyEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                GLFWKeyEvent e = (GLFWKeyEvent)event;
                if(focusedGui != null) {
                    focusedGui.stackEvent(new D_GuiKeyEvent(focusedGui,e.getKey(),e.getCodePoint(),e.getMod(),e.getAction()));
                    if(e.getAction() == GLFW_PRESS)
                        focusedGui.stackEvent(new D_GuiKeyPressEvent(focusedGui,e.getKey(),e.getCodePoint(),e.getMod()));
                    else if(e.getAction() == GLFW_RELEASE)
                        focusedGui.stackEvent(new D_GuiKeyReleaseEvent(focusedGui,e.getKey(),e.getCodePoint(),e.getMod()));
                }
            }
        };

        this.charListener = new GLFWListener(GLFWCharEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                if(focusedGui != null) {
                    focusedGui.stackEvent(new D_GuiCharEvent(focusedGui,((GLFWCharEvent)event).getCodePoint()));
                }
            }
        };

        this.scrollListener = new GLFWListener(GLFWScrollEvent.class) {
            public void invoke(GLFWEvent event) {
                if(focusedGui != null && focusedGui.isScrollable())
                    focusedGui.stackEvent(new D_GuiScrollEvent(focusedGui, ((GLFWScrollEvent)event).getXoffset(), ((GLFWScrollEvent)event).getYoffset()));
            }
        };

        Window.INSTANCE.addListener(this.keyListener);
        Window.INSTANCE.addListener(this.charListener);
        Window.INSTANCE.addListener(this.scrollListener);
    }

    public void destroy() {
        Window.INSTANCE.removeListener(this.keyListener);
        Window.INSTANCE.removeListener(this.charListener);
        Window.INSTANCE.removeListener(this.scrollListener);
    }

    private void updateCursor() {
        if(topGui instanceof D_Button)
            GLFWCursor.setCursor(GLFWCursor.standardCursors.HAND);
        else if(topGui instanceof D_Slider) {
            if(topGui.isPressed()) GLFWCursor.setCursor(H_RESIZE);
            else GLFWCursor.setCursor(GLFWCursor.standardCursors.HAND);
        } else if(topGui instanceof D_Label || topGui instanceof D_TextArea)
            GLFWCursor.setCursor(GLFWCursor.standardCursors.I_BEAM);

        if(topGui == null) GLFWCursor.reset();
    }

    public void setFocusedGui(D_Gui gui) {
        if(gui instanceof D_GuiQuad)
            gui = gui.getParent();

        if(focusedGui == gui) return;
        if(focusedGui != null) {
            focusedGui.focus(false);
            focusedGui.stackEvent(new D_GuiFocusLooseEvent(focusedGui));
        }

        focusedGui = gui;
        if(focusedGui != null) {
            focusedGui.focus(true);
            focusedGui.stackEvent(new D_GuiFocusGainEvent(focusedGui));
        }

    }

    public D_Gui getTopGui() { return topGui; }
    public D_Gui getFocusedGui() { return focusedGui; }

    private boolean topFound;
    private int button;
    public void update(ArrayList<D_Gui> guis) {
        if(guis == null) return;
        updateCursor();
        if(focusedGui != null && focusedGui.requestedLooseFocus()) setFocusedGui(null);

        topFound = false;

        button = 0;
        if(Mouse.pressed()) button = Mouse.pressedButton();

        for(D_Gui gui : guis) {
            if(gui instanceof D_Container)
                if(((D_Container) gui).getChildList() != null)
                    update(((D_Container) gui).getChildList());
            if(gui.getQuads() != null)
                gui.getQuads().forEach(d_geometry -> topFound = updateGui(d_geometry,topFound,button));
            topFound = updateGui(gui,topFound,button);
        }

    }

    private boolean updateGui(D_Gui gui, boolean topFound, int mouseButton) {
        gui.update();
        if(gui.requestedFocus()) {
            setFocusedGui(gui);
            gui.requestFocus(false);
        }
        if(topGui != null) {
            topFound = topGui != gui;
            if(topGui.getLevel() < gui.getLevel() && !topGui.isPressed()) {
                topFound = false;
            }
        }

        if( gui.isHoverable() && checkHover(gui) && gui.isVisible()) {
            if(!topFound) {
                topFound = true;
                topGui = gui;

                List<D_Gui> guiLists = Window.INSTANCE.getGuiList();
                Utils.swap(guiLists,guiLists.indexOf(gui),guiLists.size() - 1);

                gui.stackEvent(new D_GuiMouseHoverEvent(gui));
                if(!gui.isHovered()) {
                    gui.setHovered(true);
                    gui.stackEvent(new D_GuiMouseEnterEvent(gui));
                }


                if(Mouse.isMoving()) {
                    gui.stackEvent(new D_GuiMouseMoveEvent(gui, Mouse.getX(), Mouse.getY()));
                    if(Mouse.pressed())
                        gui.stackEvent(new D_GuiMouseDragEvent(gui, Mouse.pressedButton()));
                }

                if(!gui.isPressed() && Mouse.pressed()) {
                    gui.setPressed(true);
                    gui.stackEvent(new D_GuiMousePressEvent(gui, mouseButton, Mouse.getMods()));
                    gui.stackEvent(new D_GuiMouseButtonEvent(gui, mouseButton, GLFW_PRESS, Mouse.getMods()));
                    if(gui.isSelectable()) {
                        gui.setSelected(!gui.isSelected());
                        gui.stackEvent(gui.isSelected() ? new D_GuiSelectedEvent(gui) : new D_GuiDeSelectedEvent(gui));
                    }
                } else if(gui.isPressed() && !Mouse.pressed()) {
                    gui.setPressed(false);
                    gui.stackEvent(new D_GuiMouseReleaseEvent(gui, mouseButton, Mouse.getMods()));
                    gui.stackEvent(new D_GuiMouseButtonEvent(gui, mouseButton, GLFW_RELEASE, Mouse.getMods()));
                }
            }

        } else {
            if(gui.isHovered()) {
                gui.setHovered(false);
                gui.stackEvent(new D_GuiMouseExitEvent(gui));
            }

            if(topGui == gui && !gui.isPressed())
                topGui = null;

            if(gui.isPressed() && !Mouse.pressed()) {
                gui.setPressed(false);
                gui.stackEvent(new D_GuiMouseReleaseEvent(gui, mouseButton, Mouse.getMods()));
                gui.stackEvent(new D_GuiMouseButtonEvent(gui, mouseButton, GLFW_RELEASE, Mouse.getMods()));
            }

        }

        gui.unstackEvents();

        return topFound;
    }

    private boolean checkHover(D_Gui gui) {
        float cx = gui.getStyle().getCenterX();
        float cy = gui.getStyle().getCenterY();
        return Maths.checkPointCollision(Mouse.getX(),Mouse.getY(),cx,cy,gui.getStyle().getWidth(),gui.getStyle().getHeight());
    }

    private void swap(List<D_Gui> list, int index1, int index2) {
        if(index1 < 0 || index1 >= list.size() || index2 < 0 || index2 >= list.size()) return;
        D_Gui gui1 = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,gui1);
    }

}
