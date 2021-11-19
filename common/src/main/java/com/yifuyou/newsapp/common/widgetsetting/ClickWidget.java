package com.yifuyou.newsapp.common.widgetsetting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.yifuyou.newsapp.common.R;

public class ClickWidget extends WidgetInterface {

    private final ImageButton button;

    @Override
    public View addExtend() {
        button.setBackground(null);
        button.setImageResource(R.drawable.ic_next);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                settingObject.getCallback().run(v);
            }
        });
        System.out.println("----------");
        return button;
    }

    public ClickWidget(Context context) {
        this(context,null);
    }

    public ClickWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClickWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ClickWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        button=new ImageButton(context);
    }






}
