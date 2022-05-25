package org.chickenhook.myandroidhackinglibrary;

public class NativeInterface {
    static {
        System.loadLibrary("myNativeLib");
    }

    public static native void installHooks();
}
