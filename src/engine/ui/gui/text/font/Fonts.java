package engine.ui.gui.text.font;

public enum Fonts {

    Calibri_Light(8f),
    Comic_Sans_MS(8f),
    Consolas(8f),
    Courier_New(8),
    Roboto_Thin(8),
    Candara(8),
    Agency_FB(8);

    private Font font;
    private float desiredPadding ;
    private Fonts(float desiredPadding) {
        this.desiredPadding = desiredPadding;
    }

    public Font getFont() {
        if(this.font == null)
            this.font = new Font("/text/" + this.name(), desiredPadding);
        return font;
    }

}
