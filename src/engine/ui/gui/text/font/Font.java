package engine.ui.gui.text.font;

import engine.ui.gui.renderer.Loader;
import engine.ui.gui.renderer.Texture;

/**
 * This class represents a Font type.
 * It holds the meta data file and a texture atlas that contains distance field textures of each character in
 * the font.
 *
 * @author Abdul Kareem
 */
public class Font {

    private FontFile fontFile;
    private Texture fontTexture;

    public Font(String path , float padding, Loader loader) {
        this.fontFile = new FontFile(path + ".fnt", padding );
        this.fontTexture = loader.loadTexture(path + ".png");
    }

    public FontFile getFontFile() { return fontFile; }

    public Texture getFontTexture() { return fontTexture; }

}
