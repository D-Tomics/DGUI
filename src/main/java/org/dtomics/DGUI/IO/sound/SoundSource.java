package org.dtomics.DGUI.IO.sound;

import static org.lwjgl.openal.AL10.*;

public final class SoundSource {

    private int sourceId;

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
        return alGetSourcei(sourceId,AL_SOURCE_STATE) == AL_PLAYING;
    }
}
