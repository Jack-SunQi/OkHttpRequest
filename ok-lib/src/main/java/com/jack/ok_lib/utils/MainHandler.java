package com.jack.ok_lib.utils;


import android.os.Handler;
import android.os.Looper;

public class MainHandler {

    private static Handler mainHandler;

    static {
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static void post(Runnable runnable){
        mainHandler.post(runnable);
    }
}
