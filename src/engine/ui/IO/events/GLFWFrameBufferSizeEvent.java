package engine.ui.IO.events;

import engine.ui.IO.Window;

public class GLFWFrameBufferSizeEvent extends GLFWEvent {

    private int width;
    private int height;

    public GLFWFrameBufferSizeEvent(Window source, int width, int height) {
        super(source);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
