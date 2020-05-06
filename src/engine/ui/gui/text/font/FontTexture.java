package engine.ui.gui.text.font;

import engine.ui.utils.Buffers;
import engine.ui.utils.Utils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.stb.STBImage.*;

public class FontTexture {

    private int id;
    private int width;
    private int height;

    public FontTexture(String path) {
        try {
            this.id = load(path);
        } catch (IOException e) {
            System.out.println("error loading font image");
            e.printStackTrace();
        }
    }

    public void bind(int unit) {
        if(unit > 32) return;
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    private int load(String path) throws IOException {
        int[] w = {1}, h = {1}, c = {1};
        InputStream in = this.getClass().getResourceAsStream(path);
        ArrayList<Byte> list = new ArrayList<>();
        byte[] read = new byte[1024];
        int readBytes = 0;
        while((readBytes = in.read(read)) != -1) for(int i = 0; i < readBytes; i++) list.add(read[i]);
        byte[] imageData = new byte[list.size()];
        for (int i = 0; i < imageData.length; i++) imageData[i] = list.get(i);
        //for jdk 9+ image data = in.readAllBytes();
        ByteBuffer imageBuffer = Buffers.createByteBuffer(imageData);
        ByteBuffer data = stbi_load_from_memory(imageBuffer, w, h, c, 4);
        if(data == null) {
            System.err.println("::ERR::could'nt read texture from "+path);
            return -1;
        }
        return generateTexture(w[0], h[0], data);
    }


    private int generateTexture(int width, int height, ByteBuffer data) {
        int id = GL11.glGenTextures();
        this.width = width;
        this.height = height;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);

        stbi_image_free(data);
        return id;
    }
}
