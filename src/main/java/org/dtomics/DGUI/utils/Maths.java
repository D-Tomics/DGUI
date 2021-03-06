package org.dtomics.DGUI.utils;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Component;
import org.dtomics.DGUI.gui.manager.Style;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Maths {

    private static final Matrix4f modelMatrix = new Matrix4f();

    public static Matrix4f createModelMatrix(float x, float y, float sx, float sy) {
        modelMatrix.identity();
        modelMatrix.translate(x, y, 0);
        modelMatrix.scale(sx, sy, 0);
        return modelMatrix;
    }

    public static Matrix4f createModelMatrix(float x, float y, float sx, float sy, boolean normalize, Window window) {
        if (normalize) {
            return createModelMatrix(2 * x / window.getWidth(), 2 * y / window.getHeight(), sx / window.getWidth(), sy / window.getHeight());
        }
        return createModelMatrix(x, y, sx, sy);
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
        return Math.abs(pointX - x) < width / 2.0f && Math.abs(pointY - y) < height / 2.0f;
    }

    public static boolean checkPointCollision(float x, float y, D_Component component) {
        Style p = component.getStyle();
        return Math.abs(x - p.getCenterX()) < p.getWidth() / 2.0f && Math.abs(y - p.getCenterY()) < p.getHeight() / 2.0f;
    }

    public static boolean checkPointCollision(Vector2f point, D_Component component) {
        return checkPointCollision(point.x, point.y, component);
    }

    public static float round(float val, int digits) {
        int mult = (int) Math.pow(10, digits);
        return (float) (Math.floor(val * mult) / mult);
    }

    public static float map(float val, float min1, float max1, float min2, float max2) {
        return ((val - min1) / (max1 - min1)) * (max2 - min2) + min2;
    }

    public static BigDecimal map(BigDecimal val, BigDecimal min1, BigDecimal max1, BigDecimal min2, BigDecimal max2) {
        int scale = max(val.scale(), min1.scale(), max1.scale(), min2.scale(), max2.scale());
        return val.subtract(min1).divide(max1.subtract(min1), RoundingMode.FLOOR).multiply(max2.subtract(min2)).add(min2);
    }

    public static int fastFloor(double x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int min(int...values) {
        int min = values[0];
        for(int i = 0; i < values.length; i++) {
            if(values[i] < min)
                min = values[i];
        }
        return min;
    }

    public static int max(int...values) {
        int max = values[0];
        for(int i = 1; i < values.length; i++) {
            if(values[i] > max)
                max = values[i];
        }
        return max;
    }
}
