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

    private Bitmap bitmapMain;
    private Bitmap bitmapSide;
    private Bitmap bitmapSideSelected;

    private int xPos;
    private int yPos;
    private QwirkleAnimal qwirkleAnimal;
    private QwirkleColor qwirkleColor;

    // Boolean to see if tile belongs to main board (true) or side board (false)
    private boolean mainBoard;

    private boolean isSelected;

    // Constants to make bitmaps draw properly on the screen.
    public static int RECTDIM_MAIN = 74;
    public static int RECTDIM_SIDE = 175;
    public static int OFFSET_MAIN = 2;
    public static int OFFSET_SIDE = 26;

    public QwirkleTile(QwirkleAnimal animal, QwirkleColor color) {
        this.qwirkleAnimal = animal;
        this.qwirkleColor = color;
        initBitmapInstance();
    }

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
        initBitmapInstance();
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
        initBitmapInstance();
    }

    @Override
    public String toString() {
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

    private void initBitmapInstance() {
        if (tileImages == null) return;
        Bitmap bitmap = tileImages.get(this.toString());
        bitmapMain = Bitmap.createScaledBitmap(bitmap, RECTDIM_MAIN, RECTDIM_MAIN, false);
        bitmapSide = Bitmap.createScaledBitmap(bitmap, RECTDIM_SIDE, RECTDIM_SIDE, false);

        /*
        External Citation
        Date: 10 April 2018
        Problem: Could not replace color in a bitmap.
        Resource:
        https://stackoverflow.com/questions/7237915/replace-black-color-in-
        bitmap-with-red
        Solution:
        Used a slightly modified version of the StackOverflow code.
        */
        Bitmap bitmapSideCopy = bitmapSide.copy(bitmapSide.getConfig(), true);
        int[] allpixels = new int [bitmapSideCopy.getHeight()*bitmapSideCopy.getWidth()];
        bitmapSideCopy.getPixels(allpixels, 0, bitmapSideCopy.getWidth(), 0, 0,
                bitmapSideCopy.getWidth(), bitmapSideCopy.getHeight());
        for(int i = 0; i < allpixels.length; i++) {
            if (allpixels[i] == Color.BLACK) {
                allpixels[i] = Color.GRAY;
            }
        }
        bitmapSideCopy.setPixels(allpixels, 0, bitmapSideCopy.getWidth(), 0, 0,
                bitmapSideCopy.getWidth(), bitmapSideCopy.getHeight());
        this.bitmapSideSelected = bitmapSideCopy;
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
                        activity.getPackageName());
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
        if (bitmapMain == null || bitmapSide == null) initBitmapInstance();

        // No paint needed to draw the bitmap.
        if (this.mainBoard) {
            canvas.drawBitmap(bitmapMain, xPos*RECTDIM_MAIN+OFFSET_MAIN,
                    yPos*RECTDIM_MAIN, null);
        }
        else {
            Bitmap bitmap;
            if (isSelected) {
                bitmap = bitmapSideSelected;
            }
            else {
                bitmap = bitmapSide;
            }
            canvas.drawBitmap(bitmap, OFFSET_SIDE, yPos*RECTDIM_SIDE, null);
        }
    }

    // Getters
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

    // Setters
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    public void setMainBoard(boolean mainBoard) {
        this.mainBoard = mainBoard;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
