package org.dtomics.DGUI.utils;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Component;
import org.dtomics.DGUI.gui.manager.Style;
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

    public static Matrix4f createModelMatrix(float x, float y, float sx, float sy, boolean normalize, Window window) {
        if(normalize) {
            return createModelMatrix(2 * x / window.getWidth(),2 * y / window.getHeight(), sx / window.getWidth(), sy / window.getHeight());
        }
        return createModelMatrix( x, y, sx,sy);
    }

    public static Matrix4f createModeMatrix(Style properties, Window window) {
        return createModelMatrix(
                2 * properties.getCenterX() / window.getWidth(),
                2 * properties.getCenterY() / window.getHeight(),
                properties.getWidth() / window.getWidth(),
                properties.getHeight() / window.getHeight()
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
        return ((val - min1) / (max1 - min1)) * (max2 - min2) + min2;
    }

    public static int fastFloor(double x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max,val));
    }

}
