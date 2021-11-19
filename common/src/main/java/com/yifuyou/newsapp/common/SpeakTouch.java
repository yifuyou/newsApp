package com.yifuyou.newsapp.common;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 封装（） 触碰事件监听
 * 开发三个回调接口 onStart, onSpeak(String), onFinish
 *
 *
 * */
public abstract class SpeakTouch implements View.OnTouchListener {

    private EventManager asr;
    private EventListener listener;
    private boolean valid=false;

    public SpeakTouch(Context context) {
        asr = EventManagerFactory.create(context, "asr");
        listener = new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                System.out.println("---===---==="+name);

                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
                    // 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
                    System.out.println("---===---===ready===");
                }
                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                    valid=true;
                    System.out.println("---===---===data===");
                    // 一句话的临时结果，最终结果及语义结果
                    String dataString =params;
                    if(dataString!=null){
                        System.out.println("---===---===data : "+parseBaseResult(dataString)+dataString);
                        dataString=parseBaseResult(dataString);
                        //todo callback
                        onSpeak(dataString);
                    }
                }
                if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_EXIT)){
                    System.out.println("---===---===data===finish"+valid);
                    if(valid){
                        onFinish();
                        valid=false;
                    }
                }

            }
        };
        asr.registerListener(listener);
    }

    public abstract void onStart();
    public abstract void onSpeak(String s);
    public abstract void onFinish();

    private void startSpeak(){
        onStart();
        String json = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":1537}";
        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
    }
    private void stopSpeak(){
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
    }
    private void cancelSpeak(){
        asr.send(SpeechConstant.ASR_CANCEL, null, null, 0, 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("---===---===action_down");
                startSpeak();
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("---===---===action_up");
                stopSpeak();
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("---===---===action_cancel");
                cancelSpeak();
                break;
        }
        return false;
    }
    private String parseBaseResult(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("best_result");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void release(){
        asr.unregisterListener(listener);
    }
}
