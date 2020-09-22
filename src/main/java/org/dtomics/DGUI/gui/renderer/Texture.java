package org.dtomics.DGUI.gui.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.glIsTexture;

/**The instance if this class represents a texture.
 * Instance of this class is created via <code>Loader</code> class
 *
 * @see org.dtomics.DGUI.gui.renderer.Loader;
 * @author Abdul Kareem
 */
public class Texture {

    private int id;
    private int width;
    private int height;
    /**This constructor is only called in the loader class when <code>Loader.loadTexture</code> method is called
     *
     * @param id       an opengl object id for the texture
     * @param width    width of the texture
     * @param height   height of the texture
     */
    public Texture(int id, int width, int height) {
        if(!glIsTexture(id))
            throw new IllegalArgumentException("invalid texture");

        this.id = id;
        this.width = width;
        this.height = height;
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

    public int getId() { return id; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

}
