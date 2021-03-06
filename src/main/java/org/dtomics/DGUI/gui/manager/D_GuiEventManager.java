package org.dtomics.DGUI.gui.manager;

import org.dtomics.DGUI.IO.Cursors;
import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.IO.events.GLFWCharEvent;
import org.dtomics.DGUI.IO.events.GLFWEvent;
import org.dtomics.DGUI.IO.events.GLFWKeyEvent;
import org.dtomics.DGUI.IO.events.GLFWListener;
import org.dtomics.DGUI.IO.events.GLFWScrollEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowFocusLooseEvent;
import org.dtomics.DGUI.gui.components.D_Container;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.components.D_GuiQuad;
import org.dtomics.DGUI.gui.components.D_Icon;
import org.dtomics.DGUI.gui.manager.events.D_GuiCharEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiDeSelectedEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiFocusGainEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiFocusLooseEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyPressEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyReleaseEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseButtonEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseDragEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseEnterEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseExitEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseHoverEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseMoveEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMousePressEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseReleaseEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiScrollEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiSelectedEvent;
import org.dtomics.DGUI.utils.Maths;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * This class is the event generator for the gui system.
 * It keeps track of the top and focused gui.
 * Each Window in an application has their own event managers
 *
 * @author Abdul Kareem
 */
public final class D_GuiEventManager {

    private Window window;
    private D_Gui focusedGui;
    private D_Gui topGui;

    private GLFWListener keyListener;
    private GLFWListener charListener;
    private GLFWListener scrollListener;
    private GLFWListener focusLoseListener;

    public void init(Window window) {
        this.window = window;
        this.keyListener = new GLFWListener(GLFWKeyEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                GLFWKeyEvent e = (GLFWKeyEvent) event;
                if (focusedGui != null) {
                    focusedGui.stackEvent(new D_GuiKeyEvent(focusedGui, e.getKey(), e.getCodePoint(), e.getMod(), e.getAction()));
                    if (e.getAction() == GLFW_PRESS)
                        focusedGui.stackEvent(new D_GuiKeyPressEvent(focusedGui, e.getKey(), e.getCodePoint(), e.getMod()));
                    else if (e.getAction() == GLFW_RELEASE)
                        focusedGui.stackEvent(new D_GuiKeyReleaseEvent(focusedGui, e.getKey(), e.getCodePoint(), e.getMod()));
                }
            }
        };

