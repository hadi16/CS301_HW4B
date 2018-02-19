package up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Class: QwirkleBoard
 * This class contains the code to draw the main Qwirkle board.
 * Inherits from QwirkleView.
 *
 * @author Alex Hadi
 * @version February 19, 2018
 */
public class QwirkleBoard extends QwirkleView {
    // Number of rows and columns for the board.
    private static final int scaleDim = 12;

    // Array for the current state of the board.
    private QwirkleTile board[][] = new QwirkleTile[scaleDim][scaleDim];

    /**
     * Constructor: QwirkleBoard
     * @param context Object holds the current context of the view.
     */
    public QwirkleBoard(Context context){
        super(context);
        addTiles();
    }

    /**
     * Constructor: QwirkleBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public QwirkleBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        addTiles();
    }

    /**
     * Constructor: QwirkleBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public QwirkleBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        addTiles();
    }

    /**
     * Constructor: QwirkleBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public QwirkleBoard(Context context, AttributeSet attrs, int defStyleAttr,
                 int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        addTiles();
    }

    /**
     * Method: addTiles
     * Adds tiles to the board with the board array.
     */
    private void addTiles() {
        board[3][2] = new QwirkleTile(3, 2, createQwirkleBitmapObject("dog", "red"));
        board[3][3] = new QwirkleTile(3, 3, createQwirkleBitmapObject("dog", "blue"));
        board[3][4] = new QwirkleTile(3, 4, createQwirkleBitmapObject("dog", "green"));
        board[3][5] = new QwirkleTile(3, 5, createQwirkleBitmapObject("dog", "yellow"));
        board[2][5] = new QwirkleTile(2, 5, createQwirkleBitmapObject("bird", "yellow"));
        board[4][5] = new QwirkleTile(4, 5, createQwirkleBitmapObject("snake", "yellow"));
        board[5][5] = new QwirkleTile(5, 5, createQwirkleBitmapObject("fox", "yellow"));
        board[5][6] = new QwirkleTile(5, 6, createQwirkleBitmapObject("fox", "blue"));
        board[5][7] = new QwirkleTile(5, 7, createQwirkleBitmapObject("fox", "green"));
        board[3][6] = new QwirkleTile(3, 6, createQwirkleBitmapObject("dog", "orange"));
        board[3][7] = new QwirkleTile(3, 7, createQwirkleBitmapObject("dog", "purple"));
    }

    /**
     * Method: onDraw
     * Draws the board and tiles in the array onto the view.
     * @param canvas Canvas object to draw onto the view.
     */
    @Override
    public void onDraw(Canvas canvas){
        //Offset needed to center the board.
        int rectDim = canvas.getHeight() / scaleDim;
        int offset = (canvas.getWidth() - (scaleDim*rectDim)) / 2;

        // Sets background color to white.
        canvas.drawColor(Color.WHITE);

        // Draws the board.
        for (int i = 0; i< scaleDim; i++){
            for (int j = 0; j< scaleDim; j++){
                canvas.drawRect(i*rectDim+offset, j*rectDim,
                        (i+1)*rectDim+offset, (j+1)*rectDim, blackPaint);
            }
        }

        // Draws the tiles.
        for (QwirkleTile[] x : board) {
            for (QwirkleTile tile : x) {
                if (tile != null) tile.drawTile(canvas);
            }
        }
    }
}
