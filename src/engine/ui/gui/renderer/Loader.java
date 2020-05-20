package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.meshCreator.TextMesh;
import engine.ui.gui.text.meshCreator.TextMeshCreator;
import engine.ui.gui.text.meshCreator.TextMeshData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

/**
 * This class helps to load vertex Array Objects and vertex Buffer objects with data and also allows
 * to update vbo.
 * There will be one instance of this class per Window
 *
 * @author Abdul Kareem
 */
public class Loader {

    public static final int STATIC = GL15.GL_STATIC_DRAW;
    public static final int DYNAMIC = GL15.GL_DYNAMIC_DRAW;
    public static final int STREAM = GL15.GL_STREAM_DRAW;

    private Window window;
    public Loader(Window window) { this.window = window; }

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public TextMesh loadText(TextMeshData meshData, int usage) {
        int vao = createVao();
        int vbo = createBuffer(meshData.getData(),usage);
        setAttribPointer(0,2,4 * Float.BYTES,0);
        setAttribPointer(1,2, 4 * Float.BYTES,2 * Float.BYTES);
        unbind();
        return new TextMesh(vao, vbo);
    }

    public int load2dVertexData(float[] data) {
        int vao = createVao();
        createBuffer(data, STATIC);
        setAttribPointer(0, 2, 0, 0);
        unbind();
        return vao;
    }

    public void cleanUp() {
        for(int vbo : vbos)
            GL15.glDeleteBuffers(vbo);
        for(int vao : vaos)
            GL30.glDeleteVertexArrays(vao);
    }

    public void updateBuffer(int buffer, float[] data, int usage) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.length * Float.BYTES ,usage);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,0,data);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }

    protected int createVao() {
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        vaos.add(vao);
        return vao;
    }

    protected int createBuffer(float[] data,int usage) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,data,usage);
        vbos.add(vbo);
        return vbo;
    }

    protected void setAttribPointer(int index, int size, int length, int pointer) {
        GL20.glVertexAttribPointer(index,size, GL11.GL_FLOAT,false,length,pointer);
        GL20.glEnableVertexAttribArray(index);
    }

    protected void unbind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL30.glBindVertexArray(0);
    }

}
