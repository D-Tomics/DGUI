package engine.ui.IO;

import engine.ui.utils.Time;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

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

    public static void onUpdate() {
        for(int i = 0; i < windowsList.size(); i++) {
            Window window = windowsList.get(i);
            if(window.didExit())  {
                windowsList.remove(window);
                continue;
            }
            if(!window.isExitRequested()) {
                window.makeCurrent();
                window.update();
            } else {
                window.destroy();
                windowsList.remove(window);
            }
        }
        Time.update();
        glfwPollEvents();
    }

    public static void terminate() {
        for(Window window : windowsList)
            if(!window.didExit())
                window.destroy();
        Window.terminate();
    }

}
