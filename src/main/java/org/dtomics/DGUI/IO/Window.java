package org.dtomics.DGUI.IO;

import org.dtomics.DGUI.IO.events.GLFWCharEvent;
import org.dtomics.DGUI.IO.events.GLFWEvent;
import org.dtomics.DGUI.IO.events.GLFWFrameBufferSizeEvent;
import org.dtomics.DGUI.IO.events.GLFWKeyEvent;
import org.dtomics.DGUI.IO.events.GLFWKeyPressEvent;
import org.dtomics.DGUI.IO.events.GLFWKeyReleaseEvent;
import org.dtomics.DGUI.IO.events.GLFWKeyRepeatEvent;
import org.dtomics.DGUI.IO.events.GLFWListener;
import org.dtomics.DGUI.IO.events.GLFWMouseButtonEvent;
import org.dtomics.DGUI.IO.events.GLFWMouseButtonPressEvent;
import org.dtomics.DGUI.IO.events.GLFWMouseButtonReleaseEvent;
import org.dtomics.DGUI.IO.events.GLFWMouseEnterEvent;
import org.dtomics.DGUI.IO.events.GLFWMouseExitEvent;
import org.dtomics.DGUI.IO.events.GLFWMouseMoveEvent;
import org.dtomics.DGUI.IO.events.GLFWScrollEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowCloseEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowFocusGainEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowFocusLooseEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowMaximizeEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowMinimizeEvent;
import org.dtomics.DGUI.IO.events.GLFWWindowSizeEvent;
import org.dtomics.DGUI.IO.sound.SoundManager;
import org.dtomics.DGUI.gui.components.D_Container;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.D_GuiEventManager;
import org.dtomics.DGUI.gui.renderer.Loader;
import org.dtomics.DGUI.gui.renderer.MasterRenderer;
import org.dtomics.DGUI.gui.text.D_TextMaster;
import org.dtomics.DGUI.utils.Delay;
import org.dtomics.DGUI.utils.Time;
import org.dtomics.DGUI.utils.abstractions.Listener;
import org.dtomics.DGUI.utils.abstractions.Task;
import org.dtomics.DGUI.utils.abstractions.Updatable;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowMaximizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwFocusWindow;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPos;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMaximizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWaitEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glViewport;

public final class Window {

    private static int monitorWidth;
    private static int monitorHeight;
    private static float monitorAspectRatio;

    private volatile static Window INSTANCE;
    private volatile static Window current;
    private static boolean init;
    private final Delay oneSecDelay = new Delay(1000);
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
    private final Loader loader;
    private final MasterRenderer renderer;
    private final D_GuiEventManager guiEventManager;
    private ArrayList<Task> tasks;
    private ArrayList<D_Gui> guiList;
    private ArrayList<Updatable> updatables;
    private HashMap<Class<?>, List<GLFWListener>> listenerMap;

    public Window() {
        this(1, 1, "", false);
    }

    public Window(int width, int height, String title, boolean fullScreen) {
        this(width, height, title, fullScreen, true);
    }

