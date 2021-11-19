package com.yifuyou.newsapp.ui;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yifuyou.newsapp.common.DialogSpeech;
import com.yifuyou.newsapp.R;
import com.yifuyou.newsapp.common.SpeakTouch;

public class SpeechActivity extends AppCompatActivity {

    LinearLayout layout;
    ImageButton speechButton;
    DialogSpeech dialog;

    ProgressBar progressBar;
    TextView textView;

    String speakContent;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_activity);
        layout = findViewById(R.id.message_layout);
        speechButton = findViewById(R.id.speech_btn);
        progressBar=findViewById(R.id.speak_time);
        textView=findViewById(R.id.speaking_words);
        initSound();
/*        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow();
            }
        });*/
        speechButton.setOnTouchListener(new SpeakTouch(this){

            @Override
            public void onStart() {
                playSound();
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSpeak(String s) {
                speakContent=s;
                textView.setText(s);
            }

            @Override
            public void onFinish() {
                TextView tV = new TextView(getApplicationContext());
                tV.setText(speakContent);
                layout.addView(tV);
                textView.setText("");
                //移除点压状态
                speechButton.setFocusable(false);
                speechButton.setFocusable(true);
                textView.setVisibility(View.INVISIBLE);
                playSound();
            }
        });

    }

    /* 用弹窗来实现语音识别 */
 /*   private void dialogShow() {
        dialog = DialogSpeech.of(this);
        dialog.setPositiveButton((data, v) -> System.out.println(data));
        dialog.setNegativeButton((d, w) -> {
            System.out.println("cancel");
        });
        dialog.show();
    }*/

// 提示音播放功能
    private SoundPool soundPool;
    private int soundID;
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.press, 1);
    }

    private void playSound() {
        soundPool.play(
                soundID,
                0.1f,      //左耳道音量【0~1】
                0.5f,      //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1.5f          //播放速度【1是正常，范围从0~2】
        );
    }

    @Override
    protected void onDestroy() {
        soundPool.release();

        super.onDestroy();
    }
}
