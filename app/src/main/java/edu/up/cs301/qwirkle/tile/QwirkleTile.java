package edu.up.cs301.qwirkle.tile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Hashtable;

/**
 * Class: QwirkleTile
 * This class contains code to draw tiles using given Bitmap and dimensions.
 *
 * @author Alex Hadi
 * @author Stephanie Camacho
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 5, 2018
 */
public class QwirkleTile {
    // Hashtable for all Bitmaps
    private static Hashtable<String, Bitmap> tileImages = null;

    private Bitmap bitmap;
    private int xPos;
    private int yPos;
    private QwirkleAnimal qwirkleAnimal;
    private QwirkleColor qwirkleColor;

    // Boolean to see if tile belongs to main board (true) or side board (false)
    private boolean mainBoard;

    // Constants to make bitmaps draw properly on the screen.
    private static final int RECTDIM_MAIN = 74;
    private static final int RECTDIM_SIDE = 154;
    private static final int OFFSET_MAIN = 2;
    private static final int OFFSET_SIDE = 36;

    /**
     * Constructor: QwirkleTile
     * Used for the main board.
     * @param xPos The x position of the bitmap.
     * @param yPos The y position of the bitmap.
     * @param animal The QwirkleAnimal for the bitmap.
     * @param color The QwirkleColor for the bitmap.
     */
    public QwirkleTile(int xPos, int yPos, QwirkleAnimal animal, QwirkleColor color) {
        this.mainBoard = true;
        this.xPos = xPos;
        this.yPos = yPos;
        this.qwirkleAnimal = animal;
        this.qwirkleColor = color;
    }

    /**
     * Constructor: QwirkleTile
     * Used for the SideBoards.
     * @param yPos The y position of the bitmap.
     * @param animal The QwirkleAnimal for the bitmap.
     * @param color The QwirkleColor for the bitmap.
     */
    public QwirkleTile(int yPos, QwirkleAnimal animal, QwirkleColor color) {
        this.mainBoard = false;
        this.yPos = yPos;
        this.qwirkleAnimal = animal;
        this.qwirkleColor = color;
    }

    @Override
    public String toString() {
        return qwirkleAnimal.longName()+"_"+qwirkleColor.longName();
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
     * Checks two QwirkleTile objects are equivalent. Needed to make HashTable work.
     * @param o Object to be checked.
     * @return true if objects are equivalent, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        // Check to make sure it is a QwirkleTile.
        if (o instanceof QwirkleTile) {
            // Cast object as QwirkleTile
            QwirkleTile tile = (QwirkleTile)o;
            return this.qwirkleAnimal.equals(tile.qwirkleAnimal) && this.qwirkleColor.equals(tile.qwirkleColor);
        }
        return false;
    }

    public static void initBitmaps(Activity activity) {
        if (tileImages != null) return;

        tileImages = new Hashtable<>();
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
                int id = activity.getResources().getIdentifier(idName, "drawable",
                        activity.getClass().getPackage().getName());
                Log.i("initBitmaps", Integer.toString(id));
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), id);
                // Add id and bitmap to hash table.
                if (bitmap != null) tileImages.put(idName, bitmap);
            }
        }
    }

    /**
     * Method: drawTile
     * Used to draw a bitmap onto a view given a Canvas object.
     * @param canvas Canvas object to allow the bitmap to be drawn.
     */
    public void drawTile(Canvas canvas) {
        // No paint needed to draw the bitmap.
        if (mainBoard) {
            canvas.drawBitmap(bitmap, xPos*RECTDIM_MAIN+OFFSET_MAIN,
                    yPos*RECTDIM_MAIN, null);
        } else {
            canvas.drawBitmap(bitmap, OFFSET_SIDE, yPos*RECTDIM_SIDE, null);
        }
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public QwirkleAnimal getQwirkleAnimal() {
        return qwirkleAnimal;
    }

    public QwirkleColor getQwirkleColor() {
        return qwirkleColor;
    }

    public boolean isMainBoard() {
        return mainBoard;
    }
}
