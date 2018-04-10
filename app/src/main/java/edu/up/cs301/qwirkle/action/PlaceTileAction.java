package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: PlaceTileAction
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class PlaceTileAction extends GameAction {
    //instance variables: tile position and player hands
    private QwirkleTile playerHands[][];
    private int xPos;
    private int yPos;

    /*public PlaceTileAction(GamePlayer player, QwirkleTile playerHands, int xPos, int yPos) {
        super(player);
    }

    public QwirkleTile[][] getPlayerHands() {
        return playerHands;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }*/
}

