package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.renderer.shader.D_TextShader;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.font.Font;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextRenderer {

    private static D_TextShader shader;

    private Window window;
    public TextRenderer(Window window) {
        this.window = window;
        if(shader == null)
            shader = new D_TextShader();
    }
    public void render(Map<Font, List<D_TextBox>> textMap) {
        initGL();
        Set<Font> fonts = textMap.keySet();
        for(Font font : fonts) {
            shader.start();
            font.getFontTexture().bind(0);
            for(D_TextBox text : textMap.get(font)) {
                if(!text.isVisible()) continue;
                text.getMesh().bind();
                shader.load(text);
                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getMesh().getData().getVertexCount());
                text.getMesh().unbind();
            }
            shader.stop();
        }
    }

    private void initGL() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
