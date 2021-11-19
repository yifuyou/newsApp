package com.yifuyou.newsapp.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class DialogSpeech {
    public static DialogSpeech of(Context context) {
        return new DialogSpeech(context);
    }

    public static final int PROGRESSBAR_MAX = 100;
    private Context context;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private View view;
    private EventManager asr;
    private Timer timer;
    private int progress;

    Button finish_btn, cancel_btn;
    TextView speech_context;
    EventListener yourListener;
    ProgressBar timeProgressBar;
    TimerTask timerTask;
    private String dataString;
    private DialogSpeech(Context context) {
        this.context=context;
        view = LayoutInflater.from(context).inflate(R.layout.speech_layout, null, false);
        dialogBuilder = new AlertDialog.Builder(context)
                .setView(view);
        asr = EventManagerFactory.create(context, "asr");
        init();
    }
    private void init() {

        timeProgressBar = view.findViewById(R.id.speech_time);
        finish_btn = view.findViewById(R.id.speech_finish);
        cancel_btn = view.findViewById(R.id.speech_cancel);
        speech_context = view.findViewById(R.id.speech_content);
        timeProgressBar.setMax(100);
        progress = 0;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timeProgressBar.setProgress(progress+=10);
                if (timeProgressBar.getProgress() >= timeProgressBar.getMax()) {
                    asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
                    timerTask.cancel();
                }
            }
        };
        timer = new Timer();

        yourListener = new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
                    // 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
                    System.out.println("---===---===ready===");
                }
                if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                    System.out.println("---===---===data===");
                    // 一句话的临时结果，最终结果及语义结果
                    dataString =params;
                    System.out.println("---===---===data : "+dataString);
                    if(dataString!=null){
                        speech_context.setText(parseBaseResult(dataString));

                    }
                }
                // ... 支持的输出事件和事件支持的事件参数见“输入和输出参数”一节
                if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_AUDIO)){
                    System.out.println("---===---===audio=== ");
                }
                System.out.println("---===---==="+"name:"+name+", params:"+params);
                if(data!=null){
                    System.out.println(new String(data));
                }
            }
        };
        asr.registerListener(yourListener);

        finish_btn.setOnClickListener((v) -> {
            finish();
        });
        cancel_btn.setOnClickListener(v -> {
            cancelSpeech();
        });
    }
    public void setPositiveButton(OnClickListener clickListener){
        finish_btn.setOnClickListener(v->{
            clickListener.onClick(dataString,v);
            finish();
        });
    }
    public void setNegativeButton(DialogInterface.OnClickListener clickListener){
        cancel_btn.setOnClickListener(v->{
            clickListener.onClick(dialog,v.getId());
            cancelSpeech();
        });
    }


    public void show() {
        if (dialogBuilder != null) {
            if(dialog==null){
                dialog = dialogBuilder.create();
            }
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            timer.schedule(timerTask, 200, 500);

            startSpeech();
        }
    }

    private void startSpeech() {

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

  /*      if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }*/
        // 基于SDK集成2.1 设置识别参数
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        (new AutoCheck(context, new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        ToastUtil.showText(message, Toast.LENGTH_SHORT);
                        ; // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }
        }, false)).checkAsr(params);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
        // asr.params(反馈请带上此行日志):{"accept-audio-data":false,"disable-punctuation":false,"accept-audio-volume":true,"pid":1736}

//其中{"accept-audio-data":false,"disable-punctuation":false,"accept-audio-volume":true,"pid":1736}为ASR_START事件的参数

//        String json = "{\"accept-audio-data\":false,\"disable-punctuation\":false,\"accept-audio-volume\":true,\"pid\":1736}";
//        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);

    }

    private void cancelSpeech() {

//        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
        //发送停止录音事件，提前结束录音等待识别结果

        asr.send(SpeechConstant.ASR_CANCEL, null, null, 0, 0);
        //取消本次识别，取消后将立即停止不会返回识别结果
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void finish() {

        if (timer != null) {
            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
            timer.cancel();
            timer.purge();
        }
        if (asr != null) {
            asr.unregisterListener(yourListener);
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

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


    public interface OnClickListener{
        void onClick(String data,View v);
    }

}
