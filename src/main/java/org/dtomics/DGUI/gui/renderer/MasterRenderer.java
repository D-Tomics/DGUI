package org.dtomics.DGUI.gui.renderer;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Container;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.glDisable;

/**
 * This is the master renderer that renders both gui and texts.
 * This class renders them based on the layers they occupy in.
 * There will be one instance of this class per Window.
 *
 * @author Abdul Kareem
 * @see Layer
 * @see LayerStack
 */
public class MasterRenderer {

    private LayerStack layerStack;
    private D_GuiRenderer guiRenderer;
    private D_TextRenderer DTextRenderer;

    private final Window window;

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
        for (int i = 0; i < layerStack.getLayers().size(); i++) {
            Layer layer = layerStack.getLayers().get(i);
            guiRenderer.render(layer.getGuis());
            DTextRenderer.render(layer.getTextMap());
            layer.clear();
        }
    }

    public void cleanUp() {
        guiRenderer.cleanUp();
        DTextRenderer.cleanUp();
    }

    private void setUpLayers(final List<? extends D_Gui> guis) {
        if (guis == null) return;
        for (int i = 0; i < guis.size(); i++) {
            D_Gui gui = guis.get(i);
            Layer layer = layerStack.getLayer(gui.getLevel());
            if (layer == null) layerStack.push(layer = new Layer());
            layer.push(gui);
            if (gui.getIcons() != null) setUpLayers(gui.getIcons());
            if (gui.isFocused()) {
                if (gui.getQuads() != null) gui.getQuads().forEach(layerStack.getOverLay(1)::push);
            } else if (gui.getQuads() != null) setUpLayers(gui.getQuads());
            if (gui instanceof D_Container) setUpLayers(((D_Container) gui).getChildList());
        }
    }

    private void initGL() {
        glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

}
