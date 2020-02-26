package engine.ui.gui.text;

import engine.ui.IO.Window;
import engine.ui.IO.events.GLFWEvent;
import engine.ui.IO.events.GLFWListener;
import engine.ui.IO.events.GLFWWindowSizeEvent;
import engine.ui.gui.renderer.TextRenderer;
import engine.ui.gui.text.font.Font;

import java.util.*;

public final class D_TextMaster {

    private static boolean initialized = false;
    private static Map<Font, List<D_TextBox>> textMap;

    private static GLFWListener sizeChangeListener = new GLFWListener(GLFWWindowSizeEvent.class) {
        @Override
        public void invoke(GLFWEvent event) {
            if(textMap == null) return;
            Set<Font> fonts = textMap.keySet();
            for(Font font : fonts) {
                List<D_TextBox> texts = textMap.get(font);
                for(D_TextBox text : texts)
                    text.getMesh().updateData(text);
            }
        }
    };

    public static void init() {
        if(initialized) return;
        Window.INSTANCE.addListener(sizeChangeListener);
    }

    protected static void load(D_TextBox text) {
        if(textMap == null) textMap = new HashMap<>();
        List<D_TextBox> texts = textMap.get(text.getFont());
        if(texts == null) {
            texts = new ArrayList<>();
            textMap.put(text.getFont(),texts);
        }
        texts.add(text);
    }

    protected static void remove(D_TextBox text) {
        if(textMap == null || text == null) return;
        List<D_TextBox> texts = textMap.get(text.getFont());
        if(texts == null) return;
        texts.remove(text);
    }

    public static void render() {
        if(textMap == null) return;
        TextRenderer.render(textMap);
    }

    public static void cleanUp() {
        TextRenderer.cleanUp();
    }

}
