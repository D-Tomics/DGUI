package org.dtomics.DGUI.gui.text;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.IO.events.GLFWEvent;
import org.dtomics.DGUI.IO.events.GLFWListener;
import org.dtomics.DGUI.IO.events.GLFWWindowSizeEvent;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.text.font.Font;

import java.util.*;

/**This class keeps track of all the texts in the application for each window.
 *
 * @author Abdul Kareem
 */
public final class D_TextMaster {

    private static Map<Window, Map<D_Gui, Map<Font, List<D_TextBox>>>> guiTextMap;

    private static GLFWListener sizeChangeListener = new GLFWListener(GLFWWindowSizeEvent.class) {
        @Override
        public void invoke(GLFWEvent event) {
            if(guiTextMap == null || guiTextMap.get(event.getSource()) == null)
                return;
            Collection<Map<Font, List<D_TextBox>>> textMaps = guiTextMap.get(event.getSource()).values();
            for(Map<Font,List<D_TextBox>> textMap : textMaps) {
                Set<Font> keys = textMap.keySet();
                for(Font font : keys) {
                    List<D_TextBox> textBoxes = textMap.get(font);
                    for(D_TextBox textBox : textBoxes) {
                        textBox.requestUpdate();
                        textBox.update(event.getSource().getLoader());
                    }
                }
            }
        }
    };

    public static void init(Window window) {
        window.addListener(sizeChangeListener);
    }

    public static void load(Window window, D_Gui gui, D_TextBox text) {
        if(guiTextMap == null)
            guiTextMap = new HashMap<>();
        Map<D_Gui, Map<Font, List<D_TextBox>>> map = guiTextMap.computeIfAbsent(window, w -> new HashMap<>());
        Map<Font, List<D_TextBox>> textMap = map.computeIfAbsent(gui,g -> new HashMap<>());
        List<D_TextBox> texts = textMap.computeIfAbsent(text.getFont(), f -> new ArrayList<>());
        texts.add(text);
    }

    public static void remove(Window window, D_Gui gui, D_TextBox text) {
        if(guiTextMap == null || text == null) return;
        Map<D_Gui,Map<Font, List<D_TextBox>>> map = guiTextMap.get(window);
        if(map != null) {
            Optional.of(map.get(gui))
                    .map(m -> m.get(text.getFont()))
                    .ifPresent(l -> l.remove(text));
        }
    }

    public static Map<Font, List<D_TextBox>> getTextMap(Window window, D_Gui gui) {
        if(guiTextMap != null) {
            Map<D_Gui,Map<Font,List<D_TextBox>>> map = guiTextMap.get(window);
            if(map != null) {
                return map.get(gui);
            }
        }
        return null;
    }

    public static void update(Window window, Font oldFont, Font newFont, D_TextBox textBox) {
        Collection<Map<Font,List<D_TextBox>>> maps = Optional.of(guiTextMap.get(window)).map(Map::values).get();
        for(Map<Font, List<D_TextBox>> map : maps) {
            List<D_TextBox> texts = map.get(oldFont);
            if(texts.contains(textBox)) {
                texts.remove(textBox);
                List<D_TextBox> texts2 = map.computeIfAbsent(newFont, m -> new ArrayList<>());
                texts2.add(textBox);
            }
        }
    }

}
