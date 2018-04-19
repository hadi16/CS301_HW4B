package edu.up.cs301.qwirkle;

/**
 * Class: CONST
 * Class to hold all of the constants. Only has static constants.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 19, 2018
 */
public class CONST {
    // Constant for delay in milliseconds for the computer players
    public static final int COMPUTER_PLAYER_TIME_TO_SLEEP = 1000;

    // Number of rows and columns for the board.
    public static final int BOARD_WIDTH = 21;
    public static final int BOARD_HEIGHT = 16;

    // A constant for number of tiles a player can have in their hand
    public static final int NUM_IN_HAND = 6;

    // Constants to make bitmaps draw properly on the screen (set at runtime).
    public static int RECTDIM_MAIN;
    public static int RECTDIM_SIDE;
    public static int OFFSET_MAIN;
    public static int OFFSET_SIDE;
}
