package engine.ui.gui.text.font;

/**
 * This class represents a Font type.
 * It holds the meta data file and a texture atlas that contains distance field textures of each character in
 * the font.
 *
 * @author Abdul Kareem
 */
public class Font {

    private FontFile fontFile;
    private FontTexture fontTexture;

    public Font(String path , float padding) {
        this.fontFile = new FontFile(path + ".fnt", padding );
        this.fontTexture = new FontTexture(path + ".png");
    }

    public FontFile getFontFile() {
        return fontFile;
    }

    public FontTexture getFontTexture() { return fontTexture; }

}
