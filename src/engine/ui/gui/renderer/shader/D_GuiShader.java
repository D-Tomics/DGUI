package engine.ui.gui.renderer.shader;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.Style;
import engine.ui.utils.Maths;
import engine.ui.utils.colors.Color;

public class D_GuiShader extends ShaderProgram{

    public D_GuiShader() {
        super("dGuiShader.glsl");
    }


    public void loadComponent(D_Gui component) {
        loadProperties(component.getStyle());
        loadMat4("transformationMatrix",Maths.createModeMatrix(component.getStyle()));
    }

    private void loadProperties( Style p) {
        //load fill color
        Color fillColor = p.getColor();
        loadVec4f("prop.fillColor", fillColor.r, fillColor.g, fillColor.b, p.getAlpha());

        //load stroke color
        Color strokeColor = p.getBorderColor();
        loadVec4f("prop.strokeColor", strokeColor.r, strokeColor.g, strokeColor.b, p.getAlpha());

        //load stroke size
        loadFloat("prop.borderWidth", p.getBorderWidth());
        //loadVec2f("prop.strokeSize",2.0f * p.getBorderWidth()/p.getWidth(),2.0f * p.getBorderWidth()/p.getHeight());

        loadFloat("prop.radius", p.getCornerRadius());
        loadVec2f("prop.dimensions", p.getSize());
    }



}
