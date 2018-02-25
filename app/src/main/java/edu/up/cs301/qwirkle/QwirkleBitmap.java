package edu.up.cs301.qwirkle;

import android.graphics.Bitmap;

/**
 * Class: QwirkleBitmap
 * This class contains code for QwirkleBitmap object. Sent to QwirkleTile.
 *
 * @author Alex Hadi
 * @version February 24, 2018
 */
class QwirkleBitmap {
    private String animal;
    private String color;
    private Bitmap tile;

    /**
     * Constructor: QwirkleBitmap
     * @param animal The tile's animal.
     * @param color The tile's color.
     * @param tile The tile itself.
     */
    QwirkleBitmap(String animal, String color, Bitmap tile) {
        this.animal = animal;
        this.color = color;
        this.tile = tile;
    }

    // Getters
    String getAnimal() {
        return animal;
    }
    String getColor() {
        return color;
    }
    Bitmap getTile() {
        return tile;
    }
}
