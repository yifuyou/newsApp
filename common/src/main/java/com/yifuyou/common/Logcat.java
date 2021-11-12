package com.yifuyou.common;

import android.util.Log;

public class Logcat {
    public static final String TAG = "com.yifuyou.common.Logcat";
    public static boolean debugging;

    public static void logI(String msg){
        if (debugging) {
            Log.i(TAG, "log: "+msg);
        }
    }
    public static void logE(String msg, Exception e){
        if (debugging) {
            Log.e(TAG, "logE: "+msg,e);
        }
    }
    public static void logE(String msg){
        if (debugging) {
            Log.e(TAG, "logE: "+msg);
        }
    }

}
