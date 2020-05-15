package engine.ui.gui.renderer;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.font.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a single layer in a gui system. Its basically a wrapper class for a List of guis and a text map.
 * It stores all the guis and texts in a single level.
 * Used to render guis and texts in order from bottom layer to top layer
 *
 * @author kareem
 */
public class Layer {

    private ArrayList<D_Gui> guis;
    private HashMap<Font, List<D_TextBox>> textMap;

    protected Layer() {
        this.guis = new ArrayList<>();
        this.textMap = new HashMap<>();
    }

    protected void push(D_Gui gui) {
        guis.add(gui);
        if(gui.getTextMap() != null)
            textMap.putAll(gui.getTextMap());
    }

    protected ArrayList<D_Gui> getGuis() {
        return guis;
    }

    protected HashMap<Font,List<D_TextBox>> getTextMap() {
        return textMap;
    }

    protected boolean contains(D_Gui gui) {
        return guis.contains(gui);
    }

}