    public Window(int width, int height, String title, boolean fullScreen, boolean load) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.aspectRatio = (float) width / (float) height;
        this.fullScreen = fullScreen;
        this.guiEventManager = new D_GuiEventManager();
        this.loader = new Loader(this);
        this.renderer = new MasterRenderer(this);
        initGLFW();
        if (load)
            DGUI.load(this);
        if (INSTANCE == null) setThisInstance();
    }

    public static Window get() {
        return INSTANCE;
    }

    public synchronized static void initGLFW() {
        if (init) return;
        GLFWErrorCallback.createPrint(System.err).set();
        init = glfwInit();
        if (!init) throw new IllegalStateException("could'nt initialize glfw");
        SoundManager.get().init();
        Time.init();

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {
            monitorWidth = vidMode.width();
            monitorHeight = vidMode.height();
            monitorAspectRatio = (float) monitorWidth / (float) monitorHeight;
        }
    }

    public static void terminate() {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        SoundManager.get().destroy();
    }

    public static int getMonitorWidth() {
        return monitorWidth;
    }

    public static int getMonitorHeight() {
        return monitorHeight;
    }

    public static float getMonitorAspectRatio() {
        return monitorAspectRatio;
    }

    private synchronized void setThisCurrent() {
        current = this;
    }

    private synchronized void setThisInstance() {
        INSTANCE = this;
    }

    public void makeCurrent() {
        if (this.didExit()) return;
        if (current == this) return;
        setThisCurrent();
        glfwMakeContextCurrent(window_ptr);
    }

    public void create() {
        create(null, true);
    }

    public void create(Window share, boolean createContext) {
        hint(GLFW_VISIBLE, GLFW_FALSE);
        window_ptr = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : 0, share != null ? share.getWindowPointer() : 0);
        if (window_ptr == 0) throw new IllegalStateException("window creation failed");
        glfwMakeContextCurrent(window_ptr);
        if (createContext) GL.createCapabilities();
        glViewport(0, 0, width, height);

        long monitor = glfwGetPrimaryMonitor();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer
                    x = stack.mallocInt(1),
                    y = stack.mallocInt(1),
                    w = stack.mallocInt(1),
                    h = stack.mallocInt(1);
            glfwGetMonitorPos(monitor, x, y);
            glfwGetWindowSize(window_ptr, w, h);

            setPosition(x.get() + (monitorWidth - w.get()) / 2, y.get() + (monitorHeight - h.get()) / 2);
        }

        initializeComponents();

        setThisInstance();
    }

    public void show() {
        glfwShowWindow(window_ptr);
        focused = true;
        setThisInstance();
    }

    public void update() {
        update(true, true);
    }

    public void update(boolean update, boolean poll) {
        Mouse.update();
        guiEventManager.update(this.guiList);
        if (updatables != null) updatables.forEach(Updatable::update);
        if (tasks != null) {
            tasks.forEach(Task::apply);
            tasks.clear();
        }
        renderer.render();

        fps++;
        if (oneSecDelay.over()) {
            frames = fps;
            fps = 0;
        }
        glfwSwapBuffers(window_ptr);
        if (update) {
            if (poll)
                glfwPollEvents();
            else
                glfwWaitEvents();
            Time.update();
        }
    }

    public void destroy() {
        invokeEventListeners(new GLFWWindowCloseEvent(this));
        loader.cleanUp();
        renderer.cleanUp();
        guiEventManager.destroy();
        glfwFreeCallbacks(window_ptr);
        glfwDestroyWindow(window_ptr);
        exited = true;
    }

    public GLFWListener addListener(Class<? extends GLFWEvent> eventClass, Listener listener) {
        return addListener(new GLFWListener(eventClass) {
            public void invoke(GLFWEvent event) {
                listener.invoke(event);
            }
        });
    }

    public GLFWListener addListener(GLFWListener listener) {
        Objects.requireNonNull(listener, "listener should'nt be null");
        if (listenerMap == null) listenerMap = new HashMap<>();
        List<GLFWListener> listeners = listenerMap.computeIfAbsent(listener.getEventClass(), k -> new ArrayList<>());
        if (listeners.contains(listener)) return null;
        listeners.add(listener);
        return listener;
    }

    public void removeListener(GLFWListener listener) {
        Objects.requireNonNull(listener);
        if (listenerMap == null) return;
        List<GLFWListener> listeners = listenerMap.get(listener.getEventClass());
        if (listeners == null) return;
        listeners.remove(listener);
    }

    public void add(D_Gui gui) {
        if (gui == null) return;
        if (guiList == null) guiList = new ArrayList<>();
        if (guiList.contains(gui)) return;
        synchronized (this) {
            guiList.add(gui);
        }
        gui.getStyle().notifyObservers();
    }

    public void remove(D_Gui gui) {
        if (guiList == null) return;
        if (gui == null) return;
        synchronized (this) {
            guiList.remove(gui);
        }
        gui.getStyle().notifyObservers();
    }

    public void addUpdatable(Updatable updatable) {
        if (updatable == null) return;
        if (updatables == null) updatables = new ArrayList<>();
        if (updatables.contains(updatable)) return;
        synchronized (this) {
            updatables.add(updatable);
        }
    }

    public void removeUpdatable(Updatable updatable) {
        if (updatable == null) return;
        if (updatables == null) return;
        synchronized (this) {
            updatables.remove(updatable);
        }
    }

    public void addTask(Task task) {
        if (task == null) return;
        if (tasks == null) tasks = new ArrayList<>();
        if (tasks.contains(task)) return;
        synchronized (this) {
            tasks.add(task);
        }
    }

    public void removeTask(Task task) {
        if (task == null) return;
        if (tasks == null) return;
        synchronized (this) {
            tasks.remove(task);
        }
    }

    public void hint(int hint, int value) {
        glfwWindowHint(hint, value);
    }

    public void focus() {
        glfwFocusWindow(window_ptr);
        focused = true;
        setThisInstance();
    }

    //getters
    public List<D_Gui> getGuiList() {
        return guiList;
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

    public int[] getPos() {
        int[] x = new int[1];
        int[] y = new int[1];
        glfwGetWindowPos(window_ptr, x, y);
        return new int[]{x[0], y[0]};
    }

    public int getFrames() {
        return frames;
    }

    public long getWindowPointer() {
        return window_ptr;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public float getVerticalPixelSize() {
        return 1 / height;
    }

    public float getHorizontalPixelSize() {
        return 1 / width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title.toString();
        glfwSetWindowTitle(window_ptr, title.toString());
    }

    public Loader getLoader() {
        return loader;
    }

    public D_GuiEventManager getGuiEventManager() {
        return guiEventManager;
    }

    public boolean didExit() {
        return exited;
    }

    public boolean isFocused() {
        return focused;
    }

    public boolean isExitRequested() {
        return glfwWindowShouldClose(window_ptr);
    }

    public boolean isResizable() {
        return glfwGetWindowAttrib(window_ptr, GLFW_RESIZABLE) == GLFW_TRUE;
    }

    public void setResizable(boolean value) {
        hint(GLFW_RESIZABLE, value ? GLFW_TRUE : GLFW_FALSE);
    }

    public int getTopLayer() {
        if (guiList == null) return 0;
        int top = 1;
        for (D_Gui gui : guiList) {
            int level = gui instanceof D_Container ? ((D_Container) gui).getTopLevel() : 1;
            if (level > top)
                top = level;
        }
        return top;
    }

    private Window getThis() {
        return this;
    }

    //setters
    public void setPosition(float x, float y) {
        glfwSetWindowPos(window_ptr, (int) x, (int) y);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window_ptr, width, height);
    }

    public void setSize(Vector2f size) {
        setSize((int) size.x, (int) size.y);
    }

    public void setFullScreen(boolean fullScreen) {
        if (this.fullScreen == fullScreen) return;
        this.fullScreen = fullScreen;
        if (fullScreen) {
            int[] x = new int[1];
            int[] y = new int[1];
            glfwGetWindowPos(window_ptr, x, y);
            glfwSetWindowMonitor(window_ptr, glfwGetPrimaryMonitor(), x[0], y[0], width, height, vSync ? 1 : 0);
        } else {
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (vidMode == null) return;
            glfwSetWindowMonitor(window_ptr, 0, vidMode.width() - width / 2, vidMode.height() - height / 2, width, height, vSync ? 1 : 0);
        }
    }

    public void enableVSync() {
        vSync = true;
        glfwSwapInterval(1);
    }

    public void disableVSync() {
        vSync = false;
        glfwSwapInterval(0);
    }

    public void exit() {
        glfwSetWindowShouldClose(window_ptr, true);
    }

    private void initializeComponents() {
        Mouse.init(this);
        Keyboard.init(this);

        renderer.init();
        guiEventManager.init(this);

        D_TextMaster.init(this);

        setUpCallbacks();
    }

    private void invokeEventListeners(GLFWEvent event) {
        if (listenerMap == null) return;
        List<GLFWListener> listeners = listenerMap.get(event.getClass());
        if (listeners == null) return;
        for (int i = 0; i < listeners.size(); i++) {
            GLFWListener listener = listeners.get(i);
            listener.invoke(event);
        }
    }

    private void setUpCallbacks() {
        //window callbacks
        glfwSetWindowSizeCallback(window_ptr, new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                getThis().makeCurrent();
                float prevWidth = width;
                float prevHeight = height;
                width = w;
                height = h;
                aspectRatio = (float) w / (float) h;
                GL11.glViewport(0, 0, width, height);
                invokeEventListeners(new GLFWWindowSizeEvent(getThis(), prevWidth, prevHeight));
                if (guiList != null) guiList.forEach(gui -> gui.getStyle().notifyObservers());
            }
        });

        glfwSetWindowIconifyCallback(window_ptr, new GLFWWindowIconifyCallback() {
            public void invoke(long window, boolean iconified) {
                if (iconified)
                    invokeEventListeners(new GLFWWindowMinimizeEvent(getThis()));
            }
        });

        glfwSetWindowMaximizeCallback(window_ptr, new GLFWWindowMaximizeCallback() {
            public void invoke(long window, boolean maximized) {
                if (maximized)
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
                if (focused) {
                    getThis().focused = true;
                    setThisInstance();
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
                invokeEventListeners(new GLFWFrameBufferSizeEvent(getThis(), width, height));
            }
        });


        //mouse callbacks
        glfwSetCursorEnterCallback(window_ptr, new GLFWCursorEnterCallback() {
            public void invoke(long window, boolean entered) {
                if (entered) invokeEventListeners(new GLFWMouseEnterEvent(getThis()));
                else invokeEventListeners(new GLFWMouseExitEvent(getThis()));
            }
        });

        glfwSetCursorPosCallback(window_ptr, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                if (focused) invokeEventListeners(new GLFWMouseMoveEvent(getThis(), xpos, ypos));
            }
        });

        glfwSetMouseButtonCallback(window_ptr, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                invokeEventListeners(new GLFWMouseButtonEvent(getThis(), button, action, mods));
                if (action == GLFW_PRESS)
                    invokeEventListeners(new GLFWMouseButtonPressEvent(getThis(), button, mods));
                if (action == GLFW_RELEASE)
                    invokeEventListeners(new GLFWMouseButtonReleaseEvent(getThis(), button, mods));
            }
        });

        glfwSetScrollCallback(window_ptr, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                invokeEventListeners(new GLFWScrollEvent(getThis(), xoffset, yoffset));
            }
        });


        //keyboard callbacks
        glfwSetKeyCallback(window_ptr, new GLFWKeyCallback() {
            @Override
            public void invoke(long w, int key, int scancode, int action, int mods) {
                if (!focused) return;
                if (key == -1) return;
                invokeEventListeners(new GLFWKeyEvent(getThis(), key, scancode, action, mods));
                if (action == GLFW_PRESS) invokeEventListeners(new GLFWKeyPressEvent(getThis(), key, scancode, mods));
                if (action == GLFW_RELEASE)
                    invokeEventListeners(new GLFWKeyReleaseEvent(getThis(), key, scancode, mods));
                if (action == GLFW_REPEAT) invokeEventListeners(new GLFWKeyRepeatEvent(getThis(), key, scancode, mods));
            }
        });

        glfwSetCharCallback(window_ptr, new GLFWCharCallback() {
            public void invoke(long window, int codepoint) {
                if (focused) invokeEventListeners(new GLFWCharEvent(getThis(), codepoint));
            }
        });

    }

}
