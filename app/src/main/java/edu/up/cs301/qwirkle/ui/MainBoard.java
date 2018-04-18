package edu.up.cs301.qwirkle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

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
 * @author Stephanie Camacho
 * @version April 14, 2018
 */
public class MainBoard extends View {
    private Paint gridPaint; // for drawing board
    private Paint greenPaint; // for drawing legal moves


    // Instance of the game state.
    private QwirkleGameState gameState;

    // To draw the legal moves on the board.
    private ArrayList<Point> legalMoves;

    private boolean nightMode;

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     */
    public MainBoard(Context context){
        super(context);
        initMode();
        initPaint();
    }

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public MainBoard(Context context, AttributeSet attrs){
        super(context, attrs);

        initMode();
        initPaint();
    }

    /**
     * Constructor: MainBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public MainBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);

        initMode();
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
        initMode();
        initPaint();
    }

    /**
     * Method: initMode
     * Set the nightMode boolean to false initially
     */
    private void initMode() {
        nightMode = false;
    }

    /**
     * Method: initPaint
     * Set the color and stroke width of the paint
     */
    private void initPaint() {
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStrokeWidth(3.0f);
        gridPaint.setStyle(Paint.Style.STROKE);

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.FILL);
    }

    public void setMode(boolean nightMode){
        this.nightMode = nightMode;
    }



    /**
     * Method: onDraw
     * Draws the board and tiles in the array onto the view.
     * @param canvas Canvas object to draw onto the view.
     */
    @Override
    public void onDraw(Canvas canvas){
        // Sets background color to white or dark grey
        if (nightMode) {
            canvas.drawColor(Color.DKGRAY);
            gridPaint.setColor(Color.WHITE);
        }
        else {
            canvas.drawColor(Color.WHITE);
            gridPaint.setColor(Color.BLACK);
        }


        // Draws the board.
        for (int i = 0; i< CONST.BOARD_WIDTH; i++){
            for (int j = 0; j< CONST.BOARD_HEIGHT; j++){
                canvas.drawRect(i*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN,
                        j*CONST.RECTDIM_MAIN,
                        (i+1)*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN,
                        (j+1)*CONST.RECTDIM_MAIN, gridPaint);
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

        if (legalMoves == null) return;
        for (Point point : legalMoves) {
            int x = point.x;
            int y = point.y;
            canvas.drawRect(x*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN,
                    y*CONST.RECTDIM_MAIN,
                    (x+1)*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN,
                    (y+1)*CONST.RECTDIM_MAIN, greenPaint);
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

    public void setLegalMoves(ArrayList<Point> legalMoves) {
        this.legalMoves = legalMoves;
    }
}
