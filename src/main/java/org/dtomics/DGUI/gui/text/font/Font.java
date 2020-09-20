package org.dtomics.DGUI.gui.text.font;

import org.dtomics.DGUI.gui.renderer.Loader;
import org.dtomics.DGUI.gui.renderer.Texture;

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
        this.fontTexture = loader.loadTexture(path + ".png", false);
    }

    public FontFile getFontFile() { return fontFile; }

    public Texture getFontTexture() { return fontTexture; }

}
