package engine.ui.gui.renderer.shader;

import engine.ui.IO.Window;
import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.Maths;

/**
 * This class helps to manage the text shader
 *
 * @author Abdul Kareem
 */
public final class D_TextShader extends ShaderProgram {

    private Window window;
    public D_TextShader(Window window) {
        super("dTextShader.glsl");
        this.window = window;
        loadLocations(
                "texAtlas",
                "model",
                "boxScale",
                "centered",
                "textColor",
                "width",
                "edge",
                "borderColor",
                "borderEdge",
                "borderWidth",
                "alpha"
        );
    }

    public void load(D_TextBox text) {

        super.loadInt("texAtlas",0);
        super.loadVec3f("textColor", text.getTextColor().r(), text.getTextColor().g(), text.getTextColor().b());
        super.loadFloat("alpha", text.getTextColor().a());

        super.loadFloat("edge",text.getCharEdge());
        super.loadFloat("width",text.getCharWidth());

        super.loadVec3f("borderColor", text.getBorderColor().r(), text.getBorderColor().g(), text.getBorderColor().b());
        super.loadFloat("borderWidth", text.getCharBorderWidth());
        super.loadFloat("borderEdge", text.getCharBorderEdge());

        super.loadVec2f(
                "offset",
                2 * text.getOffset().x / window.getWidth(),
                2 * text.getOffset().y / window.getHeight()
        );

        super.loadVec2f(
                "boxScale",
                text.getBoxWidth() / window.getWidth(),
                text.getBoxHeight() / window.getHeight()
        );

        super.loadMat4(
                "model",
                Maths.createModelMatrix(
                        text.getPosition().x + text.getOffset().x,
                        text.getPosition().y + text.getOffset().y,
                        text.getFontSize() * window.getAspectRatio(),
                        text.getFontSize(),
                        true,
                        window
                )
        );

    }

}
