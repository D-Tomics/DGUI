package engine.ui.utils.colors;

public class ColorFactory {

    private ColorFactory() { }

    public static Color createColor(int r, int g, int b, Color dest) {
        return createColor(r / 255f, g / 255f, b / 255f, dest);
    }

    public static Color createColor(float r, float g, float b, float a, Color dest) {
        if(dest == null)
            return new Color(r,g,b,a);
        dest.set(r,g,b,a);
        return dest;
    }

    public static Color createColor(float r, float g, float b, Color dest) { return createColor(r,g,b,1,dest); }

    public static Color createColor(int color, Color dest) {
        return HexToRGB(color,dest);
    }

    public static int RGBAtoHEX(int r, int g, int b) {
        return ((byte) r & 0xFF) << 16 |
                ((byte) g & 0xFF) << 8 |
                ((byte) b & 0xFF);
    }

    public static int RGBAtoHEX(float r, float g, float b) {
        return RGBAtoHEX(
                (int)(r * 255),
                (int)(g * 255),
                (int)(b * 255)
        );
    }

    private static Color HexToRGB(int hex, Color dest) {
        float r = ((hex >> 16) & 0xFF) / 255.0f;
        float g = ((hex >> 8) & 0xFF) / 255.0f;
        float b = (hex & 0xFF) /255.0f;

        if(dest == null) dest = new Color(r,g,b);
        else dest.set(r,g,b);
        return dest;
    }

}
