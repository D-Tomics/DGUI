package org.dtomics.DGUI.gui.renderer;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.renderer.shader.D_GuiShader;
import org.dtomics.DGUI.utils.Buffers;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;

/**The instance of this class renders the gui's given to it in order on to the window that owns it.
 * There will be one instance of this class per window.
 * If one of the gui's are focused then it'll be rendered last.
 *
 * @author Abdul Kareem
 */
public class D_GuiRenderer {

    private int vao;
    private Window window;
    private D_GuiShader shader;
    private final Texture WHITE_TEXTURE;

    D_GuiRenderer(Window window) {
        this.window = window;
        shader = new D_GuiShader(window);
        vao = window.getLoader().load2dVertexData(new float[]{1, 1, -1, 1, 1, -1, -1, -1});
        this.WHITE_TEXTURE = window.getLoader().generateTexture(1, 1, Buffers.createByteBuffer(4, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF));
    }

    void render(ArrayList<D_Gui> components) {
        bindVAO();
        shader.start();
        D_Gui focused = null;
        WHITE_TEXTURE.bind(0);
        for (D_Gui component : components) {
            if(component.isFocused()) {
                focused = component;
                continue;
            }
            draw(component);
        }
        draw(focused);
        shader.stop();
        unbind();
    }

    private void bindVAO() {
        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);
    }

    private void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    private void draw(D_Gui gui) {
        if (gui == null || !gui.isVisible()) return;

        if(gui.getStyle().getBgTexture() != null)
            gui.getStyle().getBgTexture().bind(0);

        shader.loadComponent(gui);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        if(gui.getStyle().getBgTexture() != null)
            WHITE_TEXTURE.bind(0);
    }

    void cleanUp() {
        shader.cleanUp();
    }

}
