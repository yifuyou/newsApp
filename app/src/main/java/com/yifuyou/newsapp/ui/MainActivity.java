package com.yifuyou.newsapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.yifuyou.http.NetConnect;
import com.yifuyou.http.SocketServerN;
import com.yifuyou.newsapp.R;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    NetConnect httpConnect;
    EditText text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        httpConnect = NetConnect.builder();
        text = findViewById(R.id.text1);

//        requestPermissions(new String[]{Manifest.permission.INTERNET},0x088);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] strings = {
                    Manifest.permission.INTERNET,};
            for (int i = 0; i < strings.length; i++) {
                if(checkSelfPermission(strings[i])!=PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(strings, 2022);
                    break;
                }

            }

        }

//        SocketService.initService();
        SocketServerN.Builder();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    long lastTab = 0;

    @SuppressLint("NonConstantResourceId")
    public void click(View view) {

        long time = System.currentTimeMillis();
        if (time - lastTab > 1000) {
            lastTab = time;
            switch (view.getId()){
                case R.id.send_msg_btn:
                    httpConnect.call();
                    sendMessage("---000---").start();
                    sendMessage("---000---").start();
                    sendMessage("message:" + text.getText().toString().trim()).start();
                    sendMessage("-----999-----", 1500L).start();
                    break;
                case R.id.button_1:
                    Intent intent = new Intent("com.yifuyou.recorder.RecorderActivity");
                    startActivity(intent);
                    break;
                case R.id.button_2:
                    Intent setting = new Intent(this, SettingActivity.class);
                    startActivity(setting);
                    break;
                case R.id.button_3:
                    Intent speech=new Intent(this, SpeechActivity.class);
                    startActivity(speech);
                default:break;

            }

        }

    }

    public Thread sendMessage(String s, long delayed) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delayed);
                    Socket socket = new Socket("127.0.0.1", 14333);
                    OutputStream outputStream = socket.getOutputStream();
                    for (int i = 0; i < 10; i++) {
                        outputStream.write((s + i).getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    }
                    outputStream.close();
                    socket.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Thread sendMessage(String s){
        return sendMessage(s, 0);
    }

}
