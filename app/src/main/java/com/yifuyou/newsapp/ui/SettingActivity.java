package com.yifuyou.newsapp.ui;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yifuyou.newsapp.common.SettingObject;
import com.yifuyou.newsapp.common.SettingWidgetFactory;
import com.yifuyou.newsapp.common.ToastUtil;
import com.yifuyou.newsapp.R;

public class SettingActivity extends AppCompatActivity {

    public static final SettingObject[] map={
            new SettingObject("更多",(v)->{
                ToastUtil.Factory().setText("点击- 更多 ", Toast.LENGTH_SHORT).show();
            }),
            new SettingObject("通知",false,(v)->{}),
            new SettingObject("广告",true,(v)->{
                ToastUtil.Factory().setText("点击- 广告 ", Toast.LENGTH_SHORT).show();
            }),
            new SettingObject("节能",false,(v)->{}),
            new SettingObject("节流",true,(v)->{}),
            new SettingObject("个人",(v)->{}),
            new SettingObject("高级",(v)->{}),
            };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout layout=findViewById(R.id.settingList);
        SettingWidgetFactory factory = SettingWidgetFactory.of(this);
        for (SettingObject object : map) {
            layout.addView(factory.getWidget(object),params);
        }



    }
}
