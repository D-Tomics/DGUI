package org.dtomics.DGUI.IO.sound;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;

public final class SoundSource {

    private final int sourceId;

    public SoundSource() {
        sourceId = alGenSources();
        SoundManager.get().add(this);
    }

    public void play(SoundBuffer buffer) {
        stop();
        alSourcei(sourceId, AL_BUFFER, buffer.getBufferId());
        resume();
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    public void pause() {
        alSourcePause(sourceId);
    }

    public void resume() {
        alSourcePlay(sourceId);
    }

    public void delete() {
        stop();
        alDeleteSources(sourceId);
    }

    public void setVolume(float volume) {
        alSourcef(sourceId, AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        alSourcef(sourceId, AL_PITCH, pitch);
    }

    public void loop(boolean loop) {
        alSourcei(sourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
    }

    public boolean isPlaying() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }
}
