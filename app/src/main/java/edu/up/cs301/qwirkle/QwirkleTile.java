package edu.up.cs301.qwirkle;

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
 * @version February 24, 2018
 */
public class QwirkleTile {
    // Hashtable for all Bitmaps
    private static Hashtable<String, Bitmap> qwirkleBitmaps;

    // String arrays to hold all combos of the tiles
    // (utilized by initBitmaps method).
    private static final String[] QWIRKLE_COLORS =
            {"blue", "green", "orange", "purple", "red", "yellow"};
    private static final String[] QWIRKLE_ANIMALS =
            {"bat", "owl", "snake", "dog", "parrot", "fox"};

    private Bitmap tileBitmap;
    private int xPos;
    private int yPos;
    private String animal;
    private String color;
    private int rectDim;
    private int offset;

    /**
     * Constructor: QwirkleTile
     * Used for the main board.
     * @param xPos The x position of the tileBitmap.
     * @param yPos The y position of the tileBitmap.
     * @param animal The animal for the tileBitmap.
     * @param color The color for the tileBitmap.
     */
    public QwirkleTile(int xPos, int yPos, String animal, String color) {
        this.rectDim = 74;
        this.offset = 2;

        this.xPos = xPos;
        this.yPos = yPos;

        this.animal = animal;
        this.color = color;

        Bitmap bitmap = qwirkleBitmaps.get(animal+"_"+color);
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        this.tileBitmap = Bitmap.createScaledBitmap(bitmapCopy, rectDim, rectDim, false);
    }

    /**
     * Constructor: QwirkleTile
     * Used for the SideBoards.
     * @param yPos The y position of the tileBitmap.
     * @param animal The animal for the tileBitmap.
     * @param color The color for the tileBitmap.
     */
    public QwirkleTile(int yPos, String animal, String color) {
        this.rectDim = 154;
        this.offset = 36;

        this.yPos = yPos;

        Bitmap bitmap = qwirkleBitmaps.get(animal+"_"+color);
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        this.tileBitmap = Bitmap.createScaledBitmap(bitmapCopy, rectDim, rectDim, false);
    }

    public static void initBitmaps(Activity activity) {
        if (qwirkleBitmaps != null) return;

        qwirkleBitmaps = new Hashtable<>();
        // Iterate over all QwirkleAnimals and QwirkleColors.
        for (String animal: QWIRKLE_ANIMALS) {
            for (String color: QWIRKLE_COLORS) {
                String idName = animal+"_"+color;
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
                if (bitmap != null) qwirkleBitmaps.put(idName, bitmap);
            }
        }
    }

    /**
     * Method: drawTile
     * Used to draw a tileBitmap onto a view given a Canvas object.
     * @param canvas Canvas object to allow the tileBitmap to be drawn.
     */
    public void drawTile(Canvas canvas) {
        // No paint needed to draw the bitmap.
        canvas.drawBitmap(tileBitmap, xPos*rectDim+offset, yPos*rectDim, null);
    }

    public String getAnimal() {
        return animal;
    }
    public String getColor() {
        return color;
    }
}
