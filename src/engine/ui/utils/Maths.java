package engine.ui.utils;

import engine.ui.IO.Window;
import engine.ui.gui.components.D_Component;
import engine.ui.gui.manager.Style;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Maths {

    private static Matrix4f modelMatrix = new Matrix4f();
    public static Matrix4f createModelMatrix(float x, float y, float sx, float sy)  {
        modelMatrix.identity();
        modelMatrix.translate(x,y,0);
        modelMatrix.scale(sx,sy,0);
        return modelMatrix;
    }

    public static Matrix4f createModeMatrix(Style properties) {
        return createModelMatrix(
                2 * properties.getCenterX() / Window.INSTANCE.getWidth(),
                2 * properties.getCenterY() / Window.INSTANCE.getHeight(),
                (properties.getWidth() + 2 * properties.getBorderWidth()) / Window.INSTANCE.getWidth(),
                (properties.getHeight() + 2 * properties.getBorderWidth()) / Window.INSTANCE.getHeight()
        );
    }

    public static boolean checkPointCollision(float pointX, float pointY, float x, float y, float width, float height) {
        return Math.abs(pointX - x) < width/2.0f && Math.abs(pointY - y) < height/2.0f;
    }

    public static boolean checkPointCollision(float x, float y, D_Component component) {
        Style p = component.getStyle();
        return Math.abs(x - p.getCenterX()) < p.getWidth()/2.0f && Math.abs(y - p.getCenterY()) < p.getHeight()/2.0f;
    }

    public static boolean checkPointCollision(Vector2f point, D_Component component) {
        return checkPointCollision(point.x,point.y,component);
    }

    public static float round(float val,int digits) {
        int mult = (int) Math.pow(10,digits);
        return (float) (Math.floor(val * mult)/ mult);
    }

    public static float map(float val, float min1, float max1, float min2, float max2) {
        return ((val - min1)/(max1 - min1)) * (max2 - min2);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max,val));
    }

}
