package edu.up.cs301.qwirkle;

import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Alex Hadi on 4/1/2018.
 */

public class QwirkleHumanPlayer extends GameHumanPlayer {
    private GameMainActivity activity;
    private QwirkleState state;

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
        if (!(info instanceof QwirkleState)) return;

        this.state = (QwirkleState)info;
    }
}
