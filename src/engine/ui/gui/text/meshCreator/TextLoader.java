package engine.ui.gui.text.meshCreator;

import engine.ui.gui.renderer.Loader;
import engine.ui.gui.text.D_TextBox;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public final class TextLoader extends Loader {

    private static List<Integer> vaos = new ArrayList<>();
    private static List<Integer> vbos = new ArrayList<>();

    public static TextMesh loadText(D_TextBox text, int usage) {
        TextMeshData meshData = TextMeshCreator.createTextMesh(text);
        int vao = createVao();
        int vbo = createBuffer(meshData.getData(),usage);
        setAttribPointer(0,2,4 * Float.BYTES,0);
        setAttribPointer(1,2, 4 * Float.BYTES,2 * Float.BYTES);
        unbind();
        return new TextMesh(vao, vbo, meshData);
    }

    public static void cleanUp() {
        for(int vbo : vbos)
            GL15.glDeleteBuffers(vbo);
        for(int vao : vaos)
            GL30.glDeleteVertexArrays(vao);
    }

}
