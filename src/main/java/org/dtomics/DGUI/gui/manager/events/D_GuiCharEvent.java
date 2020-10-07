package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;

/**
 * This represents a character event.
 *
 * @author Abdul Kareem
 */
public class D_GuiCharEvent extends D_GuiEvent {

    private final int codePoint;

    public D_GuiCharEvent(D_Gui gui, int codePoint) {
        super(gui);
        this.codePoint = codePoint;
    }

    public int getCodePoint() {
        return codePoint;
    }
}
