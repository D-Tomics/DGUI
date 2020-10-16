package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.IO.events.GLFWKeyPressEvent;
import org.dtomics.DGUI.IO.events.GLFWListener;
import org.dtomics.DGUI.IO.events.GLFWWindowCloseEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowFocusGainEvent;
import org.dtomics.DGUI.gui.layouts.Alignment;
import org.dtomics.DGUI.gui.layouts.GridLayout;
import org.dtomics.DGUI.gui.manager.constraints.gui_constraints.RelativePosition;
import org.dtomics.DGUI.gui.manager.constraints.layout_constraints.GridConstraint;
import org.dtomics.DGUI.gui.manager.events.D_GuiEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiEventListener;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyPressEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMousePressEvent;

import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;

/**
 * This component represents a standard dialog box that prompts users for a value or informs them of something in a new window
 *
 * @author Abdul Kareem
 */
public abstract class D_OptionPane extends D_Container {

    public static final int OK = 1, CANCEL = 0;

    private D_OptionPane() {
    }

    public static void showMessageDialog(Window window, String message) {
        Window optionPane = new Window(1, 1, "message", false);
        optionPane.setResizable(false);
        optionPane.create(window, false);

        GLFWListener focusGain = window.addListener(GLFWWindowFocusGainEvent.class, e -> optionPane.focus());

        D_Panel panel = new D_Panel(new GridLayout(2, 1));
        panel.addConstraint(new RelativePosition(optionPane, 0, 0));

        D_Label label = new D_Label(message);
        D_Button ok = new D_Button("ok");
        panel.add(label, ok);
        panel.pack();

        ok.addEventListener(D_GuiMousePressEvent.class, e -> {
            optionPane.destroy();
            window.removeListener(focusGain);
            window.focus();
        });

        optionPane.add(panel);
        optionPane.setSize((int) panel.getStyle().getWidth(), (int) panel.getStyle().getHeight());
        int[] pos = window.getPos();
        optionPane.setPosition(
                pos[0] + window.getWidth() / 2.0f - optionPane.getWidth() / 2f,
                pos[1] + window.getHeight() / 2f - optionPane.getHeight() / 2f);

        optionPane.addListener(GLFWWindowCloseEvent.class, e -> {
            window.removeListener(focusGain);
            window.focus();
        });

        optionPane.addListener(GLFWKeyPressEvent.class, e -> {
            D_GuiKeyPressEvent event = (D_GuiKeyPressEvent) e;
            if (event.getKey() == GLFW_KEY_ENTER || event.getKey() == GLFW_KEY_KP_ENTER || event.getKey() == GLFW_KEY_ESCAPE) {
                optionPane.destroy();
                window.removeListener(focusGain);
                window.focus();
            }

        });
        optionPane.show();
    }

