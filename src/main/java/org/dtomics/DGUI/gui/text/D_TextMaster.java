package org.dtomics.DGUI.gui.text;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.IO.events.GLFWEvent;
import org.dtomics.DGUI.IO.events.GLFWListener;
import org.dtomics.DGUI.IO.events.GLFWWindowSizeEvent;
import org.dtomics.DGUI.gui.text.font.Font;

import java.util.*;

/**This class keeps track of all the texts in the application for each window.
 *
 * @author Abdul Kareem
 */
public final class D_TextMaster {

    private static Map<Window,Map<Font, List<D_TextBox>>> textMap;

    private static GLFWListener sizeChangeListener = new GLFWListener(GLFWWindowSizeEvent.class) {
        @Override
        public void invoke(GLFWEvent event) {
            if(textMap == null) return;
            Map<Font, List<D_TextBox>> map = textMap.get(event.getSource());
            if(map == null) return;
            Set<Font> fonts = map.keySet();
            for(Font font : fonts) {
                List<D_TextBox> texts = map.get(font);
                for(D_TextBox text : texts) {
                    text.requestUpdate();
                    text.update(event.getSource().getLoader());
                }
            }
        }
    };

    public static void init(Window window) {
        window.addListener(sizeChangeListener);
    }

    protected static void load(Window window, D_TextBox text) {
        if(textMap == null)
            textMap = new HashMap<>();
        Map<Font, List<D_TextBox>> map = textMap.computeIfAbsent(window, k -> new HashMap<>());
        List<D_TextBox> texts = map.computeIfAbsent(text.getFont(),k -> new ArrayList<>());
        texts.add(text);
    }

    public static  Map<Font, List<D_TextBox>> getTextMap(Window window) { return textMap != null ? textMap.get(window) : null; }

    protected static void remove(D_TextBox text) {
        if(textMap == null || text == null) return;
        for (Map<Font, List<D_TextBox>> maps : textMap.values()) {
            List<D_TextBox> texts = maps.get(text.getFont());
            if(texts == null) continue;
            if(!texts.contains(text)) continue;
            texts.remove(text);
        }
    }
}
