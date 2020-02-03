package engine.ui.gui.text.meshCreator;

import engine.ui.gui.renderer.Loader;
import engine.ui.gui.text.D_TextBox;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class TextMesh {

    private int vao;
    private int buffer;
    private TextMeshData meshData;

    TextMesh(int vao, int buffer, TextMeshData meshData) {
        this.vao = vao;
        this.buffer = buffer;
        this.meshData = meshData;
    }

    public void bind() {
        GL30.glBindVertexArray(vao);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void updateData(D_TextBox text) {
        meshData = TextMeshCreator.createTextMesh(text);
        TextLoader.updateBuffer(buffer,meshData.getData(), Loader.STREAM);
    }

    public int getVao() {
        return vao;
    }

    public int getBuffer() {
        return buffer;
    }

    public TextMeshData getData() {
        return meshData;
    }
}
