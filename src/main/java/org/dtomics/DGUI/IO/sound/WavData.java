package org.dtomics.DGUI.IO.sound;

import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO8;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO8;

public final class WavData {

    private final AudioInputStream audioStream;
    private final int format;
    private final ByteBuffer buffer;
    private final int sampleRate;
    private final int bytesPerFrame;
    private final int totalBytes;

    private WavData(AudioInputStream stream) {
        this.audioStream = stream;
        AudioFormat format = stream.getFormat();
        this.sampleRate = (int) format.getSampleRate();
        this.format = getOpenAlFormat(format.getChannels(), format.getSampleSizeInBits());
        this.bytesPerFrame = format.getFrameSize();
        this.totalBytes = (int) (bytesPerFrame * stream.getFrameLength());
        this.buffer = BufferUtils.createByteBuffer(totalBytes);
        loadData();
    }

    public static WavData create(String file) {
        File f = new File(file);
        AudioInputStream audioStream = null;

        try {
            if (f.exists()) {
                audioStream = AudioSystem.getAudioInputStream(f);
            } else {
                InputStream in = WavData.class.getResourceAsStream(file);
                if (in == null) {
                    System.err.println("could'nt find audio file : " + file);
                    return null;
                }
                audioStream = AudioSystem.getAudioInputStream(in);
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("error loading audio file : ");
        }
        assert audioStream != null;
        return new WavData(audioStream);
    }

    public int getFormat() {
        return format;
    }

    public ByteBuffer getData() {
        return buffer;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    private void loadData() {
        byte[] data = new byte[totalBytes];
        try {
            int bytesRead = audioStream.read(data, 0, totalBytes);
            buffer.clear();
            buffer.put(data, 0, bytesRead);
            buffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading audio file : ");
        }
    }

    private int getOpenAlFormat(int channels, int bitsPerSample) {
        if (channels == 1) {
            return bitsPerSample == 8 ? AL_FORMAT_MONO8 : AL_FORMAT_MONO16;
        } else {
            return bitsPerSample == 8 ? AL_FORMAT_STEREO8 : AL_FORMAT_STEREO16;
        }
    }
}
