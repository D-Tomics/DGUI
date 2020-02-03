package engine.ui.IO;

import engine.ui.IO.events.GLFWEvent;
import engine.ui.IO.events.GLFWKeyEvent;
import engine.ui.IO.events.GLFWListener;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard{

    private static final int
            RELEASE_STATE = 0,
            PRESS_STATE = 1,
            REPEAT_STATE = 2;

    private static int[] keyState = new int[GLFW_KEY_LAST];
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] toggleKeys = new boolean[GLFW_KEY_LAST];

    protected static void init() {
        Window.INSTANCE.addListener(new GLFWListener(GLFWKeyEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                GLFWKeyEvent keyEvent = (GLFWKeyEvent)event;
                if(keyEvent.getAction() == GLFW_PRESS)
                    keyState[keyEvent.getKey()] = PRESS_STATE;
                if(keyEvent.getAction() == GLFW_RELEASE)
                    keyState[keyEvent.getKey()] = RELEASE_STATE;
                if(keyEvent.getAction() == GLFW_REPEAT)
                    keyState[keyEvent.getKey()] = REPEAT_STATE;
            }
        });
    }

    public static boolean isKeyRepeating(int key) {
        return isKeyDown(key) || keyState[key] == REPEAT_STATE;
    }

    public static boolean isKeyPressed(int key) {
        return keyState[key] == PRESS_STATE || keyState[key] == REPEAT_STATE;
    }

    public static boolean isKeyDown(int key) {
        if(isKeyPressed(key))
        {
            if(!keys[key])
            {
                keys[key] = true;
                return true;
            }
        }
        else{
            if(keys[key])
                keys[key] = false;
        }
        return false;
    }

    public static boolean isKeyUp(int key) {
        if(!isKeyPressed(key))
        {
            if(keys[key])
            {
                keys[key] = false;
                return true;
            }
        }
        else
        {
            keys[key] = true;
        }
        return false;
    }

    public static boolean toggleKey(int key) {
        if(isKeyDown(key)) {
            toggleKeys[key] = !toggleKeys[key];
            System.out.println("toggle key "+(char)key+" : "+toggleKeys[key]);
        }
        return toggleKeys[key];
    }

    public static void resetKey(int key) {
        if(toggleKeys[key])
            toggleKeys[key] = false;
    }

}
