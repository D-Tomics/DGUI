package org.dtomics.DGUI.gui.renderer;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.renderer.shader.D_TextShader;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.text.font.Font;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**The instance of this class renders the texts given to it in order on to the window that owns it.
 * There will be one instance of this class per window.
 *
 * @author Abdul Kareem
 */
public class D_TextRenderer {

    private Window window;
    private D_TextShader shader;

    D_TextRenderer(Window window) {
        this.window = window;
        shader = new D_TextShader(window);
    }

    void render(Map<Font, List<D_TextBox>> textMap) {
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

    void cleanUp() {
        shader.cleanUp();
    }

}
