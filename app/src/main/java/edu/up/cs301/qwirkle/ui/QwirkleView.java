package edu.up.cs301.qwirkle.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Hashtable;

import edu.up.cs301.qwirkle.CONST;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: QwirkleView
 * Superclass of both boards. Inherits from View. Stores the Bitmaps.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 19, 2018
 */
public class QwirkleView extends View {
    // Hashtables for all Bitmaps (static)
    private static Hashtable<String, Bitmap> mainBoardBitmaps = null;
    private static Hashtable<String, Bitmap> sideBoardBitmaps = null;
    private static Hashtable<String, Bitmap> selectedSideBoardBitmaps = null;

    protected QwirkleGameState gameState; // Instance of the game state.
    protected Paint gridPaint; // for drawing board
    protected boolean nightMode; // for night mode

    /**
     * Constructor: QwirkleView
     * @param context The context of the view.
     */
    public QwirkleView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor: QwirkleView
     * @param context The context of the view.
     * @param attrs The attributes of the view.
     */
    public QwirkleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor: QwirkleView
     * @param context The context of the view.
     * @param attrs The attributes of the view.
     * @param defStyleAttr The style attribute.
     */
    public QwirkleView(Context context, @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Constructor: QwirkleView
     * @param context The context of the view.
     * @param attrs The attributes of the view.
     * @param defStyleAttr The style attribute.
     * @param defStyleRes The style resource.
     */
    public QwirkleView(Context context, @Nullable AttributeSet attrs,
                       int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * Method: init
     * The gridPaint is initialized and the night mode is initially false.
     */
    private void init() {
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStrokeWidth(3.0f);
        gridPaint.setStyle(Paint.Style.STROKE);

        nightMode = false;
    }

    /**
     * Method: drawTile
     * Used to draw a bitmap onto a view given a Canvas object.
     * @param canvas Canvas object to allow the bitmap to be drawn.
     * @param tile The tile to draw.
     */
    protected void drawTile(Canvas canvas, QwirkleTile tile) {
        Bitmap bitmap;
        int rectDim, offset;
        if (tile.isMainBoard()) {
            bitmap = mainBoardBitmaps.get(tile.toString());
            rectDim = CONST.RECTDIM_MAIN;
            offset = CONST.OFFSET_MAIN;
        }
        else {
            rectDim = CONST.RECTDIM_SIDE;
            offset = CONST.OFFSET_SIDE;
            if (tile.isSelected()) {
                bitmap = selectedSideBoardBitmaps.get(tile.toString());
            } else {
                bitmap = sideBoardBitmaps.get(tile.toString());
            }
        }

        // No paint needed to draw the bitmap.
        canvas.drawBitmap(bitmap, tile.getXPos()*rectDim+offset,
                tile.getYPos()*rectDim, null);
    }

    /**
     * Method: initBitmaps
     * Puts all bitmaps in the mainBoardBitmaps Hashtable.
     * Called from QwirkleMainActivity.
     *
     * @param activity activity to get the resources
     */
    public static void initBitmaps(Activity activity) {
        // Return if the hash tables are set
        if (mainBoardBitmaps != null && sideBoardBitmaps != null
                && selectedSideBoardBitmaps != null) {
            return;
        }

        // Instantiate the Hashtables.
        mainBoardBitmaps = new Hashtable<>();
        sideBoardBitmaps = new Hashtable<>();
        selectedSideBoardBitmaps = new Hashtable<>();

        // Iterate over all QwirkleAnimals and QwirkleColors.
        for (QwirkleAnimal animal: QwirkleAnimal.values()) {
            for (QwirkleColor color: QwirkleColor.values()) {
                // Create new instance of tile to get toString value
                QwirkleTile tile = new QwirkleTile(animal, color);
                String pressedName = tile.toString() + "_pressed";
                /*
                External Citation
                Date: 17 February 2018
                Problem: Could not get the resource ID based on idName.
                Resources:
                1) https://stackoverflow.com/questions/6583843/
                   how-to-access-resource-with-dynamic-name-in-my-case
                2) https://kodejava.org/how-do-i-get-package-name-of-a-class/
                3) https://stackoverflow.com/questions/15488238/
                   using-android-getidentifier
                Solution:
                Used the getIdentifier method with the getPackage method.
                 */
                /*
                External Citation
                Date: 9 April 2018
                Problem: Could not get the bitmap to copy.
                Resource:
                https://stackoverflow.com/questions/17044439/how-can-i-copy-
                bitmap-to-another-bitmap-without-using-createbitmap-and-copy
                Solution:
                Used the copy method on the Bitmap.
                */

                // Get the regular Bitmaps.
                int regularId = activity.getResources().getIdentifier(
                        tile.toString(), "drawable", activity.getPackageName());
                Bitmap regularBitmap = BitmapFactory.decodeResource(
                        activity.getResources(), regularId);
                Bitmap regularBitmapMainBoard = Bitmap.createScaledBitmap(
                        regularBitmap, CONST.RECTDIM_MAIN, CONST.RECTDIM_MAIN,
                        false);
                Bitmap regularBitmapSideBoard = Bitmap.createScaledBitmap(
                        regularBitmap, CONST.RECTDIM_SIDE, CONST.RECTDIM_SIDE,
                        false);

                // Get the selected Bitmaps.
                int pressedId = activity.getResources().getIdentifier
                        (pressedName, "drawable", activity.getPackageName());
                Bitmap selectedBitmap = BitmapFactory.decodeResource(
                        activity.getResources(), pressedId);
                Bitmap selectedBitmapSideBoard = Bitmap.createScaledBitmap(
                        selectedBitmap, CONST.RECTDIM_SIDE, CONST.RECTDIM_SIDE,
                        false);

                // Add id and bitmaps to hash tables.
                mainBoardBitmaps.put(tile.toString(), regularBitmapMainBoard);
                sideBoardBitmaps.put(tile.toString(), regularBitmapSideBoard);
                selectedSideBoardBitmaps.put(tile.toString(),
                        selectedBitmapSideBoard);
            }
        }
    }

    /**
     * Method: setNightModeAndUpdateColor
     * The night mode boolean is set and the color is updated accordingly.
     * @param nightMode True if in night mode, otherwise false.
     */
    public void setNightModeAndUpdateColor(boolean nightMode) {
        this.nightMode = nightMode;
        gridPaint.setColor(nightMode ? Color.WHITE : Color.BLACK);
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
