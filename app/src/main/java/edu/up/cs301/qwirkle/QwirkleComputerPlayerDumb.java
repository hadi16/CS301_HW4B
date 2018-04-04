package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.qwirkle.action.PlaceTileAction;

/**
 * Created by Alex Hadi on 4/1/2018.
 */

public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof QwirkleGameState)) return;

        QwirkleGameState state = (QwirkleGameState)info;
        if (state.getTurn() != this.playerNum) return;

        PlaceTileAction action = new PlaceTileAction((GamePlayer)info);
    }
}
