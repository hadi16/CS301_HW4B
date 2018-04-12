package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Class: SwapTileAction
 * Allows the player to swap tiles from their hand with ones in the draw pile.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 11, 2018
 */
public class SwapTileAction extends GameAction {
    // Array of boolean for tile indices to swap.
    private boolean[] swapIdx;

    /**
     * Constructor: SwapTileAction
     *
     * @param player the player making the move
     * @param swapIdx the array of indices for the tiles to swap
     */
    public SwapTileAction(GamePlayer player, boolean[] swapIdx) {
        // invoke superclass constructor to set the player
        super(player);

        // initialize instance variables
        this.swapIdx = new boolean[swapIdx.length];
        for (int i=0; i<swapIdx.length; i++) {
            this.swapIdx[i] = swapIdx[i];
        }
    }

    /**
     * Method: getSwapIdx
     * Gets the boolean array of indices for the tiles to swap.
     *
     * @return array that has indices to swap
     */
    public boolean[] getSwapIdx() {
        return swapIdx;
    }
}
