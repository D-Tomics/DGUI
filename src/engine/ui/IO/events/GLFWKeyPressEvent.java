package engine.ui.IO.events;

import engine.ui.IO.Window;
import org.lwjgl.glfw.GLFW;

public class GLFWKeyPressEvent extends GLFWKeyEvent{
    public GLFWKeyPressEvent(Window source, int key, int codePoint, int modes) {
        super(source,key,codePoint, GLFW.GLFW_PRESS,modes);
    }
}
