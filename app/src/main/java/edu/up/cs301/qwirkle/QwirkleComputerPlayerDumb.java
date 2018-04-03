package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Alex Hadi on 4/1/2018.
 */

public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
    }
}
