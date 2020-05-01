package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Gui;
import engine.ui.gui.text.D_TextMaster;

public class MasterRenderer {
    private D_GuiRenderer guiRenderer;
    private D_TextRenderer DTextRenderer;

    private Window window;
    public MasterRenderer(Window window) { this.window = window; }

    public void init() {
        guiRenderer = new D_GuiRenderer(window);
        DTextRenderer = new D_TextRenderer(window);
    }

    public void render() {
        window.makeCurrent();
        guiRenderer.render();
        DTextRenderer.render(D_TextMaster.getTextMap(window));
    }

    public void cleanUp() {
        guiRenderer.cleanUp();
        DTextRenderer.cleanUp();
    }
}
