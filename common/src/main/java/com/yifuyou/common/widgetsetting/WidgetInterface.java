package com.yifuyou.common.widgetsetting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.yifuyou.common.SettingObject;

public abstract class WidgetInterface extends RelativeLayout {

    public static final int BUTTON_SWITCH=0;
    public static final int BUTTON_CLICK=1;
    protected int buttonType;

    protected SettingObject settingObject;
    private final TextView textView;


 /*   @IntDef(flag = true, value = {
            BUTTON_CLICK,
            BUTTON_SWITCH
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ButtonTypeMode {}

    public void setButtonType(@ButtonTypeMode int type){
        if(buttonType!=type){
            buttonType=type;
            requestLayout();
        }
    }*/

    public void setTextView(){
        setGravity(Gravity.CENTER_VERTICAL);
        removeAllViews();
        textView.setText(settingObject.getText());
        RelativeLayout.LayoutParams params1=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(CENTER_VERTICAL );
        params1.alignWithParent=true;
        params1.leftMargin=20;
        addView(textView,params1);
        RelativeLayout.LayoutParams params2=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 128);
        params2.addRule(ALIGN_PARENT_END);
        params2.rightMargin=20;
        addView(addExtend(),params2);
    }

    public abstract View addExtend();

    public void setObject(SettingObject object){
        this.settingObject=object;
    }

    public WidgetInterface(Context context) {
        this(context,null);
    }

    public WidgetInterface(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WidgetInterface(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public WidgetInterface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        textView = new TextView(context);
    }







}
