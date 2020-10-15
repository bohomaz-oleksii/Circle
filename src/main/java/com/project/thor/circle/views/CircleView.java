package com.project.thor.circle.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView extends View {

    private Paint mPaint = new Paint();
    private Paint cPaint = new Paint();

    private float radiusCircle = 0;
    private float radiusFirstCirlce;
    private float radiusSecondCirle;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRadius(float radiusCircle, float radiusFirstCircle, float radiusSecondCircle){
        this.radiusCircle = radiusCircle;
        this.radiusFirstCirlce = radiusFirstCircle;
        this.radiusSecondCirle = radiusSecondCircle;
    }

    public float getRadiusCircle() {
        return radiusCircle;
    }

    public void setRadiusCircle(float radiusCircle) {
        this.radiusCircle = radiusCircle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight() / 2;
        //radiusCircle = canvas.getWidth() / 4;

        //Log.d("TAG", "circle = " + radiusCircle);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        cPaint.setColor(Color.RED);
        cPaint.setStrokeWidth(5);
        cPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerY, radiusCircle, mPaint);
        canvas.drawCircle(centerX, centerY, radiusFirstCirlce, cPaint);
        canvas.drawCircle(centerX, centerY, radiusSecondCirle, cPaint);
    }
}
