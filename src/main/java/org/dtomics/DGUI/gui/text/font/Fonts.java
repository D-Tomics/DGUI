package org.dtomics.DGUI.gui.text.font;

import org.dtomics.DGUI.gui.renderer.Loader;

/**
 * This enum holds the fonts that are already loaded into the engine
 */
public enum Fonts {

    Calibri_Light(8f),
    Comic_Sans_MS(8f),
    Consolas(8f),
    Courier_New(8),
    Roboto_Thin(8),
    Candara(8),
    Agency_FB(8),
    Segoe_UI(5);

    private Font font;
    private final float desiredPadding;

    Fonts(float desiredPadding) {
        this.desiredPadding = desiredPadding;
    }

    public Font getFont(Loader loader) {
        if (this.font == null)
            this.font = new Font("/text/" + this.name(), desiredPadding, loader);
        return font;
    }

}
