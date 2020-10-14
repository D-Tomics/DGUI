package org.dtomics.DGUI.gui.text;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.renderer.Loader;
import org.dtomics.DGUI.gui.text.font.Font;
import org.dtomics.DGUI.gui.text.font.Fonts;
import org.dtomics.DGUI.gui.text.meshCreator.Line;
import org.dtomics.DGUI.gui.text.meshCreator.TextAlignment;
import org.dtomics.DGUI.gui.text.meshCreator.TextMesh;
import org.dtomics.DGUI.gui.text.meshCreator.TextMeshCreator;
import org.dtomics.DGUI.gui.text.meshCreator.TextMeshData;
import org.dtomics.DGUI.utils.colors.Color;
import org.joml.Vector2f;

import java.util.List;

/**
 * This class represents a text in the application.
 * This creates a text inside a box. The box size and position can specified externally.
 * Beyond this box the text wont be rendered or discarded in the shader.
 * <p>
 * If {@code wrapText} is enabled then the text beyond the box width is placed on a new line.
 *
 * @author Abdul Kareem
 */
@Getter
@ToString
public class D_TextBox {

    private static final Fonts DEFAULT = Fonts.Agency_FB;
    private final Vector2f position;
    private final Vector2f offset;
    private final Color textColor = new Color(Color.WHITE);
    private final Color borderColor = new Color(Color.BLACK);
    private final Window window;
    private float boxWidth;
    private float boxHeight;
    private Font font;
    private String text;
    private TextMesh mesh;
    private TextMeshData meshData;
    private TextAlignment textAlignment;
    private boolean wrapped;
    private boolean update;
    @Setter
    private boolean visible = true;

    private float fontSize;
    private float lineSpacing;
    @Setter
    private float charWidth;
    @Setter
    private float charEdge;
    @Setter
    private float charBorderWidth;
    @Setter
    private float charBorderEdge;

    public D_TextBox(String text, float fontSize, float boxWidth, float boxHeight) {
        this(text, fontSize, boxWidth, boxHeight, DEFAULT.getFont(Window.get().getLoader()), TextAlignment.TOP_LEFT);
    }

    public D_TextBox(String text, float fontSize, float boxWidth, float boxHeight, TextAlignment alignment) {
        this(text, fontSize, boxWidth, boxHeight, DEFAULT.getFont(Window.get().getLoader()), alignment);
    }


    /**
     * @param text      the text that needs to be displayed
     * @param fontSize  font size of the text
     * @param boxWidth  width of the box in which the text needs to be displayed, beyond this width the text will not be rendered
     * @param boxHeight height of the box in which the text needs to be displayed, beyond this height the text will not be rendered
     * @param font      font type of the text
     * @param alignment this represents how the text mesh should be aligned w.r.t the text box
     */
    public D_TextBox(String text, float fontSize, float boxWidth, float boxHeight, Font font, TextAlignment alignment) {
        this.text = text;
        this.position = new Vector2f(0);
        this.offset = new Vector2f(0);
        this.fontSize = fontSize;
        this.charWidth = 0.45f;
        this.charEdge = 0.19f;
        this.wrapped = false;
        this.window = Window.get();

        this.font = font;

        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.lineSpacing = 5;

        this.textAlignment = alignment;
        this.meshData = TextMeshCreator.createTextMesh(window, this);
    }

    public float getMaxTextWidth() {
        return meshData.getMaxTextWidth();
    }

    public float getMaxTextHeight() {
        return meshData.getMaxTextHeight();
    }

    public int getNumOfLines() {
        return getLines().size();
    }

    public int getLineLength(int lineNum) {
        return getLine(lineNum) != null ? getLine(lineNum).length() : 0;
    }

    public float getLineHeight() {
        return meshData != null ? meshData.getLineHeight() : 0;
    }

    public Line getLine(int lineNum) {
        return meshData != null ? meshData.getLine(lineNum) : null;
    }

    public List<Line> getLines() {
        return meshData != null ? meshData.getLines() : null;
    }

    /**
     * this method makes the text wrap inside the box.
     * i.e it places the texts beyond the box width in a new line.
     *
     * @param wrapped whether to place text beyond box width in a new line.
     */
    public void setWrapped(boolean wrapped) {
        if (this.wrapped == wrapped) return;
        this.wrapped = wrapped;
        requestUpdate();
    }

    public void setText(String text) {
        if (this.text.equals(text)) return;
        this.text = text;
        requestUpdate();
    }

    public void appendText(String text) {
        this.text = this.text.concat(text);
        requestUpdate();
    }

    public void setBoxWidth(float boxWidth) {
        if (this.boxWidth == boxWidth) return;
        this.boxWidth = boxWidth;
        requestUpdate();
    }

    public void setBoxHeight(float boxHeight) {
        if (this.boxHeight == boxHeight) return;
        this.boxHeight = boxHeight;
        requestUpdate();
    }

    public void setBoxSize(float boxWidth, float boxHeight) {
        if (this.boxWidth == boxWidth && this.boxHeight == boxHeight) return;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        requestUpdate();
    }

    public void setFontSize(float fontSize) {
        if (this.fontSize == fontSize) return;
        this.fontSize = fontSize;
        requestUpdate();
    }

    public void setLineSpacing(float lineSpacing) {
        if (this.lineSpacing == lineSpacing) return;
        this.lineSpacing = lineSpacing;
        requestUpdate();
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        if (this.textAlignment == textAlignment) return;
        this.textAlignment = textAlignment;
        requestUpdate();
    }

    public void setFont(Font font) {
        if (this.font == font) return;

        D_TextMaster.update(window, this.font, font, this);
        this.font = font;

        requestUpdate();
    }

    public void setTextColor(Color textColor) {
        this.textColor.set(textColor);
    }

    public void setTextColor(float r, float g, float b) {
        this.textColor.set(r, g, b);
    }

    public void setTextColor(float r, float g, float b, float a) {
        this.textColor.set(r, g, b, a);
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor.set(borderColor);
    }

    public void setBorderColor(float r, float g, float b) {
        this.borderColor.set(r, g, b);
    }

    public void setBorderColor(float r, float g, float b, float a) {
        this.borderColor.set(r, g, b, a);
    }

    /**
     * This method sets the offset from the box position that the text gets rendered
     *
     * @param x x offset position from the box position the text gets rendered
     * @param y y offset position from the box position the text gets rendered
     */
    public void setOffset(float x, float y) {
        this.offset.set(x, y);
    }

    public void setOffset(Vector2f offset) {
        this.offset.set(offset);
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    void requestUpdate() {
        meshData = TextMeshCreator.createTextMesh(window, this);
        update = true;
    }

    public void update(Loader loader) {
        if (mesh == null)
            mesh = loader.loadText(meshData, Loader.STREAM);
        if (update) {
            mesh.updateData(meshData, loader);
            update = false;
        }
    }
}
