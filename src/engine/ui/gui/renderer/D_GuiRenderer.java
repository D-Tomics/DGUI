package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.renderer.shader.D_GuiShader;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class D_GuiRenderer {

    private int vao;
    private Window window;
    private D_GuiShader shader;

    public D_GuiRenderer(Window window) {
        this.window = window;
        shader = new D_GuiShader(window);
        vao = window.getLoader().load2dVertexData(new float[]{1, 1, -1, 1, 1, -1, -1, -1});
    }

    public void render(ArrayList<D_Gui> components) {
        bindVAO();
        shader.start();
        D_Gui focused = null;
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
        shader.loadComponent(gui);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }

    void cleanUp() {
        shader.cleanUp();
    }

}
