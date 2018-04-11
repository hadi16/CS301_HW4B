package edu.up.cs301.qwirkle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: MainBoard
 * This class contains the code to draw the main Qwirkle board.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class MainBoard extends View {
    // Number of rows and columns for the board.
    public static final int BOARD_WIDTH = 21;
    public static final int BOARD_HEIGHT = 16;
    private Paint blackPaint;

    private QwirkleGameState gameState;

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     */
    public MainBoard(Context context){
        super(context);
        initPaint();
    }

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public MainBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        initPaint();
    }

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public MainBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public MainBoard(Context context, AttributeSet attrs, int defStyleAttr,
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
     * Draws the board and tiles in the array onto the view.
     * @param canvas Canvas object to draw onto the view.
     */
    @Override
    public void onDraw(Canvas canvas){
        //Offset needed to center the board.
        int rectDim = canvas.getHeight() /BOARD_HEIGHT;
        if (rectDim != QwirkleTile.RECTDIM_MAIN) {
            QwirkleTile.RECTDIM_MAIN = rectDim;
        }
        int offset = (canvas.getWidth() - (BOARD_WIDTH*rectDim)) / 2;
        if (offset != QwirkleTile.OFFSET_MAIN) {
            QwirkleTile.OFFSET_MAIN = offset;
        }

        // Sets background color to white.
        canvas.drawColor(Color.WHITE);

        // Draws the board.
        for (int i = 0; i< BOARD_WIDTH; i++){
            for (int j = 0; j< BOARD_HEIGHT; j++){
                canvas.drawRect(i*rectDim+offset, j*rectDim,
                        (i+1)*rectDim+offset, (j+1)*rectDim, blackPaint);

            }
        }

        if (gameState == null) {
            return;
        }

        // Draws the tiles.
        QwirkleTile[][] board = gameState.getBoard();
        for (QwirkleTile[] x : board) {
            for (QwirkleTile tile : x) {
                if (tile != null) tile.drawTile(canvas);
            }
        }
    }

    public void setGameState(QwirkleGameState gameState) {
        this.gameState = gameState;
    }
}
