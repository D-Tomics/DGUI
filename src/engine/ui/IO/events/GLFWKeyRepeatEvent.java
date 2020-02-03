package engine.ui.IO.events;

import engine.ui.IO.Window;
import org.lwjgl.glfw.GLFW;

public class GLFWKeyRepeatEvent extends GLFWKeyEvent{

    public GLFWKeyRepeatEvent(Window source, int key, int scanCode, int mods) {
        super(source,key,scanCode, GLFW.GLFW_REPEAT,mods);
    }
}
