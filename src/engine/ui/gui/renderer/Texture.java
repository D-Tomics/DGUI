package engine.ui.gui.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**The instance if this class represents a texture.
 * Instance of this class is created via <code>Loader</code> class
 *
 * @see engine.ui.gui.renderer.Loader;
 * @author Abdul Kareem
 */
public class Texture {

    private static Texture boundTexture;

    private int id;
    private int width;
    private int height;

    /**This constructor is only called in the loader class when <code>Loader.loadTexture</code> method is called
     *
     * @param id       an opengl object id for the texture
     * @param width    width of the texture
     * @param height   height of the texture
     */
    protected Texture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public void bind(int unit) {
        if(boundTexture == this) return;
        if(unit > 32) return;
        boundTexture = this;
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        if(boundTexture == this)
            boundTexture = null;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

}
