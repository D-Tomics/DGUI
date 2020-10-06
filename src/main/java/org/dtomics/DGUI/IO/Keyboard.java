package org.dtomics.DGUI.IO;

import org.dtomics.DGUI.IO.events.GLFWEvent;
import org.dtomics.DGUI.IO.events.GLFWKeyEvent;
import org.dtomics.DGUI.IO.events.GLFWListener;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

public class Keyboard {

    private static final int
            RELEASE_STATE = 0,
            PRESS_STATE = 1,
            REPEAT_STATE = 2;

    private static final int[] keyState = new int[GLFW_KEY_LAST];
    private static final boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static final boolean[] toggleKeys = new boolean[GLFW_KEY_LAST];

    protected static void init(Window window) {
        window.addListener(new GLFWListener(GLFWKeyEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                GLFWKeyEvent keyEvent = (GLFWKeyEvent) event;
                if (keyEvent.getAction() == GLFW_PRESS)
                    keyState[keyEvent.getKey()] = PRESS_STATE;
                if (keyEvent.getAction() == GLFW_RELEASE)
                    keyState[keyEvent.getKey()] = RELEASE_STATE;
                if (keyEvent.getAction() == GLFW_REPEAT)
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
        if (isKeyPressed(key)) {
            if (!keys[key]) {
                keys[key] = true;
                return true;
            }
        } else {
            if (keys[key])
                keys[key] = false;
        }
        return false;
    }

    public static boolean isKeyUp(int key) {
        if (!isKeyPressed(key)) {
            if (keys[key]) {
                keys[key] = false;
                return true;
            }
        } else {
            keys[key] = true;
        }
        return false;
    }

    public static boolean toggleKey(int key) {
        if (isKeyDown(key)) {
            toggleKeys[key] = !toggleKeys[key];
            System.out.println("toggle key " + (char) key + " : " + toggleKeys[key]);
        }
        return toggleKeys[key];
    }

    public static void resetKey(int key) {
        if (toggleKeys[key])
            toggleKeys[key] = false;
    }

}
