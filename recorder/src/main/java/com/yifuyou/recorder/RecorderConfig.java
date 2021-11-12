package com.yifuyou.recorder;

import android.os.Environment;

public class RecorderConfig {
    private static RecorderConfig config;

    public static RecorderConfig getConfig() {
        if(config==null){
            config=new RecorderConfig();
        }
        return config;
    }
    private RecorderConfig(){
        path="/newsApp/record/list";
    }


    private final static String rootPath= Environment.getExternalStorageDirectory().getPath();
    protected String path;

    public void setPath(String newPath){
        this.path=newPath;
    }

    public String getDefaultPath(){
        return rootPath+path;
    }



}
