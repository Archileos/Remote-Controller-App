package com.malakhau.arthur.remotejoystick.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Joystick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    float middleX;
    float middleY;
    float radius;
    float innerRadius;
    JoystickTouchListener joystickListener;

    public Joystick(Context context) {
        super(context);
        if (context instanceof JoystickTouchListener)
            joystickListener = (JoystickTouchListener) context;
        setOnTouchListener(this);
        getHolder().addCallback(this);
    }

    public Joystick(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (context instanceof JoystickTouchListener)
            joystickListener = (JoystickTouchListener) context;
        setOnTouchListener(this);
        getHolder().addCallback(this);
    }

    public Joystick(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
        if (context instanceof JoystickTouchListener)
            joystickListener = (JoystickTouchListener) context;
        setOnTouchListener(this);
        getHolder().addCallback(this);
    }

    public interface JoystickTouchListener {

        void onChange(float aileron, float elevator);
    }

    public void setJoystick() {
        float smallestDimension = Math.min(this.getWidth(), this.getHeight());
        this.middleX = this.getWidth() >> 1;
        this.middleY = this.getHeight() >> 1;
        this.radius = smallestDimension / 3;
        this.innerRadius = smallestDimension / 6;
    }

    public void drawJoystick(float positionX, float positionY) {
        Canvas canvas = this.getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint currentColor = new Paint();
        currentColor.setARGB(255, 3, 218, 197);
        canvas.drawCircle(this.middleX, this.middleY, this.radius, currentColor);
        currentColor.setARGB(255, 0, 0, 0);
        canvas.drawCircle(positionX, positionY, this.innerRadius, currentColor);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) { }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setJoystick();
        if (this.getHolder().getSurface().isValid())
            drawJoystick(this.middleX, this.middleY);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        float positionX = motionEvent.getX();
        float positionY = motionEvent.getY();
        if (view.equals(this) && motionEvent.getAction() != MotionEvent.ACTION_UP) {
            double radius = Math.pow(Math.pow(positionX - this.middleX, 2) + Math.pow(positionY - this.middleY, 2), 0.5);
            if (radius > this.radius) {
                double sin = (positionY - this.middleY) / radius;
                double cos = (positionX - this.middleX) / radius;
                positionX = (float) (this.radius * cos + this.middleX);
                positionY = (float) (this.radius * sin + this.middleY);
            }
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            positionX = this.middleX;
            positionY = this.middleY;
        }
        drawJoystick(positionX, positionY);
        if (joystickListener != null) {
            float aileron = (positionX - this.middleX) / (this.radius / (float) Math.pow(2, 0.5));
            float elevator = (positionY - this.middleY) / (this.radius / (float) Math.pow(2, 0.5));
            if (aileron > 1)
                aileron = 1;
            if (elevator > 1)
                elevator = 1;
            joystickListener.onChange(aileron, -elevator);
        }
        return true;
    }
}
