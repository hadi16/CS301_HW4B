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
 * Created by Alex Hadi on 1/31/2018.
 */

public class QwirkleBoard extends View {
    private static final int scaleDim = 12;
    private Paint blackPaint = new Paint();

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
        int rectDim = canvas.getHeight() / scaleDim;

        Bitmap batBlueSmall = Bitmap.createScaledBitmap(batBlue, rectDim, rectDim, false);
        Bitmap batGreenSmall = Bitmap.createScaledBitmap(batGreen, rectDim, rectDim, false);
        Bitmap batOrangeSmall = Bitmap.createScaledBitmap(batOrange, rectDim, rectDim, false);
        Bitmap batPurpleSmall = Bitmap.createScaledBitmap(batPurple, rectDim, rectDim, false);
        Bitmap batYellowSmall = Bitmap.createScaledBitmap(batYellow, rectDim, rectDim, false);
        Bitmap batRedSmall = Bitmap.createScaledBitmap(batRed, rectDim, rectDim, false);

        Bitmap owlBlueSmall = Bitmap.createScaledBitmap(owlBlue, rectDim, rectDim, false);
        Bitmap owlGreenSmall = Bitmap.createScaledBitmap(owlGreen, rectDim, rectDim, false);
        Bitmap owlOrangeSmall = Bitmap.createScaledBitmap(owlOrange, rectDim, rectDim, false);
        Bitmap owlPurpleSmall = Bitmap.createScaledBitmap(owlPurple, rectDim, rectDim, false);
        Bitmap owlYellowSmall = Bitmap.createScaledBitmap(owlYellow, rectDim, rectDim, false);
        Bitmap owlRedSmall = Bitmap.createScaledBitmap(owlRed, rectDim, rectDim, false);

        Bitmap snakeBlueSmall = Bitmap.createScaledBitmap(snakeBlue, rectDim, rectDim, false);
        Bitmap snakeGreenSmall = Bitmap.createScaledBitmap(snakeGreen, rectDim, rectDim, false);
        Bitmap snakeOrangeSmall = Bitmap.createScaledBitmap(snakeOrange, rectDim, rectDim, false);
        Bitmap snakePurpleSmall = Bitmap.createScaledBitmap(snakePurple, rectDim, rectDim, false);
        Bitmap snakeYellowSmall = Bitmap.createScaledBitmap(snakeYellow, rectDim, rectDim, false);
        Bitmap snakeRedSmall = Bitmap.createScaledBitmap(snakeRed, rectDim, rectDim, false);

        Bitmap dogBlueSmall = Bitmap.createScaledBitmap(dogBlue, rectDim, rectDim, false);
        Bitmap dogGreenSmall = Bitmap.createScaledBitmap(dogGreen, rectDim, rectDim, false);
        Bitmap dogOrangeSmall = Bitmap.createScaledBitmap(dogOrange, rectDim, rectDim, false);
        Bitmap dogPurpleSmall = Bitmap.createScaledBitmap(dogPurple, rectDim, rectDim, false);
        Bitmap dogYellowSmall = Bitmap.createScaledBitmap(dogYellow, rectDim, rectDim, false);
        Bitmap dogRedSmall = Bitmap.createScaledBitmap(dogRed, rectDim, rectDim, false);

        Bitmap birdBlueSmall = Bitmap.createScaledBitmap(birdBlue, rectDim, rectDim, false);
        Bitmap birdGreenSmall = Bitmap.createScaledBitmap(birdGreen, rectDim, rectDim, false);
        Bitmap birdOrangeSmall = Bitmap.createScaledBitmap(birdOrange, rectDim, rectDim, false);
        Bitmap birdPurpleSmall = Bitmap.createScaledBitmap(birdPurple, rectDim, rectDim, false);
        Bitmap birdYellowSmall = Bitmap.createScaledBitmap(birdYellow, rectDim, rectDim, false);
        Bitmap birdRedSmall = Bitmap.createScaledBitmap(birdRed, rectDim, rectDim, false);

        Bitmap foxBlueSmall = Bitmap.createScaledBitmap(foxBlue, rectDim, rectDim, false);
        Bitmap foxGreenSmall = Bitmap.createScaledBitmap(foxGreen, rectDim, rectDim, false);
        Bitmap foxOrangeSmall = Bitmap.createScaledBitmap(foxOrange, rectDim, rectDim, false);
        Bitmap foxPurpleSmall = Bitmap.createScaledBitmap(foxPurple, rectDim, rectDim, false);
        Bitmap foxYellowSmall = Bitmap.createScaledBitmap(foxYellow, rectDim, rectDim, false);
        Bitmap foxRedSmall = Bitmap.createScaledBitmap(foxRed, rectDim, rectDim, false);

        canvas.drawColor(Color.WHITE);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3.0f);
        blackPaint.setStyle(Paint.Style.STROKE);


        int offset = (canvas.getWidth() - (scaleDim*rectDim)) / 2;
        for (int i = 0; i< scaleDim; i++){
            for (int j = 0; j< scaleDim; j++){
                canvas.drawRect((float)(i*rectDim+offset), (float)(j*rectDim), (float)((i+1)*rectDim+offset), (float)(j+1)*rectDim, blackPaint);
            }
        }

        canvas.drawBitmap(dogRedSmall, (float)(3*rectDim+offset), (float)(2*rectDim), blackPaint);
        canvas.drawBitmap(dogBlueSmall, (float)(3*rectDim+offset), (float)(3*rectDim), blackPaint);
        canvas.drawBitmap(dogGreenSmall, (float)(3*rectDim+offset), (float)(4*rectDim), blackPaint);
        canvas.drawBitmap(dogYellowSmall, (float)(3*rectDim+offset), (float)(5*rectDim), blackPaint);

        canvas.drawBitmap(birdYellowSmall, (float)(2*rectDim+offset), (float)(5*rectDim), blackPaint);
        canvas.drawBitmap(snakeYellowSmall, (float)(4*rectDim+offset), (float)(5*rectDim), blackPaint);
        canvas.drawBitmap(foxYellowSmall, (float)(5*rectDim+offset), (float)(5*rectDim), blackPaint);

        canvas.drawBitmap(foxBlueSmall, (float)(5*rectDim+offset), (float)(6*rectDim), blackPaint);
        canvas.drawBitmap(foxGreenSmall, (float)(5*rectDim+offset), (float)(7*rectDim), blackPaint);

        canvas.drawBitmap(dogOrangeSmall, (float)(3*rectDim+offset), (float)(6*rectDim), blackPaint);
        canvas.drawBitmap(dogPurpleSmall, (float)(3*rectDim+offset), (float)(7*rectDim), blackPaint);


    }
}
