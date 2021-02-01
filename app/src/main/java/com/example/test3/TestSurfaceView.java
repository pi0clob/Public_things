package com.example.test3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.graphics.Color.BLUE;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    MyThread tr;

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(BLUE);
        holder.unlockCanvasAndPost(canvas);
        tr = new MyThread(getHolder(), 10000, 10000, 0);
        tr.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rad = 5;
        tr.stopThread();
        tr = new MyThread(getHolder(), event.getRawX(), event.getRawY(), rad);
        tr.start();
        return true;
    }
}

class MyThread extends  Thread{
    private boolean running = true;
    SurfaceHolder hold;
    float x;
    float y;
    float rad;

    MyThread(SurfaceHolder holder, float x, float y, float rad){
        this.hold = holder;
        this.x = x;
        this.y = y;
        this.rad = rad;
    }

    @Override
    public void run() {
        this.drawCircleMy(hold, x, y, rad);
        super.run();
    }

    public void stopThread(){
        this.running = false;
    }

    public void drawCircleMy(SurfaceHolder holder, float x, float y, float rad) {
        Paint p = new Paint();
        p.setARGB(255, 255, 255, 0);
        while (this.running) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawARGB(255, 0, 0, 255);
            canvas.drawCircle(x, y, rad, p);
            holder.unlockCanvasAndPost(canvas);
            rad+=3;
        }
    }

}