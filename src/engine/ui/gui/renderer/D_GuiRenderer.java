package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.renderer.shader.D_GuiShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class D_GuiRenderer {

    private static D_GuiShader shader = new D_GuiShader();
    private static int VERTEX_ARRAY_NAME = -1;
    public static void render() {
        if(VERTEX_ARRAY_NAME == -1) {
            VERTEX_ARRAY_NAME = Loader.load2dVertexData(new float[]{
                    1, 1,
                    -1, 1,
                    1, -1,
                    -1,-1
            });
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);

        bindVAO();
        shader.start();
        List<D_Gui> components = Window.INSTANCE.getGuiList();
        if(components == null) return;
        for(D_Gui component : components)
            draw(component);
        shader.stop();
        unbind();
    }

    private static void bindVAO() {
        GL30.glBindVertexArray(VERTEX_ARRAY_NAME);
        GL20.glEnableVertexAttribArray(0);
    }

    private static void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    private static void draw(D_Gui gui) {
        if(gui == null) return;
        if(gui.isVisible()) {
            shader.loadComponent(gui);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP,0,4);
            if(gui.getGeometries() != null)
                gui.getGeometries().forEach(D_GuiRenderer::draw);

            if(gui instanceof D_Container) {
                D_Gui focusedGui = null;
                if(((D_Container) gui).getChildList() != null) {
                    for(D_Gui child : ((D_Container) gui).getChildList()) {
                        if(child.equals(Window.INSTANCE.getGuiEventManager().getFocusedGui())) {
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

    public static void cleanUp() {
        shader.cleanUp();
    }

}
