package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Class: PlaceTileAction
 * This class allows the player to place a tile on the board.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 11, 2018
 */
public class PlaceTileAction extends GameAction {
    // instance variables: tile position and player hands
    private int xPos;
    private int yPos;
    private int handIdx;

    /**
     * Constructor: PlaceTileAction
     *
     * @param player the player making the move
     * @param xPos the x-position of the spot on the board.
     * @param yPos the y-position of the spot on board
     * @param handIdx index of tile in hand
     */
    public PlaceTileAction(GamePlayer player, int xPos, int yPos, int handIdx) {
        // invoke superclass constructor to set the player
        super(player);

        // initialize instance variables
        this.xPos = xPos;
        this.yPos = yPos;
        this.handIdx = handIdx;
    }

    /**
     * Method: getxPos
     * Gets the x-position.
     *
     * @return x-position on board
     */
    public int getxPos() {
        return xPos;
    }
    /**
     * Method: getyPos
     * Gets the y-position
     *
     * @return y-position on board
     */
    public int getyPos() {
        return yPos;
    }
    /**
     * Method: getHandIdx
     * Gets the index of tile in hand
     *
     * @return index of tile in hand
     */
    public int getHandIdx() {
        return handIdx;
    }
}

