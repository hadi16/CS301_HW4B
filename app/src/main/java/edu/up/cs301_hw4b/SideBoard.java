package edu.up.cs301_hw4b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alex Hadi on 2/6/2018.
 */

public class SideBoard extends View {
    public SideBoard(Context context){
        super(context);
        generalInit();
    }

    public SideBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        generalInit();
    }

    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        generalInit();
    }

    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        generalInit();
    }

    private void generalInit(){
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3.0f);
        blackPaint.setStyle(Paint.Style.STROKE);

        int rectDim = canvas.getHeight() / 6;
        int offset = (canvas.getWidth() - rectDim) / 2;
        for (int i = 0; i< 6; i++){
            canvas.drawRect((float)offset, (float)(i*rectDim), (float)(offset+rectDim), (float)(i+1)*rectDim, blackPaint);
        }
    }
}
