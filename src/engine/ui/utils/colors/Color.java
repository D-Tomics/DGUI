package engine.ui.utils.colors;

public final class Color {

    public static final Color BLACK = new Color(0);
    public static final Color WHITE = new Color(1,1,1);
    public static final Color RED = new Color(1,0,0);
    public static final Color GREEN = new Color(0,1,0);
    public static final Color BLUE = new Color(0,0,1);

    public float r,g,b;

    public Color() {
        this(0);
    }

    public Color(float d) {
        this(d,d,d);
    }

    public Color(float r, float g, float b) {
        this.r = r > 1 ? r / 255f : r;
        this.g = g > 1 ? g / 255f : g;
        this.b = b > 1 ? b / 255f : b;
    }

    public Color(Color color) {
        this.set(color);
    }

    public void set(float r, float g, float b) {
        this.r = r > 1 ? r / 255f : r;
        this.g = g > 1 ? g / 255f : g;
        this.b = b > 1 ? b / 255f : b;
    }

    public void set(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
    }

    //0xrrggbb
    public void set(int color) {
        this.r = ((byte) ((color >> 16) & 0xFF)) / 255.0f;
        this.g = ((byte) ((color >>  8) & 0xFF)) / 255.0f;
        this.b = ((byte) ( color        & 0xFF)) / 255.0f;
    }
    public int toHex() {
        return ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | ((int)(b * 255));
    }

    @Override
    public String toString() {
        return r+" , "+g+" , "+b;
    }

}
