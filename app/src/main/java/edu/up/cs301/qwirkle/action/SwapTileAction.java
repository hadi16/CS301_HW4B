package edu.up.cs301.qwirkle.action;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Class: SwapTileAction
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class SwapTileAction extends GameAction {
    private boolean[] swapIdx;

    public SwapTileAction(GamePlayer player, boolean[] swapIdx) {
        super(player);
        this.swapIdx = new boolean[swapIdx.length];
        for (int i=0; i<swapIdx.length; i++) {
            this.swapIdx[i] = swapIdx[i];
        }
    }

    public boolean[] getSwapIdx() {
        return swapIdx;
    }
}
