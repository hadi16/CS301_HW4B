package edu.up.cs301.qwirkle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: SideBoard
 * This class contains the code to draw the SideBoard.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 3, 2018
 */
public class SideBoard extends View {
    private Paint blackPaint;

    private QwirkleGameState gameState;
    // Controls how many tiles are in each SideBoard.
    private static final int NUM_TILES = 6;

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     */
    public SideBoard(Context context){
        super(context);
        initPaint();
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        initPaint();
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr,
                     int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3.0f);
        blackPaint.setStyle(Paint.Style.STROKE);
    }



    /**
     * Method: onDraw
     * Given a Canvas object, draws SideBoards.
     * @param canvas Canvas object used to draw SideBoards onto view.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Offset needed to center the SideBoard.
        int rectDim = canvas.getHeight() / NUM_TILES;
        int offset = (canvas.getWidth() - rectDim) / 2;

        // Sets background color to white.
        canvas.drawColor(Color.WHITE);

        // Draws the SideBoard.
        for (int i = 0; i< NUM_TILES; i++){
            canvas.drawRect(offset, i*rectDim, offset+rectDim, (i+1)*rectDim,
                    blackPaint);
        }

        if (gameState == null) {
            return;
        }

        // Tiles are drawn.
        QwirkleTile[] myPlayerHand = gameState.getMyPlayerHand();
        for (QwirkleTile tile : myPlayerHand) {
            if (tile != null) tile.drawTile(canvas);
        }


    }



    public void setGameState(QwirkleGameState gameState) {
        this.gameState = gameState;
    }
}
