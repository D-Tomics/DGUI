package engine.ui.gui.text.font;

public class FontChar {

    private int id;
    private float xTexCoord;
    private float yTexCoord;
    private float xMaxTextureCoord;
    private float yMaxTextureCoord;
    private float xOffset;
    private float yOffset;
    private float xAdvance;
    private float sizeX;
    private float sizeY;

    FontChar(int id, float x, float y, float xTexSize, float yTextSize, float xOffset, float yOffset, float xAdvance, float sizeX, float sizeY) {
        this.id = id;
        this.xTexCoord = x;
        this.yTexCoord = y;
        this.xMaxTextureCoord = xTexSize;
        this.yMaxTextureCoord = yTextSize;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getId() {
        return id;
    }

    public float getXTexCoord() {
        return xTexCoord;
    }

    public float getYTexCoord() {
        return yTexCoord;
    }

    public float getXMaxTexCoord() {
        return xMaxTextureCoord;
    }

    public float getYMaxTexCoord() {
        return yMaxTextureCoord;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public float getXAdvance() {
        return xAdvance;
    }

    public String toString() {
        return ""+((char)id);
    }

    public boolean equals(char c) {
        return id == (int)c;
    }

}
