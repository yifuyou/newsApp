package com.yifuyou.newsapp.base;

import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yifuyou.newsapp.util.LogCat;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogCat.log("onCreate");

    }

    protected void post(){
        LogCat.log("onCreate");
    }

}
