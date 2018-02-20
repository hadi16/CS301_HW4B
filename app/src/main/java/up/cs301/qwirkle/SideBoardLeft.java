package up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Class: SideBoardLeft
 * This class contains the code to draw the tiles for the left SideBoard.
 * Inherits from SideBoard.
 *
 * @author Alex Hadi
 * @version February 20, 2018
 */
public class SideBoardLeft extends SideBoard {
    // Array used to hold current state of SideBoardLeft.
    private QwirkleTile[] sideBoardLeft = new QwirkleTile[numTiles];

    /**
     * Constructor: SideBoardLeft
     * @param context Object holds the current context of the view.
     */
    public SideBoardLeft(Context context){
        super(context);
        addTiles();
    }

    /**
     * Constructor: SideBoardLeft
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoardLeft(Context context, AttributeSet attrs){
        super(context, attrs);
        addTiles();
    }

    /**
     * Constructor: SideBoardLeft
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoardLeft(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        addTiles();
    }

    /**
     * Constructor: SideBoardLeft
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoardLeft(Context context, AttributeSet attrs, int defStyleAttr,
                         int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        addTiles();
    }

    /**
     * Method: addTiles
     * Tiles are added to left SideBoard using array.
     */
    private void addTiles() {
        sideBoardLeft[0] = new QwirkleTile(0, createQwirkleBitmap("owl", "blue"));
        sideBoardLeft[1] = new QwirkleTile(1, createQwirkleBitmap("snake", "red"));
        sideBoardLeft[2] = new QwirkleTile(2, createQwirkleBitmap("bird", "green"));
        sideBoardLeft[3] = new QwirkleTile(3, createQwirkleBitmap("fox", "orange"));
        sideBoardLeft[4] = new QwirkleTile(4, createQwirkleBitmap("bird", "purple"));
        sideBoardLeft[5] = new QwirkleTile(5, createQwirkleBitmap("bat", "yellow"));
    }

    /**
     * Method: onDraw
     * Tiles are drawn to SideBoardLeft. Superclass method called.
     * @param canvas Canvas object used to draw tiles onto SideBoardLeft.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Superclass method called.
        super.onDraw(canvas);

        // Tiles are drawn.
        for (QwirkleTile tile : sideBoardLeft) {
            if (tile != null) tile.drawTile(canvas);
        }
    }
}
