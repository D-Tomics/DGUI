package org.dtomics.DGUI.IO;

import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;

public class GLFWCursor {

    private static GLFWCursor current;

    private long cursor = -1;
    public GLFWCursor(long cursor) {
        this.cursor = cursor;
    }

    public GLFWCursor(GLFWImage image, int xOff, int yOff) {
        this.cursor = glfwCreateCursor(image, xOff, yOff);
    }

    public long get() {
        return cursor;
    }

    public void destroy() {
        glfwDestroyCursor(cursor);
    }

    public void set(Window window) {
        if (this == current) return;
        current = this;
        glfwSetCursor(window.getWindowPointer(), this.get());
    }

}
