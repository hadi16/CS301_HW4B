package edu.up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class: SideBoard
 * This class contains the code to draw both SideBoards.
 * Inherits from QwirkleView.
 *
 * @author Alex Hadi
 * @author Stephanie Camacho
 * @version February 24, 2018
 */
public class SideBoard extends View {
    // Array used to hold current state of SideBoard.
    private QwirkleTile[] sideBoard = new QwirkleTile[numTiles];

    // Controls how many tiles are in each SideBoard.
    protected static final int numTiles = 6;

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     */
    public SideBoard(Context context){
        super(context);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr,
                     int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Method: onDraw
     * Given a Canvas object, draws SideBoards.
     * @param canvas Canvas object used to draw SideBoards onto view.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Offset needed to center the SideBoard.
        int rectDim = canvas.getHeight() / numTiles;
        int offset = (canvas.getWidth() - rectDim) / 2;

        // Sets background color to white.
        canvas.drawColor(Color.WHITE);

        // Draws the SideBoard.
        for (int i = 0; i< sideBoard.length; i++){
            canvas.drawRect(offset, i*rectDim, offset+rectDim, (i+1)*rectDim,
                    blackPaint);
        }

        // Tiles are drawn.
        for (QwirkleTile tile : sideBoard) {
            if (tile != null) tile.drawTile(canvas);
        }
    }
}