    public static void showConfirmDialog(Window window, String message, Consumer<Integer> onAction) {
        Window confirm = new Window(1, 1, "confirm", false);
        confirm.setResizable(false);
        confirm.create(window, false);

        GLFWListener focusGain = window.addListener(GLFWWindowFocusGainEvent.class, e -> confirm.focus());

        D_Panel panel = new D_Panel(new GridLayout(2, 2));
        D_Button ok = new D_Button("ok");
        D_Button cancel = new D_Button("cancel");
        panel.add(new D_Label(message), new GridConstraint(0, 0, 1, 0, Alignment.CENTER));
        panel.add(ok, cancel);
        panel.pack();
        panel.addConstraint(new RelativePosition(confirm, 0, 0));

        confirm.add(panel);

        D_GuiEventListener listener = new D_GuiEventListener(D_GuiMousePressEvent.class) {
            @Override
            public void invokeEvent(D_GuiEvent event) {
                if (onAction != null) {
                    if (event.getSource().equals(ok)) onAction.accept(OK);
                    else onAction.accept(CANCEL);
                }
                confirm.destroy();
                window.removeListener(focusGain);
                window.focus();
            }
        };
        ok.addEventListener(listener);
        cancel.addEventListener(listener);

        confirm.addListener(GLFWWindowCloseEvent.class, e -> {
            window.removeListener(focusGain);
            window.focus();
        });

        confirm.setSize((int) panel.getStyle().getWidth(), (int) panel.getStyle().getHeight());
        int[] pos = window.getPos();
        confirm.setPosition(
                pos[0] + window.getWidth() / 2f - confirm.getWidth() / 2f,
                pos[1] + window.getHeight() / 2f - confirm.getHeight() / 2f
        );

        confirm.addListener(GLFWKeyPressEvent.class, e -> {
            GLFWKeyPressEvent event = (GLFWKeyPressEvent) e;
            if (event.getKey() == GLFW_KEY_ENTER || event.getKey() == GLFW_KEY_KP_ENTER) {
                if (onAction != null) onAction.accept(OK);
                confirm.destroy();
                window.removeListener(focusGain);
                window.focus();
            } else if (event.getKey() == GLFW_KEY_ESCAPE) {
                if (onAction != null) onAction.accept(CANCEL);
                confirm.destroy();
                window.removeListener(focusGain);
                window.focus();
            }
        });
        confirm.show();
    }

    public static void showInputDialog(Window window, String message, Consumer<String> onAction) {
        Window inputWindow = new Window(1, 1, "input", false);
        inputWindow.setResizable(false);
        inputWindow.create(window, false);

        GLFWListener focusGain = window.addListener(GLFWWindowFocusGainEvent.class, e -> inputWindow.focus());

        D_Panel panel = new D_Panel(new GridLayout(3, 2));
        panel.addConstraint(new RelativePosition(inputWindow, 0, 0));
        D_Label label = new D_Label(message);
        D_TextField input = new D_TextField("", 50);
        D_Button ok = new D_Button("ok");
        D_Button cancel = new D_Button("cancel");

        label.getStyle().setMarginBottom(0);
        input.getStyle().setMarginTop(0);

        panel.add(label);
        panel.add(input, new GridConstraint(0, 1, 1, 0, Alignment.CENTER));
        panel.add(ok, cancel);
        panel.pack();

        inputWindow.add(panel);
        D_GuiEventListener listener = new D_GuiEventListener(D_GuiMousePressEvent.class) {
            @Override
            public void invokeEvent(D_GuiEvent event) {
                if (onAction != null) {
                    if (event.getSource().equals(ok)) onAction.accept(input.getText());
                    else onAction.accept(null);
                }
                inputWindow.destroy();
                window.removeListener(focusGain);
                window.focus();
            }
        };
        ok.addEventListener(listener);
        cancel.addEventListener(listener);
        inputWindow.addListener(GLFWWindowCloseEvent.class, e -> {
            window.removeListener(focusGain);
            window.focus();
        });

        int[] pos = window.getPos();
        inputWindow.setSize((int) panel.getStyle().getWidth(), (int) panel.getStyle().getHeight());
        inputWindow.setPosition(pos[0] + window.getWidth() / 2f - inputWindow.getWidth() / 2f, pos[1] + window.getHeight() / 2f - inputWindow.getHeight() / 2f);

        inputWindow.addListener(GLFWKeyPressEvent.class, e -> {
            GLFWKeyPressEvent event = (GLFWKeyPressEvent) e;
            if (event.getKey() == GLFW_KEY_ENTER || event.getKey() == GLFW_KEY_KP_ENTER) {
                if (onAction != null) onAction.accept(input.getText());
                inputWindow.destroy();
                window.removeListener(focusGain);
                window.focus();
            } else if (event.getKey() == GLFW_KEY_ESCAPE) {
                if (onAction != null) onAction.accept(null);
                inputWindow.destroy();
                window.removeListener(focusGain);
                window.focus();
            }
        });
        inputWindow.show();
    }

    public void showCustomDialog(Window window, D_Panel message) {

    }

}
