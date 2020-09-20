package org.dtomics.DGUI.utils;

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

    public static FloatBuffer createFloatBuffer(int size, float... data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(size);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static DoubleBuffer createDoubleBuffer(int size, double... data) {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(size);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static IntBuffer createIntBuffer(int size, int... data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(size);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static ByteBuffer createByteBuffer(int size, byte...data) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(size);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
