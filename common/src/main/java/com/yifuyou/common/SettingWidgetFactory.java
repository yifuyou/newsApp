package com.yifuyou.common;

import android.content.Context;

import com.yifuyou.common.widgetsetting.ClickWidget;
import com.yifuyou.common.widgetsetting.SwitchWidget;
import com.yifuyou.common.widgetsetting.WidgetInterface;

public class SettingWidgetFactory {
    public static SettingWidgetFactory of(Context context) {
        return new SettingWidgetFactory(context);
    }

    private final Context context;

    private SettingWidgetFactory(Context context) {
        this.context = context;
    }

/*    public WidgetInterface getWidget(String text, int type) {
        WidgetInterface widget=null;
        switch (type) {
            default:
            case WidgetInterface.BUTTON_CLICK:
                widget = new ClickWidget(context);
                break;
            case WidgetInterface.BUTTON_SWITCH:
                widget = new SwitchWidget(context);
                break;
        }
        widget.setTextView(text);
        return widget;
    }*/

    public WidgetInterface getWidget(SettingObject settingObject) {
        WidgetInterface widget=null;
        switch (settingObject.type) {
            default:
            case WidgetInterface.BUTTON_CLICK:
                widget = new ClickWidget(context);
                break;
            case WidgetInterface.BUTTON_SWITCH:
                widget = new SwitchWidget(context);
                break;
        }
        widget.setObject(settingObject);
        widget.setTextView();
        return widget;
    }
}
