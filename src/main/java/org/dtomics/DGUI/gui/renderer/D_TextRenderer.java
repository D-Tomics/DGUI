package org.dtomics.DGUI.gui.renderer;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.renderer.shader.D_TextShader;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.text.FontTextMap;
import org.dtomics.DGUI.gui.text.font.Font;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * The instance of this class renders the texts given to it in order on to the window that owns it.
 * There will be one instance of this class per window.
 *
 * @author Abdul Kareem
 */
public class D_TextRenderer {

    private final Window window;
    private final D_TextShader shader;

    D_TextRenderer(Window window) {
        this.window = window;
        shader = new D_TextShader(window);
    }

    void render(List<FontTextMap> textMap) {
        if (textMap == null) return;
        for (int i = 0; i < textMap.size(); i++) {
            FontTextMap fontTextMap = textMap.get(i);
            Font font = fontTextMap.getFont();
            shader.start();
            font.getFontTexture().bind(0);
            final List<D_TextBox> textBoxes = fontTextMap.getTextBoxes();
            if (textBoxes != null) {
                for (int j = 0; j < textBoxes.size(); j++) {
                    D_TextBox text = textBoxes.get(j);
                    if (!text.isVisible()) continue;
                    text.update(window.getLoader());
                    text.getMesh().bind();
                    shader.load(text);
                    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getMeshData().getVertexCount());
                    text.getMesh().unbind();
                }
            }
            shader.stop();
        }
    }

    void cleanUp() {
        shader.cleanUp();
    }

}
