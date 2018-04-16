package edu.up.cs301.qwirkle.tile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.Serializable;
import java.util.Hashtable;

import edu.up.cs301.qwirkle.CONST;

/**
 * Class: QwirkleTile
 * This class contains code to draw tiles using given Bitmap and dimensions.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 16, 2018
 */
public class QwirkleTile implements Serializable {
    // Hashtable for all Bitmaps
    private static Hashtable<String, Bitmap> mainBoardBitmaps = null;
    private static Hashtable<String, Bitmap> sideBoardBitmaps = null;
    private static Hashtable<String, Bitmap> selectedSideBoardBitmaps = null;

    // Instance variables for bitmaps
    private Bitmap bitmapMain;
    private Bitmap bitmapSide;
    private Bitmap bitmapSideSelected;

    private int xPos; // x position of tile
    private int yPos; // y position of tile
    private QwirkleAnimal qwirkleAnimal; // Animal of tile
    private QwirkleColor qwirkleColor; // Color of tile

    // Booleans to tell if tiles are on main board and if selected.
    private boolean mainBoard;
    private boolean isSelected;

    /**
     * Constructor: QwirkleTile
     * Deep copy constructor for QwirkleTile
     *
     * @param orig The original QwirkleTile object instance.
     */
    public QwirkleTile(QwirkleTile orig) {
        // Set position and tile animal/color of bitmaps
        this.xPos = orig.xPos;
        this.yPos = orig.yPos;
        this.qwirkleAnimal = orig.qwirkleAnimal;
        this.qwirkleColor = orig.qwirkleColor;

        // Set the mainBoard and isSelected booleans
        this.mainBoard = orig.mainBoard;
        this.isSelected = orig.isSelected;

        // Initialize the Bitmaps.
        initBitmapInstance();
    }

    /**
     * Constructor: QwirkleTile
     * Use to create a new tile from scratch.
     *
     * @param animal QwirkleAnimal for bitmap
     * @param color QwirkleColor for bitmap
     */
    public QwirkleTile(QwirkleAnimal animal, QwirkleColor color) {
        this.qwirkleAnimal = animal;
        this.qwirkleColor = color;

        //Initialize the Bitmaps.
        initBitmapInstance();
    }

    /**
     * Method: toString
     * Returns a string representation of the tile.
     *
     * @return The string to represent the tile.
     */
    @Override
    public String toString() {
        // Use underscore to separate animal from color.
        return qwirkleAnimal.toString()+"_"+qwirkleColor.toString();
    }

    /**
     * Method: initBitmapInstance
     * Initialize all bitmaps (returns if all set).
     */
    private void initBitmapInstance() {
        // Check to make sure all hash tables are set.
        if (mainBoardBitmaps == null || sideBoardBitmaps == null ||
                selectedSideBoardBitmaps == null) {
            return;
        }

        // If all the bitmaps are set, ignore.
        if (bitmapMain != null && bitmapSide != null &&
                bitmapSideSelected != null) {
            return;
        }

        // Get and set the bitmaps.
        bitmapMain = mainBoardBitmaps.get(this.toString());
        bitmapSide = sideBoardBitmaps.get(this.toString());
        bitmapSideSelected = selectedSideBoardBitmaps.get(this.toString());
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
                String regularName = animal.toString()+"_"+color.toString();
                String pressedName = regularName + "_pressed";
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
                int regularId = activity.getResources().getIdentifier(regularName,
                        "drawable", activity.getPackageName());
                Bitmap regularBitmap = BitmapFactory.decodeResource(
                        activity.getResources(), regularId);
                Bitmap regularBitmapMainBoard = Bitmap.createScaledBitmap(
                        regularBitmap, CONST.RECTDIM_MAIN, CONST.RECTDIM_MAIN,
                        false);
                Bitmap regularBitmapSideBoard = Bitmap.createScaledBitmap(
                        regularBitmap, CONST.RECTDIM_SIDE, CONST.RECTDIM_SIDE,
                        false);

                // Get the selected Bitmaps.
                int pressedId = activity.getResources().getIdentifier(pressedName,
                        "drawable", activity.getPackageName());
                Bitmap selectedBitmap = BitmapFactory.decodeResource(
                        activity.getResources(), pressedId);
                Bitmap selectedBitmapSideBoard = Bitmap.createScaledBitmap(
                        selectedBitmap, CONST.RECTDIM_SIDE, CONST.RECTDIM_SIDE,
                        false);

                // Add id and bitmaps to hash tables.
                mainBoardBitmaps.put(regularName, regularBitmapMainBoard);
                sideBoardBitmaps.put(regularName, regularBitmapSideBoard);
                selectedSideBoardBitmaps.put(regularName, selectedBitmapSideBoard);
            }
        }
    }

    /**
     * Method: drawTile
     * Used to draw a bitmap onto a view given a Canvas object.
     * @param canvas Canvas object to allow the bitmap to be drawn.
     */
    public void drawTile(Canvas canvas) {
        // Bitmaps must be initialized first if they are null.
        if (bitmapMain == null || bitmapSide == null ||
                bitmapSideSelected == null) {
            initBitmapInstance();
        }

        // No paint needed to draw the bitmap.
        if (this.mainBoard) {
            canvas.drawBitmap(bitmapMain, xPos*CONST.RECTDIM_MAIN+CONST.OFFSET_MAIN,
                    yPos*CONST.RECTDIM_MAIN, null);
        }
        else {
            // SideBoard bitmaps can be selected or not.
            Bitmap bitmap;
            if (isSelected) {
                bitmap = bitmapSideSelected;
            }
            else {
                bitmap = bitmapSide;
            }
            canvas.drawBitmap(bitmap, CONST.OFFSET_SIDE, yPos*CONST.RECTDIM_SIDE,
                    null);
        }
    }

    // Getters
    /**
     * Method: getQwirkleAnimal
     * @return the QwirkleAnimal of bitmap
     */
    public QwirkleAnimal getQwirkleAnimal() {
        return qwirkleAnimal;
    }
    /**
     * Method: getQwirkleColor
     * @return the QwirkleColor of bitmap
     */
    public QwirkleColor getQwirkleColor() {
        return qwirkleColor;
    }
    public boolean isSelected() {
        return isSelected;
    }

    // Setters
    /**
     * Method: setXPos
     * Set x position of bitmap
     * @param xPos x coordinate to set
     */
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    /**
     * Method: setYPos
     * Set y position of bitmap
     * @param yPos y coordinate to set
     */
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    /**
     * Method: setMainBoard
     * @param mainBoard True if tile on the main board, otherwise false.
     */
    public void setMainBoard(boolean mainBoard) {
        this.mainBoard = mainBoard;
    }
    /**
     * Method: setSelected
     * Set the selected bitmaps
     * @param selected True if tile is selected, otherwise false.
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
