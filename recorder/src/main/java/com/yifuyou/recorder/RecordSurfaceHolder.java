package com.yifuyou.recorder;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceHolder;

public class RecordSurfaceHolder implements SurfaceHolder {
    @Override
    public void addCallback(Callback callback) {

    }

    @Override
    public void removeCallback(Callback callback) {

    }

    @Override
    public boolean isCreating() {
        return false;
    }

    @Override
    public void setType(int type) {

    }

    @Override
    public void setFixedSize(int width, int height) {

    }

    @Override
    public void setSizeFromLayout() {

    }

    @Override
    public void setFormat(int format) {

    }

    @Override
    public void setKeepScreenOn(boolean screenOn) {

    }

    @Override
    public Canvas lockCanvas() {
        return null;
    }

    @Override
    public Canvas lockCanvas(Rect dirty) {
        return null;
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {

    }

    @Override
    public Rect getSurfaceFrame() {
        return null;
    }

    @Override
    public Surface getSurface() {
        return null;
    }
}
