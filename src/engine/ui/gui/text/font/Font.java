package engine.ui.gui.text.font;

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
