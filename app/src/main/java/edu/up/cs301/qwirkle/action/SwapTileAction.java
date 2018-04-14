package edu.up.cs301.qwirkle.action;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: SwapTileAction
 * Allows the player to swap tiles from their hand with ones in the draw pile.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 14, 2018
 */
public class SwapTileAction extends GameAction {
    private ArrayList<Integer> swapIdx;

    public SwapTileAction(GamePlayer player, QwirkleTile[] myPlayerHand) {
        // invoke superclass constructor to set the player
        super(player);

        // Initialize the swap indices
        this.swapIdx = new ArrayList<>();
        for (int i=0; i<myPlayerHand.length; i++) {
            if (myPlayerHand[i].isSelected()) {
                swapIdx.add(i);
            }
        }
    }

    /**
     * Method: getSwapIdx
     * Gets the array of indices for the tiles to swap.
     *
     * @return array that has indices to swap
     */
    public ArrayList<Integer> getSwapIdx() {
        return swapIdx;
    }
}