        this.charListener = new GLFWListener(GLFWCharEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                if (focusedGui != null) {
                    focusedGui.stackEvent(new D_GuiCharEvent(focusedGui, ((GLFWCharEvent) event).getCodePoint()));
                }
            }
        };

        this.scrollListener = new GLFWListener(GLFWScrollEvent.class) {
            public void invoke(GLFWEvent event) {
                if (focusedGui != null && focusedGui.isScrollable())
                    focusedGui.stackEvent(new D_GuiScrollEvent(focusedGui, ((GLFWScrollEvent) event).getXoffset(), ((GLFWScrollEvent) event).getYoffset()));
            }
        };

        this.focusLoseListener = new GLFWListener(GLFWWindowFocusLooseEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                topGui = null;
                updateCursor();
            }
        };

        window.addListener(this.keyListener);
        window.addListener(this.charListener);
        window.addListener(this.scrollListener);
        window.addListener(this.focusLoseListener);
    }

    public void destroy() {
        window.removeListener(this.keyListener);
        window.removeListener(this.charListener);
        window.removeListener(this.scrollListener);
        window.removeListener(this.focusLoseListener);
    }

    public void update(ArrayList<D_Gui> guis) {
        boolean topFound = false;
        if (guis == null) return;
        if (topGui != null) {
            if (!topGui.isVisible())
                topFound = update(topGui, topFound);
        }
        for (int i = 0; i < guis.size(); i++) {
            D_Gui gui = guis.get(i);
            if (gui instanceof D_Container && ((D_Container) gui).getChildList() != null) {
                update(((D_Container) gui).getChildList());
            }

            if (gui.getQuads() != null)
                for (D_Gui quad : gui.getQuads())
                    topFound = update(quad, topFound);
            if (gui.getIcons() != null)
                for (D_Icon icon : gui.getIcons())
                    icon.update();
            topFound = update(gui, topFound);
            if (window.isFocused())
                updateCursor();
        }
    }

    public D_Gui getTopGui() {
        return topGui;
    }

    public D_Gui getFocusedGui() {
        return focusedGui;
    }

    public void setFocusedGui(D_Gui gui) {
        if (gui instanceof D_GuiQuad)
            gui = gui.getParent();

        if (focusedGui == gui) return;
        if (focusedGui != null) {
            focusedGui.focus(false);
            focusedGui.stackEvent(new D_GuiFocusLooseEvent(focusedGui));
        }

        focusedGui = gui;
        if (focusedGui != null) {
            focusedGui.focus(true);
            focusedGui.stackEvent(new D_GuiFocusGainEvent(focusedGui));
        }
    }

    private boolean checkHover(D_Gui gui) {
        float cx = gui.getStyle().getCenterX();
        float cy = gui.getStyle().getCenterY();
        return Maths.checkPointCollision(Mouse.getX(), Mouse.getY(), cx, cy, gui.getStyle().getWidth(), gui.getStyle().getHeight()) && window.isFocused();
    }


    private boolean update(D_Gui gui, boolean topFound) {
        gui.update();
        if (gui.requestedFocus()) {
            setFocusedGui(gui);
            gui.requestFocus(false);
        } else if (gui.requestedLooseFocus()) {
            if (gui == focusedGui)
                setFocusedGui(null);
            gui.requestLooseFocus(false);
        }

        if (topGui != null) {
            topFound = topGui != gui;
            if (gui.getLevel() >= topGui.getLevel() && !topGui.isPressed()) topFound = false;

            if (Mouse.isMoving() && Mouse.pressed()) {
                topGui.stackEvent(new D_GuiMouseDragEvent(topGui, Mouse.pressedButton(), Mouse.getMods(), Mouse.getDx(), Mouse.getDy()));
            }
        }

        if (gui.isHoverable() && gui.isVisible() && checkHover(gui)) {
            if (!topFound) {
                topGui = gui;
                topFound = true;

                if (!topGui.isHovered()) {
                    topGui.setHovered(true);
                    topGui.stackEvent(new D_GuiMouseEnterEvent(topGui));
                }
                topGui.stackEvent(new D_GuiMouseHoverEvent(topGui));

                if (Mouse.isMoving()) {
                    topGui.stackEvent(new D_GuiMouseMoveEvent(topGui, Mouse.getX(), Mouse.getY()));
                }

                if (!topGui.isPressed() && Mouse.pressed()) {
                    topGui.setPressed(true);
                    topGui.stackEvent(new D_GuiMousePressEvent(topGui, Mouse.pressedButton(), Mouse.getMods()));
                    topGui.stackEvent(new D_GuiMouseButtonEvent(topGui, Mouse.pressedButton(), GLFW_PRESS, Mouse.getMods()));
                    if (topGui.isSelectable()) {
                        topGui.setSelected(!topGui.isSelected());
                        topGui.stackEvent(topGui.isSelected() ? new D_GuiSelectedEvent(topGui) : new D_GuiDeSelectedEvent(topGui));
                    }
                } else if (topGui.isPressed() && !Mouse.pressed()) {
                    topGui.setPressed(false);
                    topGui.stackEvent(new D_GuiMouseReleaseEvent(topGui, Mouse.pressedButton(), Mouse.getMods()));
                    topGui.stackEvent(new D_GuiMouseButtonEvent(topGui, Mouse.pressedButton(), GLFW_RELEASE, Mouse.getMods()));
                }
            }
        } else {
            if (gui.isHovered()) {
                gui.setHovered(false);
                gui.stackEvent(new D_GuiMouseExitEvent(gui));
            }

            if (topGui == gui && !gui.isPressed()) {
                topFound = false;
                topGui = null;
            }

            if (gui.isPressed() && !Mouse.pressed()) {
                gui.setPressed(false);
                gui.stackEvent(new D_GuiMouseReleaseEvent(gui, Mouse.pressedButton(), Mouse.getMods()));
                gui.stackEvent(new D_GuiMouseButtonEvent(gui, Mouse.pressedButton(), GLFW_RELEASE, Mouse.getMods()));
            }

            if(focusedGui != null) {
                if(!focusedGui.isPressed() && Mouse.pressed()) {
                    focusedGui.requestLooseFocus(true);
                }
            }
        }
        gui.unstackEvents();
        return topFound;
    }

    private void updateCursor() {
        if(topGui != null) {
            topGui.getStyle().getCursor().set(window);
        } else {
            Cursors.ARROW.get().set(window);
        }
    }

}
