package engine.ui.gui.renderer.shader;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.Style;
import engine.ui.utils.colors.Color;
import engine.ui.utils.Maths;

public class D_GuiShader extends ShaderProgram{

    public D_GuiShader() {
        super("dGuiShader.glsl");
        loadLocations(
                "transformationMatrix",
                "prop.fillColor",
                "prop.strokeColor",
                "prop.strokeSize"
        );

    }


    public void loadComponent(D_Gui component) {
        loadProperties(component.getStyle());
        loadMat4("transformationMatrix",Maths.createModeMatrix(component.getStyle()));
    }

    private void loadProperties( Style p) {
        //load fill color
        Color fillColor = p.getColor();
        loadVec4f("prop.fillColor", fillColor.r(), fillColor.g(), fillColor.b(), fillColor.a());

        //load stroke color
        Color strokeColor = p.getBorderColor();
        loadVec4f("prop.strokeColor", strokeColor.r(), strokeColor.g(), strokeColor.b(), strokeColor.a());

        //load stroke size
        loadVec2f("prop.strokeSize",2.0f * p.getBorderWidth()/p.getWidth(),2.0f * p.getBorderWidth()/p.getHeight());
    }



}
