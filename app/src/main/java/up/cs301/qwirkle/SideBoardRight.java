package up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Class: SideBoardRight
 * This class contains the code to draw the tiles for the right SideBoard.
 * Inherits from SideBoard.
 *
 * @author Alex Hadi
 * @version February 18, 2018
 */
public class SideBoardRight extends SideBoard {
    // Array used to hold current state of SideBoardRight.
    private QwirkleTile[] sideBoardRight = new QwirkleTile[numTiles];

    /**
     * Constructor: SideBoardRight
     * @param context Object holds the current context of the view.
     */
    public SideBoardRight(Context context){
        super(context);
        addTiles();
    }

    /**
     * Constructor: SideBoardRight
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoardRight(Context context, AttributeSet attrs){
        super(context, attrs);
        addTiles();
    }

    /**
     * Constructor: SideBoardRight
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoardRight(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        addTiles();
    }

    /**
     * Constructor: SideBoardRight
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoardRight(Context context, AttributeSet attrs, int defStyleAttr,
                          int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        addTiles();
    }

    /**
     * Method: addTiles
     * Tiles are added to right SideBoard using array.
     */
    private void addTiles() {
        sideBoardRight[0] = new QwirkleTile(0, getBitmap("owl", "green"));
        sideBoardRight[1] = new QwirkleTile(1, getBitmap("snake", "purple"));
        sideBoardRight[2] = new QwirkleTile(2, getBitmap("bird", "blue"));
        sideBoardRight[3] = new QwirkleTile(3, getBitmap("fox", "red"));
        sideBoardRight[4] = new QwirkleTile(4, getBitmap("owl", "red"));
        sideBoardRight[5] = new QwirkleTile(5, getBitmap("bat", "orange"));
    }

    /**
     * Method: onDraw
     * Tiles are drawn to SideBoardRight. Superclass method called.
     * @param canvas Canvas object used to draw tiles onto SideBoardRight.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Superclass method called.
        super.onDraw(canvas);

        // Tiles are drawn.
        for (QwirkleTile tile : sideBoardRight) {
            if (tile != null) tile.drawTile(canvas);
        }
    }
}
