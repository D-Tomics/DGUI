package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.renderer.shader.D_TextShader;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.font.Font;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class D_TextRenderer {

    private Window window;
    private D_TextShader shader;

    public D_TextRenderer(Window window) {
        this.window = window;
        shader = new D_TextShader(window);
    }

    public void render(Map<Font, List<D_TextBox>> textMap) {
        if (textMap == null) return;
        Set<Font> fonts = textMap.keySet();
        for (Font font : fonts) {
            shader.start();
            font.getFontTexture().bind(0);
            for (D_TextBox text : textMap.get(font)) {
                if (!text.isVisible()) continue;
                text.update(window.getLoader());
                text.getMesh().bind();
                shader.load(text);
                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getMeshData().getVertexCount());
                text.getMesh().unbind();
            }
            shader.stop();
        }
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
