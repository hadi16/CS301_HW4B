package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-move object that a Qwirkle player sends to the game
 * to swap tiles from their hand with ones in the drawpile.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class SwapTileAction extends GameAction {
    // instance variable

    // Array of boolean for tiles to swap
    private boolean[] swapIdx;

    /**
     * Constructor for SwapTileAction
     *
     * @param player
     *          the player making the move
     * @param swapIdx
     *          the index of the tile being swapped
     */
    public SwapTileAction(GamePlayer player, boolean[] swapIdx) {
        // invoke superclass constructor to set the player
        super(player);

        //initialize arguments
        this.swapIdx = new boolean[swapIdx.length];
        for (int i=0; i<swapIdx.length; i++) {
            this.swapIdx[i] = swapIdx[i];
        }
    }

    /**
     * get the index of the tile being swapped
     *
     * @return index of the tile being swapped
     */
    public boolean[] getSwapIdx() {
        return swapIdx;
    }
}
