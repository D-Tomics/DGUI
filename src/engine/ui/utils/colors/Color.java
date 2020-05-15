package engine.ui.utils.colors;

public final class Color {

    public static final Color BLACK = new Color(0);
    public static final Color WHITE = new Color(1,1,1);
    public static final Color RED = new Color(1,0,0);
    public static final Color GREEN = new Color(0,1,0);
    public static final Color BLUE = new Color(0,0,1);

    private float r,g,b,a;

    private float brightness = 1;

    public Color() {
        this(0);
    }
    public Color(float d) {
        this(d,d,d);
    }
    public Color(float r, float g, float b) { this(r, g, b, 1); }
    public Color(float r, float g, float b, float a) { set(r,g,b,a); }
    public Color(Color color) { this.set(color); }

    public Color set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    public Color set(float r, float g, float b) { return this.set(r,g,b,1);}
    // integer colors are varied from 0 to 255
    public Color set(int r, int g, int b) { return this.set(r / 255f, g / 255f, b / 255f, 1f);}
    public Color set(int r, int g, int b, int a) { return this.set(r / 255f, g / 255f, b / 255f, a / 255f);}
    public Color set(Color color) { return set(color.r(), color.g(), color.b(), color.a()); }

    public Color setBrightness(float brightness) { this.brightness = brightness; return this; }
    public float getBrightness() { return brightness; }

    //0xrrggbb
    public void set(int color) {
        this.r = ((byte) ((color >> 16) & 0xFF)) / 255.0f;
        this.g = ((byte) ((color >>  8) & 0xFF)) / 255.0f;
        this.b = ((byte) ( color        & 0xFF)) / 255.0f;
    }

    public float r() { return r * brightness; }
    public float g() { return g * brightness; }
    public float b() { return b * brightness; }
    public float a() { return a; }

    public void r(float r) { this.r = r; }
    public void g(float g) { this.g = g; }
    public void b(float b) { this.b = b; }
    public void a(float a) { this.a = a; }

    public static Color mix(Color a, Color b, float mix) {
        return mix(a, b, new Color(), mix);
    }

    public static Color mix(Color a, Color b, Color dest, float mix) {
        dest.r = a.r() * (1.0f - mix) + b.r() * mix;
        dest.g = a.g() * (1.0f - mix) + b.g() * mix;
        dest.b = a.b() * (1.0f - mix) + b.b() * mix;
        dest.a = a.a() * (1.0f - mix) + b.a() * mix;
        return dest;
    }

    @Override
    public String toString() {
        return "< "+r+" , "+g+" , "+b +" , " + a+ " >";
    }

}
