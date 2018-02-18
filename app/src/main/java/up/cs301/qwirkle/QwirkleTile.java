package up.cs301.qwirkle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Class: QwirkleTile
 * This class contains code to draw tiles using given Bitmap and dimensions.
 *
 * @author Alex Hadi
 * @version February 18, 2018
 */
class QwirkleTile {
    private Bitmap tile;
    private int xPos;
    private int yPos;
    private int rectDim;
    private int offset;

    /**
     * Constructor: QwirkleTile
     * Used for the main board.
     * @param xPos The x position of the tile.
     * @param yPos The y position of the tile.
     * @param tile The Bitmap for the tile.
     */
    QwirkleTile(int xPos, int yPos, Bitmap tile) {
        this.rectDim = 97;
        this.offset = 98;

        this.xPos = xPos;
        this.yPos = yPos;
        this.tile = Bitmap.createScaledBitmap(tile, rectDim, rectDim, false);
    }

    /**
     * Constructor: QwirkleTile
     * Used for the SideBoards.
     * @param yPos The y position of the tile.
     * @param tile The Bitmap for the tile.
     */
    QwirkleTile(int yPos, Bitmap tile) {
        this.rectDim = 189;
        this.offset = 75;

        this.yPos = yPos;
        this.tile = Bitmap.createScaledBitmap(tile, rectDim, rectDim, false);
    }

    /**
     * Method: drawTile
     * Used to draw a tile onto a view given a Canvas object.
     * @param canvas Canvas object to allow the tile to be drawn.
     */
    void drawTile(Canvas canvas) {
        // No paint needed to draw the bitmap.
        canvas.drawBitmap(tile, xPos*rectDim+offset, yPos*rectDim, null);
    }
}