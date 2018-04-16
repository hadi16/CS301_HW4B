package edu.up.cs301.qwirkle.action;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Class: PassAction
 * Allows the player to just simply pass.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 16, 2018
 */
public class PassAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 2819384729183940183L;
    public PassAction(GamePlayer player) {
        super(player);
    }
}
