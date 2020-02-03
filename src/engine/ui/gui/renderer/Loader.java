package engine.ui.gui.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class Loader {

    public static final int STATIC = GL15.GL_STATIC_DRAW;
    public static final int DYNAMIC = GL15.GL_DYNAMIC_DRAW;
    public static final int STREAM = GL15.GL_STREAM_DRAW;

    private static List<Integer> vaos = new ArrayList<>();
    private static List<Integer> vbos = new ArrayList<>();

    public static int load2dVertexData(float[] data) {
        int vao = createVao();
        createBuffer(data, STATIC);
        setAttribPointer(0, 2, 0, 0);
        unbind();
        return vao;
    }

    public static void cleanUp() {
        for(int vbo : vbos)
            GL15.glDeleteBuffers(vbo);
        for(int vao : vaos)
            GL30.glDeleteVertexArrays(vao);
    }

    public static void updateBuffer(int buffer, float[] data, int usage) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.length * Float.BYTES ,usage);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,0,data);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }

    protected static int createVao() {
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        vaos.add(vao);
        return vao;
    }

    protected static int createBuffer(float[] data,int usage) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,data,usage);
        vbos.add(vbo);
        return vbo;
    }

    protected static void setAttribPointer(int index, int size, int length, int pointer) {
        GL20.glVertexAttribPointer(index,size, GL11.GL_FLOAT,false,length,pointer);
        GL20.glEnableVertexAttribArray(index);
    }

    protected static void unbind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL30.glBindVertexArray(0);
    }

}
