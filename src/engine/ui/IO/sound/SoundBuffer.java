package engine.ui.IO.sound;

import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public final class SoundBuffer {

    private final int bufferId;
    private int format;
    private int sampleRate;
    private ByteBuffer buffer;

    public SoundBuffer() {
        bufferId = alGenBuffers();
    }

    public SoundBuffer(String file) {
        WavData wavData = WavData.create(file);
        if(wavData == null)
            throw new IllegalStateException("null Wav data");

        bufferId = alGenBuffers();
        set(wavData.getData(), wavData.getFormat(), wavData.getSampleRate());
        SoundManager.get().add(this);
    }

    public SoundBuffer(ByteBuffer buffer, int format, int sampleRate) {
        bufferId = alGenBuffers();
        set(buffer, format, sampleRate);
        alBufferData(bufferId,format,buffer,sampleRate);
        SoundManager.get().add(this);
    }

    public void set(ByteBuffer buffer, int format, int sampleRate) {
        this.buffer = buffer;
        this.format = format;
        this.sampleRate = sampleRate;
        alBufferData(bufferId, format, buffer, sampleRate);
    }

    int getBufferId() {
        return bufferId;
    }

    public ByteBuffer getData() {
        return buffer;
    }

    public int getFormat() {
        return format;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void delete() {
        buffer.clear();
        alDeleteBuffers(bufferId);
    }


}
