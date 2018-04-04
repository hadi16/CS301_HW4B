package edu.up.cs301.qwirkle.tile;

/**
 * Enum: QColor
 *
 * @author Alex Hadi
 * @author Stephanie Camacho
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 3, 2018
 */
public enum QColor {
    blue, green, orange, purple, red, yellow;

    public char shortName() {
        return this.toString().charAt(0);
    }

    public String longName() {
        String s = this.toString();
        return s.substring(0,1)+s.substring(1);
    }
}
