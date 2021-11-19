package com.yifuyou.newsapp.common;


import android.view.View;

public class SettingObject {

    public static final int BUTTON_SWITCH=0;
    public static final int BUTTON_CLICK=1;
    String text;
    boolean state;
    Callback callback;
    int type;

    public String getText() {
        return text;
    }


    public boolean isState() {
        return state;
    }


    public Callback getCallback() {
        return callback;
    }


    public int getType() {
        return type;
    }


    public SettingObject(String text, boolean state, Callback callback){
        this.text=text;
        this.state=state;
        this.callback=callback;
        this.type=BUTTON_SWITCH;
    }
    public SettingObject(String text,Callback callback){
        this.text=text;
        this.callback=callback;
        this.type=BUTTON_CLICK;
    }

    public interface Callback{
        void run(View view);
    }
}
