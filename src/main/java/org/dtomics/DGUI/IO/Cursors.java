package org.dtomics.DGUI.IO;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;

public enum  Cursors {
    ARROW(GLFW_ARROW_CURSOR),
    HAND(GLFW_HAND_CURSOR),
    CROSS_HAIR(GLFW_CROSSHAIR_CURSOR),
    I_BEAM(GLFW_IBEAM_CURSOR),
    H_RESIZE(GLFW_HRESIZE_CURSOR),
    V_RESIZE(GLFW_VRESIZE_CURSOR);

    private int cursor;
    private long ptr = -1;
    private GLFWCursor glfwCursor;

    Cursors(int cursor) {
        this.cursor = cursor;
    }

    protected long getPtr() {
        if (ptr == -1)
            ptr = glfwCreateStandardCursor(cursor);
        return ptr;
    }

    public GLFWCursor get() {
        if(glfwCursor == null)
            glfwCursor = new GLFWCursor(getPtr());
        return glfwCursor;
    }
}
