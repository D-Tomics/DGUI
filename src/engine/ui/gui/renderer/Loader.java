package engine.ui.gui.renderer;

import engine.ui.IO.Window;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.meshCreator.TextMesh;
import engine.ui.gui.text.meshCreator.TextMeshCreator;
import engine.ui.gui.text.meshCreator.TextMeshData;
import engine.ui.utils.Buffers;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.stb.STBImage.*;

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
    private List<Integer> textures = new ArrayList<>();

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

    /**This method loads a texture from memory to vram
     *
     * @param path         the path to which the texture exists
     * @param flipVertical whether to flip the texture vertically
     * @return             returns the instance of Texture class
     */
    public Texture loadTexture(String path, boolean flipVertical) {
        int[] w = {1}, h = {1}, c = {1};
        InputStream in = getTextureInputStream(path);
        if(in == null)
            return new Texture(-1,0,0);

        ByteBuffer imageBuffer = Buffers.createByteBuffer(getImageData(in));
        stbi_set_flip_vertically_on_load(flipVertical);
        ByteBuffer data = stbi_load_from_memory(imageBuffer, w, h, c, 4);
        if(data == null) {
            System.err.println(" error loading texture from "+path);
            return new Texture(0,0,0);
        }
        return generateTexture(w[0], h[0], data);
    }

    /**This method generates a texture into vram with specified width height and data
     *
     * @param width     width of texture to be generated
     * @param height    height of texture to be generated
     * @param data      the color data of each pixel in the texture
     * @return          returns instance of Texture class
     */
    public Texture generateTexture(int width, int height, ByteBuffer data) {
        int id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);

        stbi_image_free(data);
        return new Texture(id,width,height);
    }

    /**This method deletes all vaos, vbos and textures that are loaded
     */
    public void cleanUp() {
        vbos.forEach(GL15::glDeleteBuffers);
        vaos.forEach(GL30::glDeleteVertexArrays);
        textures.forEach(GL11::glDeleteTextures);
    }

    /**This method updates the data of a buffer
     *
     * @param buffer opengl buffer id whose data needs to be updated
     * @param data   new data to be stored in the buffer
     * @param usage  the operation that this buffer is used for
     */
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

    //texture stuff

    private InputStream getTextureInputStream(String path) {
        InputStream in = this.getClass().getResourceAsStream(path);
        if(in == null) {
            try {
                File file = new File(path);
                if(!file.exists()) {
                    System.err.println("texture " + path+" does'nt exist");
                    return null;
                }
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return in;
    }

    private byte[] getImageData(InputStream in) {
        int readBytes = 0;
        byte[] read = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //for jdk 9+ image data = in.readAllBytes();
        try {
            while((readBytes = in.read(read)) != -1)
                os.write(read,0,readBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

}
