package up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alex Hadi on 2/6/2018.
 */

public class SideBoardLeft extends View {

    private Bitmap batBlue = BitmapFactory.decodeResource(getResources(), R.drawable.bat_blue);
    private Bitmap batGreen = BitmapFactory.decodeResource(getResources(), R.drawable.bat_green);
    private Bitmap batOrange = BitmapFactory.decodeResource(getResources(), R.drawable.bat_orange);
    private Bitmap batPurple = BitmapFactory.decodeResource(getResources(), R.drawable.bat_purple);
    private Bitmap batYellow = BitmapFactory.decodeResource(getResources(), R.drawable.bat_yellow);
    private Bitmap batRed = BitmapFactory.decodeResource(getResources(), R.drawable.bat_red);

    private Bitmap owlBlue = BitmapFactory.decodeResource(getResources(), R.drawable.owl_blue);
    private Bitmap owlGreen = BitmapFactory.decodeResource(getResources(), R.drawable.owl_green);
    private Bitmap owlOrange = BitmapFactory.decodeResource(getResources(), R.drawable.owl_orange);
    private Bitmap owlPurple = BitmapFactory.decodeResource(getResources(), R.drawable.owl_purple);
    private Bitmap owlYellow = BitmapFactory.decodeResource(getResources(), R.drawable.owl_yellow);
    private Bitmap owlRed = BitmapFactory.decodeResource(getResources(), R.drawable.owl_red);

    private Bitmap snakeBlue = BitmapFactory.decodeResource(getResources(), R.drawable.snake_blue);
    private Bitmap snakeGreen = BitmapFactory.decodeResource(getResources(), R.drawable.snake_green);
    private Bitmap snakeOrange = BitmapFactory.decodeResource(getResources(), R.drawable.snake_orange);
    private Bitmap snakePurple = BitmapFactory.decodeResource(getResources(), R.drawable.snake_purple);
    private Bitmap snakeYellow = BitmapFactory.decodeResource(getResources(), R.drawable.snake_yellow);
    private Bitmap snakeRed = BitmapFactory.decodeResource(getResources(), R.drawable.snake_red);

    private Bitmap dogBlue = BitmapFactory.decodeResource(getResources(), R.drawable.dog_blue);
    private Bitmap dogGreen = BitmapFactory.decodeResource(getResources(), R.drawable.dog_green);
    private Bitmap dogOrange = BitmapFactory.decodeResource(getResources(), R.drawable.dog_orange);
    private Bitmap dogPurple = BitmapFactory.decodeResource(getResources(), R.drawable.dog_purple);
    private Bitmap dogYellow = BitmapFactory.decodeResource(getResources(), R.drawable.dog_yellow);
    private Bitmap dogRed = BitmapFactory.decodeResource(getResources(), R.drawable.dog_red);

    private Bitmap birdBlue = BitmapFactory.decodeResource(getResources(), R.drawable.bird_blue);
    private Bitmap birdGreen = BitmapFactory.decodeResource(getResources(), R.drawable.bird_green);
    private Bitmap birdOrange = BitmapFactory.decodeResource(getResources(), R.drawable.bird_orange);
    private Bitmap birdPurple = BitmapFactory.decodeResource(getResources(), R.drawable.bird_purple);
    private Bitmap birdYellow = BitmapFactory.decodeResource(getResources(), R.drawable.bird_yellow);
    private Bitmap birdRed = BitmapFactory.decodeResource(getResources(), R.drawable.bird_red);

    private Bitmap foxBlue = BitmapFactory.decodeResource(getResources(), R.drawable.fox_blue);
    private Bitmap foxGreen = BitmapFactory.decodeResource(getResources(), R.drawable.fox_green);
    private Bitmap foxOrange = BitmapFactory.decodeResource(getResources(), R.drawable.fox_orange);
    private Bitmap foxPurple = BitmapFactory.decodeResource(getResources(), R.drawable.fox_purple);
    private Bitmap foxYellow = BitmapFactory.decodeResource(getResources(), R.drawable.fox_yellow);
    private Bitmap foxRed = BitmapFactory.decodeResource(getResources(), R.drawable.fox_red);



    public SideBoardLeft(Context context){
        super(context);
        generalInit();
    }

    public SideBoardLeft(Context context, AttributeSet attrs){
        super(context, attrs);
        generalInit();
    }

    public SideBoardLeft(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        generalInit();
    }

    public SideBoardLeft(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
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


        Bitmap owlBlueSmall = Bitmap.createScaledBitmap(owlBlue, rectDim, rectDim , false);
        Bitmap snakeRedSmall = Bitmap.createScaledBitmap(snakeRed, rectDim, rectDim , false);
        Bitmap birdGreenSmall = Bitmap.createScaledBitmap(birdGreen, rectDim, rectDim , false);
        Bitmap foxOrangeSmall = Bitmap.createScaledBitmap(foxOrange, rectDim, rectDim , false);
        Bitmap birdPurpleSmall = Bitmap.createScaledBitmap(birdPurple, rectDim, rectDim , false);
        Bitmap batYellowSmall = Bitmap.createScaledBitmap(batYellow, rectDim, rectDim , false);


        canvas.drawBitmap(owlBlueSmall, (float)(offset), 0, blackPaint);
        canvas.drawBitmap(snakeRedSmall, (float)(offset), (float)(rectDim), blackPaint);
        canvas.drawBitmap(birdGreenSmall, (float)(offset), (float)(2*rectDim), blackPaint);
        canvas.drawBitmap(foxOrangeSmall, (float)(offset), (float)(3*rectDim), blackPaint);
        canvas.drawBitmap(birdPurpleSmall, (float)(offset), (float)(4*rectDim), blackPaint);
        canvas.drawBitmap(batYellowSmall, (float)(offset), (float)(5*rectDim), blackPaint);
        
    }
}
