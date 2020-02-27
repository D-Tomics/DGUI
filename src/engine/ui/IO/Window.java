package engine.ui.IO;

import engine.ui.IO.events.*;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.D_GuiEventManager;
import engine.ui.gui.renderer.D_GuiRenderer;
import engine.ui.gui.renderer.Loader;
import engine.ui.utils.abstractions.Listener;
import engine.ui.gui.text.D_TextMaster;
import engine.ui.utils.Delay;
import engine.ui.utils.Time;
import engine.ui.utils.abstractions.Updatable;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public final class Window {

    private static Delay oneSecDelay = new Delay(1000);

    private int frames;
    private int width;
    private int height;
    private String title;
    private long window_ptr;
    private float aspectRatio;
    private boolean fullScreen;
    private boolean initialized;

    private static int monitorWidth;
    private static int monitorHeight;
    private static float monitorAspectRatio;

    public static Window INSTANCE;

    private ArrayList<D_Gui> guiList;
    private ArrayList<Updatable> updatables;
    private D_GuiEventManager guiEventManager;
    private HashMap<Class<?>, List<GLFWListener>> listenerMap;
    private HashMap<Integer, Integer> windowHints;

    public Window(int width, int height, String title, boolean fullScreen) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.aspectRatio = (float)width/(float)height;
        this.fullScreen = fullScreen;
        this.guiEventManager = new D_GuiEventManager();

        if(INSTANCE == null) INSTANCE = this;
    }

    public void addListener(Class<? extends GLFWEvent> eventClass, Listener listener) {
        addListener(new GLFWListener(eventClass) {
            public void invoke(GLFWEvent event) {
                listener.invoke(event);
            }
        });
    }

    public void addListener(GLFWListener listener) {
        Objects.requireNonNull(listener,"listener should'nt be null");
        if(listenerMap == null) listenerMap = new HashMap<>();
        List<GLFWListener> listeners = listenerMap.computeIfAbsent(listener.getEventClass(), k -> new ArrayList<>());
        if(listeners.contains(listener)) return;
        listeners.add(listener);
    }

    public void removeListener(GLFWListener listener) {
        Objects.requireNonNull(listener);
        if(listenerMap == null) return;
        List<GLFWListener> listeners = listenerMap.get(listener.getEventClass());
        if(listeners == null) return;
        listeners.remove(listener);
    }

    public void add(D_Gui gui) {
        if(gui == null) return;
        if(guiList == null) guiList = new ArrayList<>();
        if(guiList.contains(gui)) return;
        synchronized (this) { guiList.add(gui); }
        gui.getStyle().notifyObservers();
    }

    public void remove(D_Gui gui) {
        if(guiList == null) return;
        if(gui == null) return;
        synchronized (this) { guiList.remove(gui); }
        gui.getStyle().notifyObservers();
    }

    public void addUpdatable(Updatable updatable) {
        if(updatable == null) return;
        if(updatables == null) updatables = new ArrayList<>();
        if(updatables.contains(updatable)) return;
        synchronized (this) { updatables.add(updatable); }
    }

    public void removeUpdatable(Updatable updatable) {
        if(updatable == null) return;
        if(updatables == null) return;
        synchronized (this) { updatables.remove(updatable); }
    }

    public void addHint(int hint, int value) {
        if(windowHints == null) windowHints = new HashMap<>();
        windowHints.put(hint, value);
    }

    public void create() {
        if(initialized) return;
        initGLfw();
        OpenAL.create();
        window_ptr = glfwCreateWindow(width,height,title,fullScreen? glfwGetPrimaryMonitor() : 0,0);
        if(window_ptr == 0) throw new IllegalStateException("window creation failed");

        initializeComponents();

        glfwMakeContextCurrent(window_ptr);
        GL.createCapabilities();
        GL11.glViewport(0,0,width,height);
        glfwShowWindow(window_ptr);
        initialized = true;
    }

    public void reload(int width, int height, boolean fullScreen,String title) {
        this.width = width;
        this.height = height;
        this.fullScreen = fullScreen;
        this.title = title;

        glfwSetWindowSize(window_ptr, width, height);
        //destroy();
        //create();
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window_ptr, width, height);
    }

    private int fps;
    public void update() {
        if(!initialized) return;

        Mouse.update();
        if(updatables != null ) updatables.forEach(Updatable::update);
        fps++;
        if(oneSecDelay.over()) {
            frames = fps;
            fps = 0;
        }
        guiEventManager.update(this.guiList);
        glfwSwapBuffers(window_ptr);
        glfwPollEvents();
        Time.update();
    }

    public void destroy() {
        if(!initialized) return;
        initialized = false;
        OpenAL.close();
        Loader.cleanUp();
        D_GuiRenderer.cleanUp();
        D_TextMaster.cleanUp();
        guiEventManager.destroy();
        glfwDestroyWindow(window_ptr);
        glfwTerminate();
    }

    //getters
    public List<D_Gui> getGuiList() { return guiList; }
    public List<Updatable> getUpdatables() { return updatables; }

    public long getWindowPointer() { return window_ptr; }

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getAspectRatio() { return aspectRatio; }
    public float getVerticalPixelSize() { return 1 / height; }
    public float getHorizontalPixelSize() { return 1 / width; }

    public String getTitle() { return title; }
    public D_GuiEventManager getGuiEventManager() { return guiEventManager; }

    public int getFrames() { return frames; }
    public static int getMonitorWidth() { return monitorWidth; }
    public static int getMonitorHeight() { return monitorHeight; }
    public static float getMonitorAspectRatio() { return monitorAspectRatio; }
    private Window getThis() { return this; }

    //setters
    public void setTitle(String title) {
        if(!initialized) return;
        this.title = title;
        glfwSetWindowTitle(window_ptr, title);
    }


    public boolean isExitRequested() { return initialized && glfwWindowShouldClose(window_ptr); }
    public boolean isResizable() { return glfwGetWindowAttrib(window_ptr,GLFW_RESIZABLE) == GLFW_TRUE; }

    public void setResizable(boolean value) { glfwWindowHint(GLFW_RESIZABLE,value?GLFW_TRUE : GLFW_FALSE); }
    public void enableVSync() { glfwSwapInterval(1); }
    public void disableVSync() { glfwSwapInterval(0); }
    public void exit() { glfwSetWindowShouldClose(window_ptr,true); }

    private void initGLfw() {
        if(initialized) return;
        if(!glfwInit()) throw  new IllegalStateException("could'nt initialize glfw");
        if(windowHints != null) windowHints.keySet().forEach(hint -> glfwWindowHint(hint, windowHints.get(hint)));
    }

    private void initializeComponents() {
        Time.init();
        Mouse.init();
        Keyboard.init();
        guiEventManager.init();
        D_TextMaster.init();
        initialiseMonitorParams();

        setUpCallbacks();
    }

    private void initialiseMonitorParams() {
        GLFWVidMode primaryMonitor = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if(primaryMonitor == null) return;
        Window.monitorWidth = primaryMonitor.width();
        Window.monitorHeight = primaryMonitor.height();
        Window.monitorAspectRatio = (float)monitorWidth / (float)monitorHeight;
    }

    private void invokeEventListeners(GLFWEvent event) {
        if(listenerMap == null) return;
        List<GLFWListener> listeners = listenerMap.get(event.getClass());
        if(listeners == null) return;
        for(GLFWListener listener : listeners)
            listener.invoke(event);
    }

    private void setUpCallbacks() {
        //window callbacks
        glfwSetWindowSizeCallback(window_ptr, new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                aspectRatio = (float)w/(float)h;
                GL11.glViewport(0,0,width,height);
                invokeEventListeners(new GLFWWindowSizeEvent(getThis()));
            }
        });

        glfwSetWindowIconifyCallback(window_ptr, new GLFWWindowIconifyCallback() {
            public void invoke(long window, boolean iconified) {
                if(iconified)
                    invokeEventListeners(new GLFWWindowMinimizeEvent(getThis()));
            }
        });

        glfwSetWindowMaximizeCallback(window_ptr, new GLFWWindowMaximizeCallback() {
            public void invoke(long window, boolean maximized) {
                if(maximized)
                    invokeEventListeners(new GLFWWindowMaximizeEvent(getThis()));
            }
        });

        glfwSetErrorCallback(new GLFWErrorCallback() {
            public void invoke(int error, long description) {
                System.err.println(getDescription(description));
            }
        });

        glfwSetWindowFocusCallback(window_ptr, new GLFWWindowFocusCallback() {
            public void invoke(long window, boolean focused) {
                if(focused) {
                    INSTANCE = getThis();
                    invokeEventListeners(new GLFWWindowFocusGainEvent(getThis()));
                } else
                    invokeEventListeners(new GLFWWindowFocusLooseEvent(getThis()));
            }
        });

        glfwSetWindowCloseCallback(window_ptr, new GLFWWindowCloseCallback() {
            public void invoke(long window) {
                invokeEventListeners(new GLFWWindowCloseEvent(getThis()));
            }
        });

        glfwSetFramebufferSizeCallback(window_ptr, new GLFWFramebufferSizeCallback() {
            public void invoke(long window, int width, int height) {
                invokeEventListeners(new GLFWFrameBufferSizeEvent(getThis(),width,height));
            }
        });


        //mouse callbacks
        glfwSetCursorEnterCallback(window_ptr, new GLFWCursorEnterCallback() {
            public void invoke(long window, boolean entered) {
                if(entered) invokeEventListeners(new GLFWMouseEnterEvent(getThis()));
                else invokeEventListeners(new GLFWMouseExitEvent(getThis()));
            }
        });

        glfwSetCursorPosCallback(Window.INSTANCE.getWindowPointer(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                invokeEventListeners(new GLFWMouseMoveEvent(getThis(),xpos,ypos));
            }
        });

        glfwSetMouseButtonCallback(Window.INSTANCE.getWindowPointer(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                invokeEventListeners(new GLFWMouseButtonEvent(getThis(),button,action,mods));
                if(action == GLFW_PRESS)
                    invokeEventListeners(new GLFWMouseButtonPressEvent(getThis(),button,mods));
                if(action == GLFW_RELEASE)
                    invokeEventListeners(new GLFWMouseButtonReleaseEvent(getThis(),button,mods));
            }
        });

        glfwSetScrollCallback(Window.INSTANCE.getWindowPointer(), new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                invokeEventListeners(new GLFWScrollEvent(getThis(),xoffset,yoffset));
            }
        });


        //keyboard callbacks
        glfwSetKeyCallback(window_ptr, new GLFWKeyCallback() {
            @Override
            public void invoke(long w, int key, int scancode, int action, int mods) {
                if(key == -1 ) return;
                invokeEventListeners(new GLFWKeyEvent(getThis(),key,scancode,action,mods));
                if(action == GLFW_PRESS) invokeEventListeners(new GLFWKeyPressEvent(getThis(),key,scancode,mods));
                if(action == GLFW_RELEASE) invokeEventListeners(new GLFWKeyReleaseEvent(getThis(),key,scancode,mods));
                if(action == GLFW_REPEAT) invokeEventListeners(new GLFWKeyRepeatEvent(getThis(),key,scancode,mods));
            }
        });

        glfwSetCharCallback(window_ptr, new GLFWCharCallback() {
            public void invoke(long window, int codepoint) {
                invokeEventListeners(new GLFWCharEvent(getThis(),codepoint));
            }
        });

    }

}
