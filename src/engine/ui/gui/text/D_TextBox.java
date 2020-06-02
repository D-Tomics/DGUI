package engine.ui.gui.text;

import engine.ui.IO.Window;
import engine.ui.gui.renderer.Loader;
import engine.ui.gui.text.font.Font;
import engine.ui.gui.text.font.Fonts;
import engine.ui.gui.text.meshCreator.Line;
import engine.ui.gui.text.meshCreator.TextMesh;
import engine.ui.gui.text.meshCreator.TextMeshCreator;
import engine.ui.gui.text.meshCreator.TextMeshData;
import engine.ui.utils.colors.Color;
import org.joml.Vector2f;

import java.util.List;

/**
 * This class represents a text in the application.
 * This creates a text inside a box. The box size and position can specified externally.
 * Beyond this box the text wont be rendered or discarded in the shader.
 *
 * If {@code wrapText} is enabled then the text beyond the box width is placed on a new line.
 *
 * @author Abdul Kareem
 */
public class D_TextBox {

    private static final Fonts DEFAULT = Fonts.Agency_FB;

    private boolean wrapText;

    private float boxWidth;
    private float boxHeight;

    private Window window;

    private Font font;
    private String text;
    private TextMesh mesh;
    private TextMeshData meshData;
    private Vector2f position;
    private Vector2f offset;

    private Color textColor = new Color(Color.WHITE);
    private Color borderColor = new Color(Color.BLACK);

    private boolean update;
    private boolean centered;
    private boolean visible = true;

    private float fontSize;
    private float charWidth;
    private float charEdge;
    private float charBorderWidth;
    private float charBorderEdge;

    public D_TextBox(String text, float fontSize, float boxWidth, float boxHeight) {
        this(text, fontSize, boxWidth, boxHeight, DEFAULT.getFont(Window.INSTANCE.getLoader()), false);
    }

    public D_TextBox(String text, float fontSize, float boxWidth, float boxHeight, boolean centered) {
        this(text, fontSize, boxWidth, boxHeight, DEFAULT.getFont(Window.INSTANCE.getLoader()), centered);
    }


    /**
     * @param text      the text that needs to be displayed
     * @param fontSize  font size of the text
     * @param boxWidth  width of the box in which the text needs to be displayed, beyond this width the text will not be rendered
     * @param boxHeight height of the box in which the text needs to be displayed, beyond this height the text will not be rendered
     * @param font      font type of the text
     * @param centered  this represents whether the text need to be centered in the box horizontally
     */
    public D_TextBox(String text, float fontSize, float boxWidth, float boxHeight, Font font, boolean centered) {
        this.text = text;
        this.position = new Vector2f(0);
        this.offset = new Vector2f(0);
        this.fontSize = fontSize;
        this.charWidth = 0.45f;
        this.charEdge = 0.19f;
        this.wrapText = false;
        this.window = Window.INSTANCE;

        this.font = font;
        this.centered = centered;

        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;

        this.meshData = TextMeshCreator.createTextMesh(window,this);
        D_TextMaster.load(window, this);
    }

    public boolean isCentered() { return centered; }
    public boolean isVisible() { return  visible; }
    public boolean isWrapped() { return wrapText; }

    public String getText() { return text; }
    public Font getFont() { return font; }
    public TextMesh getMesh() { return mesh; }
    public TextMeshData getMeshData() { return meshData; }

    public float getMaxTextWidth() { return meshData.getMaxTextWidth(); }
    public float getMaxTextHeight() { return meshData.getMaxTextHeight(); }
    public float getBoxWidth() { return boxWidth; }
    public float getBoxHeight() { return boxHeight; }
    public float getFontSize() { return fontSize; }

    public float getCharWidth() { return charWidth; }
    public float getCharEdge() { return charEdge; }
    public float getCharBorderWidth() { return charBorderWidth; }
    public float getCharBorderEdge() { return charBorderEdge; }

    public Color getBorderColor() { return borderColor; }
    public Color getTextColor() { return textColor; }
    public Vector2f getPosition() { return position; }
    public Vector2f getOffset() { return offset; }

    public int getNumOfLines() { return getLines().size(); }
    public int getLineLength(int lineNum) { return getLine(lineNum) != null ? getLine(lineNum).length() : 0; }
    public float getLineHeight() { return meshData != null ? meshData.getLineHeight() : 0; }
    public Line getLine(int lineNum) { return meshData != null ? meshData.getLine(lineNum) : null; }
    public List<Line> getLines() { return meshData != null ? meshData.getLines() : null; }

    /** this method makes the text wrap inside the box.
     * i.e it places the texts beyond the box width in a new line.
     *
     * @param wrapText whether to place text beyond box width in a new line.
     */
    public void setWrapText(boolean wrapText) {
        if(this.wrapText == wrapText) return;
        this.wrapText = wrapText;
        requestUpdate();
    }

    public void setText(String text) {
        if(this.text.equals(text)) return;
        this.text = text;
        requestUpdate();
    }

    public void setBoxWidth(float boxWidth ) {
        if(this.boxWidth == boxWidth) return;
        this.boxWidth = boxWidth;
        requestUpdate();
    }

    public void setBoxHeight(float boxHeight) {
        if(this.boxHeight == boxHeight) return;
        this.boxHeight = boxHeight;
        requestUpdate();
    }

    public void setBoxSize(float boxWidth, float boxHeight) {
        if(this.boxWidth == boxWidth && this.boxHeight == boxHeight) return;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        requestUpdate();
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
        requestUpdate();
    }

    public void setCharWidth(float charWidth) { this.charWidth = charWidth; }
    public void setCharEdge(float charEdge) { this.charEdge = charEdge; }
    public void setCharBorderWidth(float width) { this.charBorderWidth = width; }
    public void setCharBorderEdge(float edge) { this.charBorderEdge = edge; }

    public void setAlpha(float alpha) { this.textColor.a(alpha); }

    public void setTextColor(Color textColor) { this.textColor.set(textColor); }
    public void setTextColor(float r, float g, float b) { this.textColor.set(r,g,b); }

    public void setBorderColor(Color borderColor) { this.borderColor.set(borderColor); }
    public void setBorderColor(float r, float g, float b) { this.borderColor.set(r,g,b); }

    public void setOffset(Vector2f offset) { this.offset.set(offset); }

    /** This method sets the offset from the box position that the text gets rendered
     *
     * @param x x offset position from the box position the text gets rendered
     * @param y y offset position from the box position the text gets rendered
     */
    public void setOffset(float x, float y) { this.offset.set(x,y); }

    public void setPosition(Vector2f position) { this.position.set(position); }
    public void setPosition(float x, float y) { position.set(x, y); }

    public void setVisible(boolean visible) { this.visible = visible; }
    public void setCentered(boolean centered) {
        if(this.centered == centered) return;
        this.centered = centered;
        requestUpdate();
    }

    public void setFont(Font font) {
        if(this.font == font ) return;

        D_TextMaster.remove(this);
        this.font = font;
        D_TextMaster.load(window,this);

        requestUpdate();
    }

    void requestUpdate() {
        meshData = TextMeshCreator.createTextMesh(window,this);
        update = true;
    }

    public void update(Loader loader) {
        if(mesh == null)
            mesh = loader.loadText(meshData,Loader.STREAM);
        if(update) {
            mesh.updateData(meshData,loader);
            update = false;
        }
    }

    @Override
    public String toString() {
        String str = "";
        for(char c : text.toCharArray())
        {
            if(c == '\n')
            {
                str += "\\n";
                continue;
            }
            str += c;
        }
        return str;
    }
}
