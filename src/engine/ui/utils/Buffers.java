package engine.ui.utils;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffers {

    public static FloatBuffer createFloatBuffer(float[] data) { return createFloatBuffer(data.length, data); }
    public static DoubleBuffer createDoubleBuffer(double[] data) { return createDoubleBuffer(data.length, data); }
    public static IntBuffer createIntBuffer(int[] data) { return createIntBuffer(data.length, data); }
    public static ByteBuffer createByteBuffer(byte[] data) { return createByteBuffer(data.length, data); }

    public static FloatBuffer createFloatBuffer(int size, float... data) { return BufferUtils.createFloatBuffer(size).put(data).flip(); }
    public static DoubleBuffer createDoubleBuffer(int size, double... data) { return BufferUtils.createDoubleBuffer(size).put(data).flip(); }
    public static IntBuffer createIntBuffer(int size, int... data) { return BufferUtils.createIntBuffer(size).put(data).flip(); }
    public static ByteBuffer createByteBuffer(int size, byte...data) { return BufferUtils.createByteBuffer(size).put(data).flip(); }

}
