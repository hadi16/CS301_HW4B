package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

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
    private int xPos;
    private int yPos;
    private int handIdx;

    public PlaceTileAction(GamePlayer player, int xPos, int yPos, int handIdx) {
        super(player);
        this.xPos = xPos;
        this.yPos = yPos;
        this.handIdx = handIdx;
    }

    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public int getHandIdx() {
        return handIdx;
    }
}

