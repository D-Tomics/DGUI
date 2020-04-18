package engine.ui.gui.manager;

import engine.ui.IO.GLFWCursor;
import engine.ui.IO.Mouse;
import engine.ui.IO.Window;
import engine.ui.IO.events.*;
import engine.ui.gui.components.*;
import engine.ui.gui.manager.events.*;
import engine.ui.utils.Maths;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

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

    public void update(ArrayList<D_Gui> guis) {
        boolean topFound = false;
        for(D_Gui gui : guis) {
            if(gui instanceof D_Container && ((D_Container) gui).getChildList() != null) {
                update(((D_Container) gui).getChildList());
            }

            if(gui.getQuads() != null)
                for(D_Gui quad : gui.getQuads())
                    topFound = update(quad,topFound);
            topFound = update(gui,topFound);
            updateCursor();
        }
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

    public D_Gui getTopGui()     { return topGui;     }
    public D_Gui getFocusedGui() { return focusedGui; }

    private boolean checkHover(D_Gui gui) {
        float cx = gui.getStyle().getCenterX();
        float cy = gui.getStyle().getCenterY();
        return Maths.checkPointCollision(Mouse.getX(),Mouse.getY(),cx,cy,gui.getStyle().getWidth(),gui.getStyle().getHeight());
    }


    private boolean update(D_Gui gui, boolean topFound) {
        gui.update();
        if(gui.requestedFocus()) {
            setFocusedGui(gui);
            gui.requestFocus(false);
        }

        if(topGui != null ) {
            topFound = topGui != gui;
            if(gui.getLevel() >= topGui.getLevel() && !topGui.isPressed()) topFound = false;
        }

        if(gui.isHoverable() && gui.isVisible() && checkHover(gui)) {
            if(!topFound) {
                topGui = gui;
                topFound = true;

                if(!topGui.isHovered()) {
                    topGui.setHovered(true);
                    topGui.stackEvent(new D_GuiMouseEnterEvent(topGui));
                }
                topGui.stackEvent(new D_GuiMouseHoverEvent(topGui));

                if(Mouse.isMoving()) {
                    topGui.stackEvent(new D_GuiMouseMoveEvent(topGui,Mouse.getX(), Mouse.getY()));
                    if(Mouse.pressed()) {
                        topGui.stackEvent(new D_GuiMouseDragEvent(topGui, Mouse.pressedButton()));
                    }
                }

                if(!topGui.isPressed() && Mouse.pressed()) {
                    topGui.setPressed(true);
                    topGui.stackEvent(new D_GuiMousePressEvent(topGui,Mouse.pressedButton(),Mouse.getMods()));
                    topGui.stackEvent(new D_GuiMouseButtonEvent(topGui,Mouse.pressedButton(), GLFW_PRESS, Mouse.getMods()));
                    if(topGui.isSelectable()) {
                        topGui.setSelected(!topGui.isSelected());
                        topGui.stackEvent(topGui.isSelected() ? new D_GuiSelectedEvent(topGui) : new D_GuiDeSelectedEvent(topGui));
                    }
                } else if(topGui.isPressed() && !Mouse.pressed()) {
                    topGui.setPressed(false);
                    topGui.stackEvent(new D_GuiMouseReleaseEvent(topGui,Mouse.pressedButton(),Mouse.getMods()));
                    topGui.stackEvent(new D_GuiMouseButtonEvent(topGui,Mouse.pressedButton(),GLFW_RELEASE, Mouse.getMods()));
                }
            }
        } else {
            if(gui.isHovered()) {
                gui.setHovered(false);
                gui.stackEvent(new D_GuiMouseExitEvent(gui));
            }

            if(topGui == gui && !gui.isPressed()) {
                topFound = false;
                topGui = null;
            }

            if(gui.isPressed() && !Mouse.pressed()) {
                gui.setPressed(false);
                gui.stackEvent(new D_GuiMouseReleaseEvent(gui, Mouse.pressedButton(), Mouse.getMods()));
                gui.stackEvent(new D_GuiMouseButtonEvent(gui, Mouse.pressedButton(), GLFW_RELEASE, Mouse.getMods()));
            }
        }
        gui.unstackEvents();
        return topFound;
    }

    private void updateCursor() {
        if(
            topGui instanceof D_Button ||
            topGui instanceof D_CheckBox ||
            topGui instanceof D_List<?> ||
            topGui instanceof D_Slider
        ) GLFWCursor.setCursor(GLFWCursor.standardCursors.HAND);
        else if(
                topGui instanceof D_Label ||
                topGui instanceof D_TextComponent
        ) GLFWCursor.setCursor(GLFWCursor.standardCursors.I_BEAM);
        else
            GLFWCursor.setCursor(GLFWCursor.standardCursors.ARROW);
    }

}
