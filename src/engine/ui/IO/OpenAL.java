package engine.ui.IO;

import org.lwjgl.openal.*;

public class OpenAL {

    private static boolean initialised;
    private static long device;
    private static long context;
    private static ALCapabilities capabilities;

    OpenAL() {}

    protected static void create() {
        if(initialised) return;
        initialised = true;
        String defaultdevice  = ALC10.alcGetString(0,ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        device = ALC10.alcOpenDevice(defaultdevice);
        if(ALC10.alcGetError(device) != ALC10.ALC_NO_ERROR)
            System.err.println("Error in opening device "+ defaultdevice);

        context = ALC10.alcCreateContext(device,new int[]{0});
        if(ALC10.alcGetError(device) != ALC10.ALC_NO_ERROR)
            System.err.println("Error in creating context");

        ALC10.alcMakeContextCurrent(context);
        if(ALC10.alcGetError(device) != ALC10.ALC_NO_ERROR)
            System.err.println("Error in making audio context current");

        capabilities = AL.createCapabilities(ALC.createCapabilities(device));
        if(AL10.alGetError() != AL10.AL_NO_ERROR)
            System.err.println("Error int creating capabilities");
    }

    protected static void close() {
        //initialised = false;
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
        ALC.destroy();
    }

    public static ALCapabilities getCapabilities() {
        return capabilities;
    }

    public static long getContext() {
        return context;
    }

}
