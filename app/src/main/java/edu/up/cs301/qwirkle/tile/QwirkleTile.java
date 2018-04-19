package edu.up.cs301.qwirkle.tile;

import java.io.Serializable;

/**
 * Class: QwirkleTile
 * This class contains code to draw tiles using given Bitmap and dimensions.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 18, 2018
 */
public class QwirkleTile implements Serializable {
    // For serialization.
    private static final long serialVersionUID = 3781304818481394857L;

    // Insyance variables

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

    // Getters
    public int getXPos() {
        return xPos;
    }
    public int getYPos() {
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

    /**
     * Method:
     * @return
     */
    public boolean isSelected() {
        return isSelected;
    }
    public boolean isMainBoard() {
        return mainBoard;
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
