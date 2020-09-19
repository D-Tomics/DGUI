package engine.ui.IO.sound;

import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public final class SoundBuffer {

    private final int bufferId;
    private int format;
    private int sampleRate;
    private int bytesPerSample;
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
        SoundManager.get().add(this);
    }

    public void set(ByteBuffer buffer, int format, int sampleRate) {
        this.buffer = buffer;
        this.format = format;
        this.sampleRate = sampleRate;
        this.bytesPerSample = getSampleSizeInBytes();
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

    public int getBytesPerSample() {
        return bytesPerSample;
    }

    public void delete() {
        buffer.clear();
        alDeleteBuffers(bufferId);
    }

    private int getSampleSizeInBytes() {
        return format == AL_FORMAT_MONO8 || format == AL_FORMAT_STEREO8 ? 1 :
                format == AL_FORMAT_MONO16 || format == AL_FORMAT_STEREO16 ? 2 : 4;
    }

}
