package org.dtomics.DGUI.IO;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;

public class GLFWCursor {

    private static final int[] cursors = new int[]{
            GLFW_ARROW_CURSOR,
            GLFW_HAND_CURSOR,
            GLFW_CROSSHAIR_CURSOR,
            GLFW_IBEAM_CURSOR,
            GLFW_HRESIZE_CURSOR,
            GLFW_VRESIZE_CURSOR
    };
    private static final standardCursors previous = standardCursors.ARROW;
    private static standardCursors current = standardCursors.ARROW;
    private static boolean changed;

    public static void setCursor(Window window, standardCursors cursor) {
        if (cursor == current) return;
        changed = true;
        current = cursor;
        set(window);
    }

    public static standardCursors getCursor() {
        return current;
    }

    private static void set(Window window) {
        glfwSetCursor(window.getWindowPointer(), current.get());
    }

    public static void reset(Window window) {
        if (previous == current) return;
        current = previous;
        set(window);
    }

    public enum standardCursors {
        ARROW,
        HAND,
        CROSS_HAIR,
        I_BEAM,
        H_RESIZE,
        V_RESIZE;
        private long ptr = -1;

        protected long get() {
            if (ptr == -1) ptr = glfwCreateStandardCursor(cursors[this.ordinal()]);
            return ptr;
        }
    }

}
