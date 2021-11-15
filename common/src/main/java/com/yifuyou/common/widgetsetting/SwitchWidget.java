package com.yifuyou.common.widgetsetting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;


public class SwitchWidget extends WidgetInterface{

    private final Switch button;

    @Override
    public View addExtend() {
        button.setTextOff("F");
        button.setTextOn("O");
        button.setShowText(true);
        button.setChecked(settingObject.isState());
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingObject.getCallback().run(buttonView);
            }
        });

        System.out.println("===========");
        return button;
    }



    public SwitchWidget(Context context) {
        this(context,null);
    }

    public SwitchWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SwitchWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        button=new Switch(context);
    }




}
