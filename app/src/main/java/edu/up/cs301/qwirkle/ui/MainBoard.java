package edu.up.cs301.qwirkle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import edu.up.cs301.qwirkle.CONST;
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
    private Paint blackPaint;

    //Instance of the game state
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

    /**
     * Method: initPaint
     * Set the color and stroke width of the paint
     */
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
        // Sets background color to white.
        canvas.drawColor(Color.WHITE);

        // Draws the board.
        for (int i = 0; i< CONST.BOARD_WIDTH; i++){
            for (int j = 0; j< CONST.BOARD_HEIGHT; j++){
                canvas.drawRect(i*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN, j*CONST.RECTDIM_MAIN,
                        (i+1)*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN, (j+1)*CONST.RECTDIM_MAIN, blackPaint);
            }
        }
        //If there's nothing in the game state, ignore
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

    /**
     * Method: setGameState
     * set the game state to the current game state
     * @param gameState current game state
     */
    public void setGameState(QwirkleGameState gameState) {
        this.gameState = gameState;
    }
}
