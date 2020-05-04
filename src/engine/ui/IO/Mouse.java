package engine.ui.IO;

import engine.ui.IO.events.*;
import engine.ui.utils.Delay;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public final class Mouse {

    public static final int
            MOUSE_NULL = -1,
            MOUSE_BUTTON_LEFT = GLFW_MOUSE_BUTTON_LEFT,
            MOUSE_BUTTON_RIGHT = GLFW_MOUSE_BUTTON_RIGHT,
            MOUSE_BUTTON_MIDDLE = GLFW_MOUSE_BUTTON_MIDDLE,
            MOUSE_BUTTON_1 = GLFW_MOUSE_BUTTON_1,
            MOUSE_BUTTON_2 = GLFW_MOUSE_BUTTON_2,
            MOUSE_BUTTON_3 = GLFW_MOUSE_BUTTON_3,
            MOUSE_BUTTON_4 = GLFW_MOUSE_BUTTON_4,
            MOUSE_BUTTON_5 = GLFW_MOUSE_BUTTON_5,
            MOUSE_BUTTON_6 = GLFW_MOUSE_BUTTON_6,
            MOUSE_BUTTON_7 = GLFW_MOUSE_BUTTON_7,
            MOUSE_BUTTON_8 = GLFW_MOUSE_BUTTON_8;

    private static Vector2f position = new Vector2f(0);
    private static Delay repeatDelay = new Delay(200);

    private static int yScroll;
    private static int xScroll;
    private static int mods;
    private static int pressedButton = MOUSE_NULL;

    private static int[] pressedButtons = new int[GLFW_MOUSE_BUTTON_LAST];
    private static int[] pressCount = new int[GLFW_MOUSE_BUTTON_LAST];

    private static boolean[] down = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static boolean[] up = new boolean[GLFW_MOUSE_BUTTON_LAST];

    private static boolean isMoving = false;
    private static boolean scrollingY = false;
    private static boolean scrollingX = false;

    protected static void init(Window window) {

        window.addListener(new GLFWListener(GLFWMouseMoveEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                position.x = (float) ((GLFWMouseMoveEvent)event).getXpos() - event.getSource().getWidth()/2.0f;
                position.y = -(float) ((GLFWMouseMoveEvent)event).getYpos() + event.getSource().getHeight()/2.0f;
                isMoving = true;
            }
        });

        window.addListener(new GLFWListener(GLFWMouseButtonEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                GLFWMouseButtonEvent e = (GLFWMouseButtonEvent)event;
                mods = e.getMods();
                if(e.getAction() == GLFW_PRESS) {
                    pressedButton = e.getButton();
                    pressedButtons[e.getButton()] = 1;
                    pressCount[e.getButton()] += 1;
                    repeatDelay.reset();
                }

                if(e.getAction() == GLFW_RELEASE) {
                    pressedButton = MOUSE_NULL;
                    pressedButtons[e.getButton()] = 0;
                }
            }
        });

        window.addListener(new GLFWListener(GLFWScrollEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                GLFWScrollEvent e = (GLFWScrollEvent)event;
                yScroll = (int)e.getYoffset();
                if(e.getYoffset() != 0)
                    scrollingY = true;
                xScroll = (int)e.getXoffset();
                if(e.getXoffset() != 0)
                    scrollingX = true;
            }
        });

        window.addListener(new GLFWListener(GLFWWindowCloseEvent.class) {
            @Override
            public void invoke(GLFWEvent event) {
                pressedButton = MOUSE_NULL;
                Arrays.fill(pressedButtons, 0);
                Arrays.fill(pressCount, 0);
            }
        });

    }

    protected static void update() {
        if(repeatDelay.over()) Arrays.fill(pressCount,0);
    }


    private static long cursor;
    public static void setCursor(long cursor, Window window) {
        if(Mouse.cursor != cursor) {
            Mouse.cursor = cursor;
            glfwSetCursor(window.getWindowPointer(),cursor);
        }
    }

    public static void hideCursor(boolean value, Window window) {
        if(value)
        {
            glfwSetInputMode(window.getWindowPointer(),GLFW_CURSOR,GLFW_CURSOR_HIDDEN);
            glfwSetInputMode(window.getWindowPointer(),GLFW_CURSOR,GLFW_CURSOR_DISABLED);
            return;
        }
        glfwSetInputMode(window.getWindowPointer(),GLFW_CURSOR,GLFW_CURSOR_NORMAL);
    }
    public static void disableCursor(Window window) { glfwSetInputMode(window.getWindowPointer(),GLFW_CURSOR,GLFW_CURSOR_DISABLED); }
    public static void enableCursor(Window window) { glfwSetInputMode(window.getWindowPointer(),GLFW_CURSOR,GLFW_CURSOR_NORMAL); }

    public static int getMods() { return mods; }

    public static int pressedButton() { return pressedButton; }
    public static int pressedCount(int button) { return pressCount[button]; }

    public static boolean pressed() { return pressedButton != MOUSE_NULL; }
    public static boolean pressed(int button) { return button != MOUSE_NULL && pressedButtons[button] != 0; }

    public static boolean isButtonDown() { return isButtonDown(pressedButton); }
    public static boolean isButtonDown(int button) {
        if(button == MOUSE_NULL) return false;
        if(pressed(button)) {
            if(!down[button]) {
                down[button] = true;
                return true;
            }
            return false;
        }
        down[button] = false;
        return false;
    }

    public static boolean isButtonUp() { return isButtonUp(pressedButton); }
    public static boolean isButtonUp(int button) {
        if(button == MOUSE_NULL) return false;
        if(!pressed(button)) {
            if(!up[button]) {
                up[button] = true;
                return true;
            }
            return false;
        }
        up[button] = false;
        return false;
    }

    public static boolean isScrollingY() {
        if(scrollingY) {
            scrollingY = false;
            return true;
        }
        return false;

    }
    public static boolean isScrollingX() {
        if(scrollingX) {
            scrollingX = false;
            return true;
        }
        return false;
    }

    public static boolean isMoving() {
        if(isMoving) {
            isMoving = false;
            return true;
        }
        return false;
    }

    public static float getX() { return position.x; }
    public static float getY() { return position.y; }

    public static float nGetX(Window window) { return position.x + window.getWidth()/2.0f; }
    public static float nGetY(Window window) { return -position.y + window.getHeight()/2.0f; }

    public static int getScrollY() { return yScroll; }
    public static int getScrollX() { return xScroll; }

    private static Vector3f ray = new Vector3f();
    private static Matrix4f tempMatrix = new Matrix4f();
    private static Vector4f tempVector = new Vector4f();

    /**converts 2D mouse pixel coordinates to 3d vectors in a space defined by projection matrix
        @param projection - matrix that defines a frustum
        @param  view - matrix tha defines a camera
        @return returns the ray from mouse position relative to a camera position
     */
    public static Vector3f getRay(Matrix4f projection,Matrix4f view, Window window) {
        tempVector.set(2 * getX() / (float) window.getWidth(), 2 * getY() / (float) window.getHeight(), -1.0f, 1.0f);//clipSpace : z is -1 to indicate forward dir
        tempVector.mul(projection.invert(tempMatrix)).w = 0; // eyeSpace Vector
        tempVector.mul(view.invert(tempMatrix));   //worldSpace Vector
        ray.set(tempVector.x,tempVector.y,tempVector.z);
        ray.normalize();
        return ray;
    }

}
