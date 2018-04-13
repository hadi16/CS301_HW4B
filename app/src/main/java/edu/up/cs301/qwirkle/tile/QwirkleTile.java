package edu.up.cs301.qwirkle.tile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Hashtable;

/**
 * Class: QwirkleTile
 * This class contains code to draw tiles using given Bitmap and dimensions.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleTile {
    // Hashtable for all Bitmaps
    private static Hashtable<String, Bitmap> tileImages = null;
    private static Hashtable<String, Bitmap> selectedTileImages = null;

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

    // Constants to make bitmaps draw properly on the screen.
    public static int RECTDIM_MAIN = 74;
    public static int RECTDIM_SIDE = 175;
    public static int OFFSET_MAIN = 2;
    public static int OFFSET_SIDE = 26;

    /**
     * Constructor: QwirkleTile
     * Deep copy constructor for QwirkleTile
     *
     * @param orig The original QwirkleTile object instance.
     */
    public QwirkleTile(QwirkleTile orig) {
        // Set bitmaps
        this.bitmapMain = orig.bitmapMain;
        this.bitmapSide = orig.bitmapSide;
        this.bitmapSideSelected = orig.bitmapSideSelected;

        // Set position and tile animal/color of bitmaps
        this.xPos = orig.xPos;
        this.yPos = orig.yPos;
        this.qwirkleAnimal = orig.qwirkleAnimal;
        this.qwirkleColor = orig.qwirkleColor;

        // Set the mainBoard and isSelected booleans
        this.mainBoard = orig.mainBoard;
        this.isSelected = orig.isSelected;

        // Initialize the bitmaps (if needed)
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

        //Initialize all bitmaps (if needed)
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

    /*
    External Citation
    Date: 27 February 2018
    Problem: Not sure how to check for object equality.
    Resource:
    https://stackoverflow.com/questions/16069106/how-to-compare-two-java-objects
    Solution: Overrode the equals() method.
    */
    /**
     * Method: equals
     * Checks two QwirkleTile objects are equivalent. Needed for HashTable.
     * @param o Object to be checked.
     * @return true if objects are equivalent, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        // Check to make sure it is a QwirkleTile.
        if (o instanceof QwirkleTile) {
            // Cast object as QwirkleTile
            QwirkleTile tile = (QwirkleTile)o;
            return this.qwirkleAnimal.equals(tile.qwirkleAnimal) &&
                    this.qwirkleColor.equals(tile.qwirkleColor);
        }
        return false;
    }

    /**
     * Method: initBitmapInstance
     * Initialize all bitmaps (returns if all set).
     */
    private void initBitmapInstance() {
        // If the HashTable is null, ignore
        if (tileImages == null) return;

        // If all the bitmaps are set, ignore.
        if (bitmapMain != null && bitmapSide != null &&
                bitmapSideSelected != null) {
            return;
        }

        // Get and set the main bitmaps.
        Bitmap bitmap = tileImages.get(this.toString());
        bitmapMain = Bitmap.createScaledBitmap(bitmap, RECTDIM_MAIN,
                RECTDIM_MAIN, false);
        bitmapSide = Bitmap.createScaledBitmap(bitmap, RECTDIM_SIDE,
                RECTDIM_SIDE, false);

        // Get and set the selected bitmap.
        Bitmap selectedBitmap = selectedTileImages.get(this.toString());
        bitmapSideSelected = Bitmap.createScaledBitmap(selectedBitmap,
                RECTDIM_SIDE, RECTDIM_SIDE, false);
    }

    /**
     * Method: initBitmaps
     * Puts all bitmaps in the tileImages Hashtable.
     * Called from QwirkleMainActivity.
     *
     * @param activity activity to get the resources
     */
    public static void initBitmaps(Activity activity) {
        // Return if the hash tables are set
        if (tileImages != null && selectedTileImages != null) return;

        tileImages = new Hashtable<>();
        selectedTileImages = new Hashtable<>();
        // Iterate over all QwirkleAnimals and QwirkleColors.
        for (QwirkleAnimal animal: QwirkleAnimal.values()) {
            for (QwirkleColor color: QwirkleColor.values()) {
                String idName = animal.toString()+"_"+color.toString();
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
                int id = activity.getResources().getIdentifier(idName,
                        "drawable", activity.getPackageName());
                Bitmap bitmap = BitmapFactory.decodeResource(
                        activity.getResources(), id);
                // Add id and bitmap to hash table.
                if (bitmap != null) tileImages.put(idName, bitmap);

                /*
                External Citation
                Date: 10 April 2018
                Problem: Could not replace color in a bitmap.
                Resource:
                https://stackoverflow.com/questions/7237915/replace-black-
                color-in-bitmap-with-red
                Solution:
                Used a slightly modified version of the StackOverflow code.
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
                Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
                int[] allpixels = new int [bitmapCopy.getHeight()*
                        bitmapCopy.getWidth()];
                bitmapCopy.getPixels(allpixels, 0, bitmapCopy.getWidth(), 0, 0,
                        bitmapCopy.getWidth(), bitmapCopy.getHeight());
                for(int i = 0; i < allpixels.length; i++) {
                    if (allpixels[i] == Color.BLACK) {
                        allpixels[i] = Color.GRAY;
                    }
                }
                bitmapCopy.setPixels(allpixels, 0, bitmapCopy.getWidth(), 0, 0,
                        bitmapCopy.getWidth(), bitmapCopy.getHeight());
                selectedTileImages.put(idName, bitmapCopy);
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

        // To prevent out of memory error.
        if (bitmapSideSelected == null && isSelected) initBitmapInstance();

        // No paint needed to draw the bitmap.
        if (this.mainBoard) {
            canvas.drawBitmap(bitmapMain, xPos*RECTDIM_MAIN+OFFSET_MAIN,
                    yPos*RECTDIM_MAIN, null);
        }
        else {
            // SideBoard bitmaps can be selected or not.
            Bitmap bitmap;
            if (isSelected) bitmap = bitmapSideSelected;
            else bitmap = bitmapSide;
            canvas.drawBitmap(bitmap, OFFSET_SIDE, yPos*RECTDIM_SIDE, null);
        }
    }

    // Getters
    /**
     * Method: getxPos
     * @return x position of bitmap
     */
    public int getxPos() {
        return xPos;
    }
    /**
     * Method: getyPos
     * @return y position of bitmap
     */
    public int getyPos() {
        return yPos;
    }
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

    // Setters
    /**
     * Method: setxPos
     * Set x position of bitmap
     * @param xPos x coordinate to set
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    /**
     * Method: setyPos
     * Set y position of bitmap
     * @param yPos y coordinate to set
     */
    public void setyPos(int yPos) {
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
