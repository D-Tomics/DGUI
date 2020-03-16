package engine.ui.IO.sound;

import engine.ui.utils.Delay;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

public class SoundCapture {

    public static final int CAPTURE_IDLE = 0, CAPTURE_RECORDING = 1;

    private int frequency;
    private int bitsPerSample;
    private int channels;
    private int format;

    private int state = CAPTURE_IDLE;

    public SoundCapture(int frequency, int bitsPerSample, int channels) {
        if (!ALC10.alcIsExtensionPresent(0, "ALC_EXT_CAPTURE")) {
            throw new IllegalStateException("could'nt record");
        }
        this.frequency = frequency;
        this.bitsPerSample = bitsPerSample;
        this.channels = channels;
        this.format = getOpenAlFormat();
    }

    //records synchronously
    public synchronized SoundBuffer record(int timeInSec) {
        if(timeInSec < 0)
            throw new IllegalStateException("invalid time : "+timeInSec);
        Delay delay = new Delay(timeInSec * 1000);
        long captureDevice = start(timeInSec);
        while (!delay.over()) ;
        return stop(captureDevice, timeInSec);
    }

    public boolean isRecording() {
        return state == CAPTURE_RECORDING;
    }

    public int getState() {
        return state;
    }

    private int getOpenAlFormat() {
        return bitsPerSample == 8 ?
                (channels == 1 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_STEREO8) :
                (channels == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16);
    }

    private long start(int timeInSec) {
        int bufferSize = frequency * (bitsPerSample / 8) * channels * (timeInSec + 1);
        long captureDeviceHandle = getCaptureDevice(bufferSize);
        ALC11.alcCaptureStart(captureDeviceHandle);
        state = CAPTURE_RECORDING;
        return captureDeviceHandle;
    }

    private SoundBuffer stop(long captureDeviceHandle, int timeInSec) {
        ALC11.alcCaptureStop(captureDeviceHandle);
        state = CAPTURE_IDLE;

        int bufferSize = frequency * channels * bitsPerSample / 8 * (timeInSec + 1);
        ByteBuffer buffer = getCapturedBuffer(captureDeviceHandle, bufferSize);
        closeCaptureDevice(captureDeviceHandle);
        try {
            return new SoundBuffer(buffer, format, frequency);
        } finally {
            buffer.flip();
        }
    }

    private long getCaptureDevice(int bufferSize) {
        long captureDeviceHandle = ALC11.alcCaptureOpenDevice((ByteBuffer) null, frequency, format, bufferSize);
        if (captureDeviceHandle == 0)
            throw new IllegalStateException("could'nt find capture device");
        return captureDeviceHandle;
    }

    private ByteBuffer getCapturedBuffer(long captureDeviceHandle, int bufferSize) {
        int capturedSamples = ALC10.alcGetInteger(captureDeviceHandle, ALC11.ALC_CAPTURE_SAMPLES);
        ByteBuffer buffer = BufferUtils.createByteBuffer(bufferSize);
        ALC11.alcCaptureSamples(captureDeviceHandle, buffer, capturedSamples);
        return buffer;
    }

    private void closeCaptureDevice(long device) {
        ALC11.alcCaptureCloseDevice(device);
    }

}
