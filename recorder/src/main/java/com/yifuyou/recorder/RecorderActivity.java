package com.yifuyou.recorder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yifuyou.newsapp.common.ToastUtil;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class RecorderActivity extends AppCompatActivity {

    RecordItemAdapter adapter;
    RecyclerView recyclerView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        checkPermission();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_recorder, new RecorderFragment(), "recorder").commitNow();

        recyclerView = findViewById(R.id.recyc_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecordItemAdapter("");
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    stopCheck();
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    checkDateUpdate();
                }
                return false;
            }
        });


    }
    void checkPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] strings = {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE};
            for (String string : strings) {
                if (checkSelfPermission(string) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(strings, 2022);
                    break;
                }

            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //获取权限后刷新数据
        if(requestCode==2022){
            if(adapter!=null){
                adapter.flashDate();
            }
        }
    }

    private Timer timer;
    private long delay=2000L;
    public void checkDateUpdate(){
        System.out.println("=======timer s========");
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                File file = new File(RecorderConfig.getConfig().getDefaultPath());
                if (file.isDirectory()) {
                    if (adapter.dataInit()) {
                        recyclerView.post(()->{
                            assert recyclerView!=null&&recyclerView.getAdapter()!=null;
                            recyclerView.getAdapter().notifyDataSetChanged();
                        });
                    }
                }
            }
        },delay);

    }

    public void stopCheck(){
        System.out.println("=======timer p========");
        if (timer != null) {
            timer.cancel();
        }
    }

    final String[] menuItem=new String[]{
            "delete","save",
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        for (String s : menuItem) {
            MenuItem item = menu.add(s);
            System.out.println(" item "+item.getItemId());
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        menu.getItem(0).setVisible(!adapter.deleting());
        menu.getItem(1).setVisible(adapter.deleting());
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title1 = item.getTitle().toString();
        switch (title1){
            case "delete":
                adapter.wantDelete(true);
                break;
            case "save":
                adapter.wantDelete(false);
            default:
                break;
        }
        ToastUtil.Factory().setText("LLLLL", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
