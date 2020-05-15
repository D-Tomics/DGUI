package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Container;
import engine.ui.gui.components.D_Gui;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.glDisable;

public class MasterRenderer {

    private LayerStack layerStack;
    private D_GuiRenderer guiRenderer;
    private D_TextRenderer DTextRenderer;

    private Window window;

    public MasterRenderer(Window window) {
        this.window = window;
    }

    public void init() {
        guiRenderer = new D_GuiRenderer(window);
        DTextRenderer = new D_TextRenderer(window);
        layerStack = new LayerStack();
    }

    public void render() {
        setUpLayers(window.getGuiList());
        window.makeCurrent();
        initGL();
        for (Layer layer : layerStack) {
            guiRenderer.render(layer.getGuis());
            DTextRenderer.render(layer.getTextMap());
        }
    }

    public void cleanUp() {
        guiRenderer.cleanUp();
        DTextRenderer.cleanUp();
    }

    private void setUpLayers(List<D_Gui> guis) {
        if (guis == null) return;
        for (int i = 0; i < guis.size(); i++) {
            D_Gui gui = guis.get(i);
            Layer layer = layerStack.get(gui.getLevel());
            if (layer == null) layerStack.push(layer = new Layer());
            if (layer.contains(gui)) continue;
            layer.push(gui);
            if(gui.getQuads() != null) gui.getQuads().forEach(layerStack.getOverLay()::push);
            if (gui instanceof D_Container) setUpLayers(((D_Container) gui).getChildList());
        }
    }

    private void initGL() {
        glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

}
