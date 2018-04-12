package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-move object that a player sends to the game
 * to place a tile on the board
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */


public class PlaceTileAction extends GameAction {
    // instance variables: tile position and player hands
    private int xPos;
    private int yPos;
    private int handIdx;

    /**
     * Constructor for PlaceTileAction
     *
     * @param player
     *          the player making the move
     * @param xPos
     *          the x-position of spot on board
     * @param yPos
     *          the y-position of spot on board
     * @param handIdx
     *          Index of tile in hand
     */
    public PlaceTileAction(GamePlayer player, int xPos, int yPos, int handIdx) {
        // invoke superclass constructor to set the player
        super(player);
        // initialize arguments
        this.xPos = xPos;
        this.yPos = yPos;
        this.handIdx = handIdx;
    }

    /**
     * get the x-position
     *
     * @return
     *      x-position on board
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * get the y-position
     *
     * @return
     *      y-position on board
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * get index of tile in hand
     *
     * @return
     *      index of tile in hand
     */
    public int getHandIdx() {
        return handIdx;
    }
}

