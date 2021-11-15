package com.yifuyou.common.widgetsetting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.yifuyou.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
