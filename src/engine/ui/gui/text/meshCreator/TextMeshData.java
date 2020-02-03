package engine.ui.gui.text.meshCreator;

import java.util.List;

public class TextMeshData {

    private float[] data;
    private int vertexCount;

    private float lineHeight;
    private float maxTextWidth;
    private float maxTextHeight;

    private List<Line> lines;

    TextMeshData( float[] data, int vertexCount, List<Line> lines, float width, float height) {
        this.data = data;
        this.vertexCount = vertexCount;
        this.lines = lines;
        this.maxTextWidth = width;
        this.maxTextHeight = height;
        this.lineHeight = maxTextHeight / lines.size();
    }

    float[] getData() {
        return data;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public float getLineHeight() { return lineHeight; }
    public float getMaxTextWidth() {
        return maxTextWidth;
    }
    public float getMaxTextHeight() {
        return maxTextHeight;
    }

    public Line getLine(int index) { return index >= lines.size() || index < 0 ? null : lines.get(index); }
    public List<Line> getLines() { return lines; }
}
