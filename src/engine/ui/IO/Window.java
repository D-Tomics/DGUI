package engine.ui.IO;

import engine.ui.IO.events.*;
import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.D_GuiEventManager;
import engine.ui.gui.renderer.Loader;
import engine.ui.gui.renderer.MasterRenderer;
import engine.ui.gui.text.D_TextMaster;
import engine.ui.utils.Delay;
import engine.ui.utils.Time;
import engine.ui.utils.abstractions.Listener;
import engine.ui.utils.abstractions.Task;
import engine.ui.utils.abstractions.Updatable;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public final class Window {

    private static Delay oneSecDelay = new Delay(1000);

    private static boolean init;
    public static void initGLFW() {
        if(init) return;
        init = glfwInit();
        if(!init) throw  new IllegalStateException("could'nt initialize glfw");
        OpenAL.create();
        Time.init();
    }

    public static void terminate() {
        glfwTerminate();
        OpenAL.close();
    }

    private int fps;
    private int width;
    private int height;
    private int frames;
    private long window_ptr;
    private float aspectRatio;
    private boolean vSync;
    private boolean fullScreen;
    private boolean focused;
    private boolean exited;

    private String title;

    private static int monitorWidth;
    private static int monitorHeight;
    private static float monitorAspectRatio;

    public static Window INSTANCE;
    private static Window current;

    private Loader loader;
    private MasterRenderer renderer;
    private D_GuiEventManager guiEventManager;

    private ArrayList<Task> tasks;
    private ArrayList<D_Gui> guiList;
    private ArrayList<Updatable> updatables;

    private HashMap<Class<?>, List<GLFWListener>> listenerMap;

    public Window(int width, int height, String title, boolean fullScreen) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.aspectRatio = (float)width/(float)height;
        this.fullScreen = fullScreen;
        this.guiEventManager = new D_GuiEventManager();
        this.loader = new Loader(this);
        this.renderer = new MasterRenderer(this);
        initGLFW();
        DGUI.load(this);
        if(INSTANCE == null) INSTANCE = this;
    }

    public void makeCurrent() {
        if(current == this) return;
        current = this;
        glfwMakeContextCurrent(window_ptr);
    }

    public void create() { create(null, true); }

    public void create(Window share, boolean createContext) {
        window_ptr = glfwCreateWindow(width,height,title, fullScreen ? glfwGetPrimaryMonitor() : 0, share != null ? share.getWindowPointer() : 0);
        if(window_ptr == 0) throw new IllegalStateException("window creation failed");
        glfwMakeContextCurrent(window_ptr);
        if(createContext) GL.createCapabilities();
        glViewport(0,0,width, height);

        initializeComponents();

        glfwShowWindow(window_ptr);
        focused = true;
        INSTANCE = this;
    }

    public void update() {
        renderer.render();
        Mouse.update();
        guiEventManager.update(this.guiList);
        if(updatables != null ) updatables.forEach(Updatable::update);
        if(tasks != null) {
            tasks.forEach(Task::apply);
            tasks.clear();
        }

        fps++;
        if(oneSecDelay.over()) {
            frames = fps;
            fps = 0;
        }
        glfwSwapBuffers(window_ptr);
        glfwPollEvents();
        Time.update();
    }

    public void destroy() {
        invokeEventListeners(new GLFWWindowCloseEvent(this));
        renderer.cleanUp();
        guiEventManager.destroy();
        glfwDestroyWindow(window_ptr);
        exited = true;
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

    public void addTask(Task task) {
        if(task == null) return;
        if(tasks == null) tasks = new ArrayList<>();
        if(tasks.contains(task)) return;
        synchronized (this) { tasks.add(task); }
    }

    public void removeTask(Task task) {
        if(task == null) return;
        if(tasks == null) return;
        synchronized (this) { tasks.remove(task); }
    }

    public void hint(int hint, int value) { glfwWindowHint(hint,value); }

    //getters
    public List<D_Gui>     getGuiList()     { return guiList; }
    public List<Updatable> getUpdatables()  { return updatables; }


    public int getFrames() { return frames; }
    public long getWindowPointer() { return window_ptr; }

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getAspectRatio() { return aspectRatio; }
    public float getVerticalPixelSize() { return 1 / height; }
    public float getHorizontalPixelSize() { return 1 / width; }

    public String getTitle() { return title; }
    public Loader getLoader() { return loader; }
    public D_GuiEventManager getGuiEventManager() { return guiEventManager; }

    public boolean didExit() { return exited; }
    public boolean isFocused() { return focused; }
    public boolean isExitRequested() { return glfwWindowShouldClose(window_ptr); }
    public boolean isResizable() { return glfwGetWindowAttrib(window_ptr,GLFW_RESIZABLE) == GLFW_TRUE; }

    public int getTopLayer() {
        if(guiList == null) return 0;
        int top = 1;
        for(D_Gui gui : guiList) {
            int level = gui instanceof D_Container ? ((D_Container) gui).getTopLevel() : 1;
            if(level > top)
                top = level;
        }
        return top;
    }

    public static int getMonitorWidth() { return monitorWidth; }
    public static int getMonitorHeight() { return monitorHeight; }
    public static float getMonitorAspectRatio() { return monitorAspectRatio; }

    private Window getThis() { return this; }

    //setters
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window_ptr, width, height);
    }

    public void setFullScreen(boolean fullScreen) {
        if(this.fullScreen == fullScreen) return;
        this.fullScreen = fullScreen;
        if(fullScreen) {
            int[] x = new int[1];
            int[] y = new int[1];
            glfwGetWindowPos(window_ptr,x,y);
            glfwSetWindowMonitor(window_ptr, glfwGetPrimaryMonitor(), x[0], y[0], width, height, vSync ? 1 : 0);
        } else {
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if(vidMode == null) return;
            glfwSetWindowMonitor(window_ptr, 0, vidMode.width() - width / 2, vidMode.height() - height / 2, width, height, vSync ? 1 : 0);
        }
    }

    public void setTitle(Object title) {
        this.title = title.toString();
        glfwSetWindowTitle(window_ptr, title.toString());
    }

    public void setResizable(boolean value) { hint(GLFW_RESIZABLE,value?GLFW_TRUE : GLFW_FALSE); }
    public void enableVSync() { vSync = true; glfwSwapInterval(1); }
    public void disableVSync() { vSync = false; glfwSwapInterval(0); }
    public void exit() { glfwSetWindowShouldClose(window_ptr,true); }

    private void initializeComponents() {
        Mouse.init(this);
        Keyboard.init(this);

        renderer.init();
        guiEventManager.init(this);

        D_TextMaster.init(this);
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
                getThis().makeCurrent();
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
                throw new IllegalStateException(getDescription(description));
            }
        });

        glfwSetWindowFocusCallback(window_ptr, new GLFWWindowFocusCallback() {
            public void invoke(long window, boolean focused) {
                if(focused) {
                    getThis().focused = true;
                    INSTANCE = getThis();
                    invokeEventListeners(new GLFWWindowFocusGainEvent(getThis()));
                } else {
                    getThis().focused = false;
                    invokeEventListeners(new GLFWWindowFocusLooseEvent(getThis()));
                }
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

        glfwSetCursorPosCallback(window_ptr, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                if(focused) invokeEventListeners(new GLFWMouseMoveEvent(getThis(),xpos,ypos));
            }
        });

        glfwSetMouseButtonCallback(window_ptr, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                invokeEventListeners(new GLFWMouseButtonEvent(getThis(),button,action,mods));
                if(action == GLFW_PRESS)
                    invokeEventListeners(new GLFWMouseButtonPressEvent(getThis(),button,mods));
                if(action == GLFW_RELEASE)
                    invokeEventListeners(new GLFWMouseButtonReleaseEvent(getThis(),button,mods));
            }
        });

        glfwSetScrollCallback(window_ptr, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                invokeEventListeners(new GLFWScrollEvent(getThis(),xoffset,yoffset));
            }
        });


        //keyboard callbacks
        glfwSetKeyCallback(window_ptr, new GLFWKeyCallback() {
            @Override
            public void invoke(long w, int key, int scancode, int action, int mods) {
                if(!focused) return;
                if(key == -1 ) return;
                invokeEventListeners(new GLFWKeyEvent(getThis(),key,scancode,action,mods));
                if(action == GLFW_PRESS) invokeEventListeners(new GLFWKeyPressEvent(getThis(),key,scancode,mods));
                if(action == GLFW_RELEASE) invokeEventListeners(new GLFWKeyReleaseEvent(getThis(),key,scancode,mods));
                if(action == GLFW_REPEAT) invokeEventListeners(new GLFWKeyRepeatEvent(getThis(),key,scancode,mods));
            }
        });

        glfwSetCharCallback(window_ptr, new GLFWCharCallback() {
            public void invoke(long window, int codepoint) {
                if(focused) invokeEventListeners(new GLFWCharEvent(getThis(),codepoint));
            }
        });

    }

}
