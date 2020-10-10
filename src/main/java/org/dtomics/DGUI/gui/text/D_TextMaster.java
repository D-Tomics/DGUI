package org.dtomics.DGUI.gui.text;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.IO.events.GLFWWindowSizeEvent;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.text.font.Font;
import org.dtomics.DGUI.utils.D_Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * This class keeps track of all the texts in the application for each window.
 *
 * @author Abdul Kareem
 */
public final class D_TextMaster {
    private static final Consumer<List<FontTextMap>> onSizeChangeConsumer = fontTextMaps -> {
        for (int i = 0; i < fontTextMaps.size(); i++) {
            List<D_TextBox> textBoxes = fontTextMaps.get(i).getTextBoxes();
            if (textBoxes != null)
                textBoxes.forEach(D_TextBox::requestUpdate);
        }
    };

    private static Map<Window, Map<D_Gui, List<FontTextMap>>> windowGuiFontTextListMap;

    public static void init(Window window) {
        window.addListener(GLFWWindowSizeEvent.class, D_TextMaster::onSizeChange);
    }

    public static synchronized void load(Window window, D_Gui gui, D_TextBox text) {
        if (windowGuiFontTextListMap == null)
            windowGuiFontTextListMap = new HashMap<>();
        FontTextMap fontTextMap = getFontTextMap(window, gui, text.getFont());
        fontTextMap.addText(text);
    }

    public static synchronized void remove(Window window, D_Gui gui, D_TextBox text) {
        if (windowGuiFontTextListMap == null || text == null) return;
        Optional.of(windowGuiFontTextListMap.get(window))
                .map(w -> w.get(gui))
                .map(l -> getFontTextMap(l, text.getFont()))
                .ifPresent(f -> f.removeText(text));
    }

    public static List<FontTextMap> getTextMap(Window window, D_Gui gui) {
        if (windowGuiFontTextListMap != null) {
            Map<D_Gui, List<FontTextMap>> map = windowGuiFontTextListMap.get(window);
            if (map != null) {
                return map.get(gui);
            }
        }
        return null;
    }

    public static void update(Window window, Font oldFont, Font newFont, D_TextBox textBox) {
        Collection<List<FontTextMap>> fonTextMapsCollection = Optional.of(windowGuiFontTextListMap.get(window)).map(Map::values).get();
        fonTextMapsCollection.forEach(fontTextMaps -> {
            FontTextMap oldFontTextMap = null;
            FontTextMap newFontTextMap = null;

            for (int i = 0; i < fontTextMaps.size(); i++) {
                if (oldFontTextMap != null && newFontTextMap != null) break;
                FontTextMap fontTextMap = fontTextMaps.get(i);
                if (fontTextMap.getFont().equals(oldFont)) oldFontTextMap = fontTextMap;
                else if (fontTextMap.getFont().equals(newFont)) newFontTextMap = fontTextMap;
            }

            if (oldFontTextMap == null) return;
            if (newFontTextMap == null) {
                newFontTextMap = new FontTextMap(newFont);
                fontTextMaps.add(newFontTextMap);
                return;
            }

            oldFontTextMap.removeText(textBox);
            newFontTextMap.addText(textBox);
        });
    }

    private static void onSizeChange(D_Event<Window> e) {
        GLFWWindowSizeEvent event = (GLFWWindowSizeEvent) e;
        if (windowGuiFontTextListMap == null || windowGuiFontTextListMap.get(event.getSource()) == null) return;

        Collection<List<FontTextMap>> fontTextMapsCollection = windowGuiFontTextListMap.get(event.getSource()).values();
        fontTextMapsCollection.forEach(onSizeChangeConsumer);
    }

    private static FontTextMap getFontTextMap(Window window, D_Gui gui, Font font) {
        Map<D_Gui, List<FontTextMap>> guiFontTextListMap = windowGuiFontTextListMap.computeIfAbsent(window, k -> new HashMap<>());
        List<FontTextMap> fontTextMaps = guiFontTextListMap.computeIfAbsent(gui, g -> new ArrayList<>());
        FontTextMap fontTextMap = getFontTextMap(fontTextMaps, font);
        if (fontTextMap == null)
            fontTextMap = new FontTextMap(font);
        fontTextMaps.add(fontTextMap);
        return fontTextMap;
    }

    private static FontTextMap getFontTextMap(List<FontTextMap> fontTextMaps, Font font) {
        for (int i = 0; i < fontTextMaps.size(); i++) {
            if (fontTextMaps.get(i).getFont().equals(font)) {
                return fontTextMaps.get(i);
            }
        }
        return null;
    }

}
