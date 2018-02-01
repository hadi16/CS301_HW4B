package edu.up.cs301_hw4b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Alex Hadi on 1/31/2018.
 */

public class QwirkleBoard extends SurfaceView {
    public QwirkleBoard(Context context){
        super(context);
        generalInit();
    }

    public QwirkleBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        generalInit();
    }

    public QwirkleBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        generalInit();
    }

    public QwirkleBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        generalInit();
    }

    private void generalInit(){
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(10.0f);
        blackPaint.setStyle(Paint.Style.STROKE);
        for (int i=0; i<canvas.getWidth(); i+=1000){
            for (int j=0; j<canvas.getHeight(); j+=1000){
                canvas.drawRect((float)i, (float)j, (float)(i+100), (float)(j+100), blackPaint);
            }
        }
    }
}
