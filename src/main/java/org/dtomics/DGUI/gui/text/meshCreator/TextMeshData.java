package org.dtomics.DGUI.gui.text.meshCreator;

import java.util.List;

/**
 * This Java Bean holds all the data related to a text mesh
 *
 * @author Abdul Kareem
 */
public class TextMeshData {

    private float[] data;
    private int vertexCount;

    private float lineHeight;
    private float maxTextWidth;
    private float maxTextHeight;

    private List<Line> lines;

    /**This constructor is only called in the text mesh creator
     *
     * @param data        The vertex data of the mesh
     * @param vertexCount The number of vertices in the mesh
     * @param lines       This represents the structure of the text
     * @param width       This is the max width occupied by the mesh in the window
     * @param height      This is the max height occupied by the mesh in the window
     */
    TextMeshData( float[] data, int vertexCount, List<Line> lines, float width, float height) {
        this.data = data;
        this.vertexCount = vertexCount;
        this.lines = lines;
        this.maxTextWidth = width;
        this.maxTextHeight = height;
        this.lineHeight = maxTextHeight / lines.size();
    }

    public float[] getData() {
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
