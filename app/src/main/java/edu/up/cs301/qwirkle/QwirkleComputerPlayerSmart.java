package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * A complex computer version of a Qwirkle player. Since this is meant to act as
 * an "hard-mode" opponent for the human player, the actions of this AI include
 * detecting and calculating the best course of action when it comes to placing
 * a tile on the board. The main objective of this AI is to gather as much
 * points as possible with the least amount of tiles.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleComputerPlayerSmart extends GameComputerPlayer {
    /**
     * Constructor for objects of class QwirkleComputerPlayerSmart
     *
     * @param name
     *          the computer player's name
     */
    public QwirkleComputerPlayerSmart(String name) {
        super(name);
    }

    /**
     * Callback method when the game's state has changed
     *
     * @param info
     *          the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
    }
}
