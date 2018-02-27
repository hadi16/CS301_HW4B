package edu.up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Hashtable;

/**
 * Class: QwirkleView
 * This class is the superclass that QwirkleBoard and SideBoard inherits from.
 * Within it, the main hash table for the bitmaps and blackPaint are set.
 * Inherits from View.
 *
 * @author Alex Hadi
 * @version February 24, 2018
 */
public class QwirkleView extends View {
    // Hashtable for all Bitmaps
    private Hashtable<String, Bitmap> bitmapHashtable = new Hashtable<>();

    // Paint object used to draw QwirkleBoard and SideBoards.
    protected Paint blackPaint = new Paint();

    // String arrays to hold all combos of the tiles
    // (utilized by createBitmapHashTable method).
    private static final String[] QwirkleColor =
            {"blue", "green", "orange", "purple", "yellow", "red"};
    private static final String[] QwirkleAnimal =
            {"bat", "owl", "snake", "dog", "bird", "fox"};

    /**
     * Constructor: QwirkleView
     * @param context Object holds the current context of the view.
     */
    public QwirkleView(Context context){
        super(context);
        generalInit();
    }

    /**
     * Constructor: QwirkleView
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public QwirkleView(Context context, AttributeSet attrs){
        super(context, attrs);
        generalInit();
    }

    /**
     * Constructor: QwirkleView
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public QwirkleView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        generalInit();
    }

    /**
     * Constructor: QwirkleView
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public QwirkleView(Context context, AttributeSet attrs, int defStyleAttr,
                        int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        generalInit();
    }

    /**
     * Method: generalInit
     * Executed upon object creation (in constructor).
     */
    private void generalInit() {
        createBitmapHashTable();
        setBlackPaint();
        setWillNotDraw(false);
    }

    /**
     * Method: createBitmapHashTable
     * Creates bitmapHashTable that is used to get Bitmap,
     * given an animal and color.
     */
    private void createBitmapHashTable() {
        // Iterate over all QwirkleAnimals and QwirkleColors.
        for (String animal: QwirkleAnimal) {
            for (String color: QwirkleColor) {
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
                int id = getResources().getIdentifier(idName, "drawable",
                        this.getClass().getPackage().getName());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
                // Add idName and bitmap to hash table.
                if (bitmap != null) bitmapHashtable.put(idName, bitmap);
            }
        }
    }

    /**
     * Method: createQwirkleBitmap
     * Getter for the Bitmaps. Used to draw the tiles.
     * @param animal String for the animal of the tile.
     * @param color String for the color of the tile.
     * @return Copy of the Bitmap.
     */
    protected QwirkleBitmap createQwirkleBitmap(String animal, String color) {
        Bitmap bitmap = bitmapHashtable.get(animal+"_"+color);
        /*
        External Citation
        Date: 17 February 2018
        Problem: Wanted to copy the Bitmap so it could be reused for tiles.
        Resource:
        https://stackoverflow.com/questions/17044439/how-can-i-copy-bitmap-
        to-another-bitmap-without-using-createbitmap-and-copy
        Solution: Used the copy method and getConfig method.
        */
        return new QwirkleBitmap(animal, color, bitmap.copy(bitmap.getConfig(), true));
    }

    /**
     * Method: setBlackPaint
     * Helper method to set the color and style of blackPaint.
     */
    private void setBlackPaint() {
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3.0f);
        blackPaint.setStyle(Paint.Style.STROKE);
    }
}
