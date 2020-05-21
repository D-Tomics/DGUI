package engine.ui.IO;

import engine.ui.utils.Time;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWaitEvents;

/**
 * This class represents the entry point into the API.
 * This class should be initialised before calling any methods from the api, and should be terminated
 * after the use of api.
 * If the application that uses this api has an update loop then this class's update function should be included in that.
 * If not then an update loop like
 * <pre>
 *     while(!DGUI.exitRequested()) {
 *         DGUI.update(true);
 *     }
 * </pre>
 *
 * This loop should be in the same thread that initialised DGUI.
 *
 */
public class DGUI {

    protected static ArrayList<Window> windowsList;

    static void load(Window window) {
        if(window == null) return;
        if(windowsList == null) windowsList = new ArrayList<>();
        if(windowsList.contains(window)) return;
        windowsList.add(window);
    }

    public static void init() { Window.initGLFW(); }

    public static boolean exitRequested() {
        boolean exitRequested = true;
        for(Window window : windowsList)
            exitRequested &= window.isExitRequested();
        return exitRequested;
    }

    public static void update(boolean poll) {
        for(int i = 0; i < windowsList.size(); i++) {
            Window window = windowsList.get(i);
            if(window.didExit())  {
                windowsList.remove(window);
                continue;
            }
            if(!window.isExitRequested()) {
                window.makeCurrent();
                window.update(false,poll);
            } else {
                window.destroy();
                windowsList.remove(window);
            }
        }
        Time.update();
        if(poll)
            glfwPollEvents();
        else
            glfwWaitEvents();
    }

    public static void terminate() {
        for(Window window : windowsList)
            if(!window.didExit())
                window.destroy();
        Window.terminate();
    }

}
