package com.project.thor.circle.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.project.thor.circle.enums.GameState;

import java.util.Random;

public class FigureView extends View {

    private final static int ID_CIRCLE = 1;
    private final static int ID_TRIANGLE = 2;
    private final static int ID_RECRANGLE = 3;
    private final static int ID_STAR = 4;
    private int idFigure;
    private Random random = new Random();

    private GameState state;

    private Paint mPaint = new Paint();
    private Paint cPaint = new Paint();

    private float radius;
    private float radiusFirst;
    private float radiusSecond;



    public FigureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRadius(float radiusCircle, float radiusFirstCircle, float radiusSecondCircle){
        this.radius = radiusCircle;
        this.radiusFirst = radiusFirstCircle;
        this.radiusSecond = radiusSecondCircle;
    }
    public float getRadiusFirst() {return radiusFirst;}
    public float getRadiusSecond() { return radiusSecond; }
    public float getRadius() {
        return radius;
    }
    public void setRadius(float radiusCircle) { this.radius = radiusCircle; }
    public void setIdFigure(int idFigure) { this.idFigure = idFigure; }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight() / 2;
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(canvas.getWidth()/288);
        mPaint.setStyle(Paint.Style.STROKE);
        cPaint.setColor(Color.RED);
        cPaint.setStrokeWidth(canvas.getWidth()/288);
        cPaint.setStyle(Paint.Style.STROKE);

        if (state != GameState.RUN){
            radiusFirst =(centerX/3) + random.nextInt((int)(centerX/2));// min 1/4 max;
            radiusSecond =radiusFirst - random.nextInt((int) centerX/8)-(centerX/18);
        }
        if(radius <= centerX){
            radius = radius + (float)(canvas.getWidth()*0.005);
        }
        switch(idFigure){
            case ID_CIRCLE:
                canvas.drawCircle(centerX, centerY, radius, mPaint);
                canvas.drawCircle(centerX, centerY, radiusFirst, cPaint);
                canvas.drawCircle(centerX, centerY, radiusSecond, cPaint);
                break;
            case ID_RECRANGLE:
                canvas.drawRect(centerX-radius, centerY-radius, centerX+radius, centerY+radius, mPaint);
                canvas.drawRect(centerX-radiusFirst, centerY-radiusFirst, centerX+radiusFirst, centerY+radiusFirst, cPaint);
                canvas.drawRect(centerX-radiusSecond, centerY-radiusSecond, centerX+radiusSecond, centerY+radiusSecond, cPaint);
                break;
            case ID_TRIANGLE:
                canvas.drawPath(drawTriangle(centerX, centerY, radius), mPaint);
                canvas.drawPath(drawTriangle(centerX, centerY, radiusFirst), cPaint);
                canvas.drawPath(drawTriangle(centerX, centerY, radiusSecond), cPaint);
                break;
            case ID_STAR:
                canvas.drawPath(drawStar(centerX, centerY, radius), mPaint);
                canvas.drawPath(drawStar(centerX, centerY, radiusFirst), cPaint);
                canvas.drawPath(drawStar(centerX, centerY, radiusSecond), cPaint);
                break;
        }


    }

    public Path drawTriangle(float xf, float yf, float radius){
        Path path = new Path();
        int r = (int) radius;
        int x = (int) xf;
        int y = (int) yf;
        int k =  (int) Math.sqrt((r*r) - (r/2)^2);
        Point a = new Point(x, y - r);
        Point b = new Point(x - k, y + r/2);
        Point c = new Point(x + k, y + r/2);
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.moveTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.moveTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();
        return path;
    }

    public Path drawStar(float xf, float yf, float radius){
        Path path = new Path();
        int r = (int) radius;
        int x = (int) xf;
        int y = (int) yf;
        Point a = new Point(x, y - r);
        Point b = new Point(x +(int)(r*0.3), y - (int)(r*0.3));
        Point o = new Point(x - (int)(r*0.3), y - (int)(r*0.3));
        Point c = new Point(x + (int)(r*0.9), y - (int)(r*0.3));
        Point d = new Point(x + (int)(r*0.4), y + (int)(r*0.2));
        Point e = new Point(x + (int)(r*0.6), y + (int)(r*0.9));
        Point l = new Point(x, y + (int)(r*0.5));
        Point m = new Point(x - (int)(r*0.6), y + (int)(r*0.9));
        Point k = new Point(x - (int)(r*0.4), y + (int)(r*0.2));
        Point n = new Point(x - (int)(r*0.9), y - (int)(r*0.3));
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.moveTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.moveTo(c.x, c.y);
        path.lineTo(d.x, d.y);
        path.moveTo(d.x, d.y);
        path.lineTo(e.x, e.y);
        path.moveTo(e.x, e.y);
        path.lineTo(l.x, l.y);
        path.moveTo(l.x, l.y);
        path.lineTo(m.x, m.y);
        path.moveTo(m.x, m.y);
        path.lineTo(k.x, k.y);
        path.moveTo(k.x, k.y);
        path.lineTo(n.x, n.y);
        path.moveTo(n.x, n.y);
        path.lineTo(o.x, o.y);
        path.moveTo(o.x, o.y);
        path.lineTo(a.x, a.y);
        path.close();
        return path;
    }


    public void setState(GameState state) {
        this.state = state;
    }

}
