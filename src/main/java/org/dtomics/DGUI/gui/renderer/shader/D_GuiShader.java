package org.dtomics.DGUI.gui.renderer.shader;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.Style;
import org.dtomics.DGUI.utils.Maths;
import org.dtomics.DGUI.utils.colors.Color;

/**
 * This class helps to manage the gui shader.
 *
 * @author Abdul Kareem
 */
public class D_GuiShader extends ShaderProgram {

    private final Window window;

    public D_GuiShader(Window window) {
        super("shaders/dGuiShader.glsl");
        super.start();
        super.loadInt("bgTexture", 0);
        super.stop();
        this.window = window;
    }


    public void loadComponent(D_Gui component) {
        loadProperties(component.getStyle());
        loadMat4("transformationMatrix", Maths.createModeMatrix(component.getStyle(), window));
    }

    private void loadProperties(Style p) {
        //load fill color
        Color fillColor = p.getBgColor();
        loadVec4f("prop.fillColor", fillColor.r(), fillColor.g(), fillColor.b(), fillColor.a());

        //load stroke color
        Color strokeColor = p.getBorderColor();
        loadVec4f("prop.strokeColor", strokeColor.r(), strokeColor.g(), strokeColor.b(), strokeColor.a());

        //load stroke size
        loadFloat("prop.borderWidth", p.getBorderWidth());
        //loadVec2f("prop.strokeSize",2.0f * p.getBorderWidth()/p.getWidth(),2.0f * p.getBorderWidth()/p.getHeight());

        loadFloat("prop.radius", p.getCornerRadius());
        loadVec2f("prop.dimensions", p.getSize());
    }


}
