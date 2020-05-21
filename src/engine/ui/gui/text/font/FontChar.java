package engine.ui.gui.text.font;

/**
 * This Java Bean holds the meta data of a character in a font.
 * This data is loaded via the meta file <Code>FontFile</Code>
 *
 * @see FontFile
 * @author Abdul Kareem
 */
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

    /**
     * This creates a character of a font file with specific ascii value
     * @param id        the ascii value of the character
     * @param x         x texture coordinate of the character in the texture atlas that it was loaded from.
     * @param y         y texture coordinate of the character in the texture atlas that it was loaded from.
     * @param xTexSize  width of the character in the texture atlas
     * @param yTextSize height of the character in the texture atlas
     * @param xOffset   x offset from the cursor x coordinate
     * @param yOffset   y offset from the line that the characters are placed on
     * @param xAdvance  indicates how far to move the cursor after this character
     * @param sizeX     indicates width of the quad that creates this character
     * @param sizeY     indicates height of the quad that creates this character
     */
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
