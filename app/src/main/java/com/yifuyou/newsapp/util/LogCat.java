package com.yifuyou.newsapp.util;

import android.util.Log;

public  class LogCat {
    public static final String TAG="---Log---";

    public static void log(String message){
        Log.i(TAG, "log: "+message);

    }

}
