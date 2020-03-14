package engine.ui.IO.sound;

import engine.ui.IO.Window;

import static org.lwjgl.openal.AL10.*;

public final class SoundBuffer {

    private final int bufferId;

    public SoundBuffer(String file) {
        WavData wavData = WavData.create(file);
        bufferId = alGenBuffers();
        assert wavData != null;
        alBufferData(bufferId, wavData.getFormat(), wavData.getData(), wavData.getSampleRate());
        SoundManager.get().add(this);
    }

    int getBufferId() {
        return bufferId;
    }

    public void delete() {
        alDeleteBuffers(bufferId);
    }


}
