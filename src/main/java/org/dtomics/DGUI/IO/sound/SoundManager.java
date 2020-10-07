package org.dtomics.DGUI.IO.sound;

import java.util.ArrayList;
import java.util.List;

public final class SoundManager {

    private final static List<SoundSource> soundSources = new ArrayList<>();
    private final static List<SoundBuffer> soundBuffers = new ArrayList<>();

    private static SoundManager instance;
    private boolean initialised;
    private OpenAL openAL;

    private SoundManager() {
    }

    public static SoundManager get() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }

    public void init() {
        if (initialised) return;
        initialised = true;
        openAL = new OpenAL();
    }

    void add(SoundSource soundSource) {
        soundSources.add(soundSource);
    }

    void add(SoundBuffer soundBuffer) {
        soundBuffers.add(soundBuffer);
    }

    public void destroy() {
        initialised = false;
        for (SoundSource soundSource : soundSources) soundSource.delete();
        for (SoundBuffer soundBuffer : soundBuffers) soundBuffer.delete();
        soundSources.clear();
        soundBuffers.clear();
        openAL.destroy();
    }

}
