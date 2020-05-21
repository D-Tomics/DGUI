package engine.ui.gui.text.meshCreator;

import engine.ui.gui.renderer.Loader;
import engine.ui.gui.text.D_TextBox;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**Acts as a wrapper around vao and vbo that holds the text mesh data.
 *
 * @author Abdul Kareem
 *
 */
public final class TextMesh {

    private int vao;
    private int buffer;

    public TextMesh(int vao, int buffer) {
        this.vao = vao;
        this.buffer = buffer;
    }

    public void bind() {
        GL30.glBindVertexArray(vao);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void updateData(TextMeshData meshData,Loader loader) {
        loader.updateBuffer(buffer,meshData.getData(), Loader.STREAM);
    }

    public int getVao() {
        return vao;
    }

    public int getBuffer() {
        return buffer;
    }

}
