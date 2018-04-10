package edu.up.cs301.qwirkle;

import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Class: QwirkleHumanPlayer
 * The human player configuration of Qwirkle.
 *
 * @author Alex Hadi
 * @author Stephanie Camacho
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 9, 2018
 */

public class QwirkleHumanPlayer extends GameHumanPlayer {
    private GameMainActivity activity;
    private QwirkleGameState state;

    /**
     * Constructor: QwirkleHumanPlayer
     * Creates a new QwirkleHumanPlayer.
     *
     * @param name The player's name.
     */
    public QwirkleHumanPlayer(String name) {
        super(name);
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.activity = activity;

        activity.setContentView(R.layout.qwirkle_human_player);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (!(info instanceof QwirkleGameState)) return;

        this.state = (QwirkleGameState)info;
    }
}
