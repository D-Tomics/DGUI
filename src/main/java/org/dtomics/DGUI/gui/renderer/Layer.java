package org.dtomics.DGUI.gui.renderer;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.text.FontTextMap;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single layer in a gui system. Its basically a wrapper class for a List of guis and a text map.
 * It stores all the guis and texts in a single level.
 * Used to render guis and texts in order from bottom layer to top layer
 *
 * @author kareem
 */
public class Layer {

    private final ArrayList<D_Gui> guis;
    private final List<FontTextMap> textMap;

    protected Layer() {
        this.guis = new ArrayList<>();
        this.textMap = new ArrayList<>();
    }

    protected void push(D_Gui gui) {
        guis.add(gui);
        if (gui.getTextMap() != null) {
            textMap.addAll(gui.getTextMap());
        }
    }

    protected void clear() {
        guis.clear();
        textMap.clear();
    }

    protected ArrayList<D_Gui> getGuis() {
        return guis;
    }

    protected List<FontTextMap> getTextMap() {
        return textMap;
    }

    protected boolean contains(D_Gui gui) {
        return guis.contains(gui);
    }

}
