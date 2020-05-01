package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.renderer.shader.D_GuiShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class D_GuiRenderer {



    private int vao;
    private Window window;
    private D_GuiShader shader;
    public D_GuiRenderer(Window window) {
        this.window = window;
        shader = new D_GuiShader(window);
        vao = window.getLoader().load2dVertexData(new float[]{ 1, 1,   -1, 1,   1, -1,   -1,-1});
    }

    public void render() {
        glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);

        bindVAO();
        shader.start();
        List<D_Gui> components = window.getGuiList();
        if(components == null) return;
        for(D_Gui component : components)
            draw(component);
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
        if(gui == null) return;
        if(gui.isVisible()) {
            shader.loadComponent(gui);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP,0,4);
            if(gui.getQuads() != null)
                gui.getQuads().forEach(this::draw);

            if(gui instanceof D_Container) {
                D_Gui focusedGui = null;
                if(((D_Container) gui).getChildList() != null) {
                    for(D_Gui child : ((D_Container) gui).getChildList()) {
                        if(child.equals(window.getGuiEventManager().getFocusedGui())) {
                            focusedGui = child;
                            continue;
                        }
                        draw(child);
                    }
                    draw(focusedGui);
                }
            }
        }
    }

    void cleanUp() {
        shader.cleanUp();
    }

}
