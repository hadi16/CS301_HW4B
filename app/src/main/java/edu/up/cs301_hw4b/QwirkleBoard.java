package edu.up.cs301_hw4b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alex Hadi on 1/31/2018.
 */

public class QwirkleBoard extends View {
    private static final int scaleDim = 12;

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
        blackPaint.setStrokeWidth(3.0f);
        blackPaint.setStyle(Paint.Style.STROKE);

        int rectDim = canvas.getHeight() / scaleDim;
        int offset = (canvas.getWidth() - (scaleDim*rectDim)) / 2;
        for (int i = 0; i< scaleDim; i++){
            for (int j = 0; j< scaleDim; j++){
                canvas.drawRect((float)(i*rectDim+offset), (float)(j*rectDim), (float)((i+1)*rectDim+offset), (float)(j+1)*rectDim, blackPaint);
            }
        }
    }
}
