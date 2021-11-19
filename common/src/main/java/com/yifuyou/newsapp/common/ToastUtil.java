package com.yifuyou.newsapp.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 统一初始化 toast
 */
public class ToastUtil {
    protected static Context context;

    public static void init(Context context0){
        context=context0;
    }

    public static void showText(String text,int duration){
        Toast.makeText(context,text,duration).show();
    }

    public static Toast makeText(String text,int duration){
        return Toast.makeText(context,text,duration);
    }

    private ViewGroup view;
    private TextView textView;
    private Toast toast;

    public static ToastUtil Factory(){
        return new ToastUtil();
    }


    private ToastUtil(){
         view = (ViewGroup)LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
         textView = (TextView) view.findViewById(R.id.toast_msg_view);
         toast=new Toast(context);
    }
    public ToastUtil setText(CharSequence text,int duration){
        if(text==""){
            throw new IllegalArgumentException("in makeText (CharSequence text), text should not be null");
        }
        if(textView!=null){
            textView.setText(text);
        }else{
            throw new NullPointerException("setText () on a null pointer ");
        }
        toast.setDuration(duration);
        return this;
    }
    public void show(){
        toast.setView(view);
        toast.show();
    }

}
