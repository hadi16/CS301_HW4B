package edu.up.cs301.qwirkle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;
import edu.up.cs301.qwirkle.ui.SideBoard;

/**
 * Class: QwirkleHumanPlayer
 * The human player configuration of Qwirkle.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */

public class QwirkleHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {
    private GameMainActivity activity;
    private QwirkleGameState state;
    private Button buttonSwap;
    private MainBoard mainBoard;
    private SideBoard sideBoard;

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
        QwirkleTile.initBitmaps(activity);
        activity.setContentView(R.layout.qwirkle_human_player);

        buttonSwap = (Button)activity.findViewById(R.id.buttonSwap);
        mainBoard = (MainBoard)activity.findViewById(R.id.mainBoard);
        mainBoard.setOnTouchListener(this);
        sideBoard = (SideBoard)activity.findViewById(R.id.sideBoard);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (!(info instanceof QwirkleGameState)) return;
        this.state = (QwirkleGameState)info;
        mainBoard.setGameState(state);
        sideBoard.setGameState(state);
        mainBoard.invalidate();
        sideBoard.invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (v.getId() == R.id.mainBoard) {
            return true;
        }
        else if (v.getId() == R.id.sideBoard) {
            return true;
        }
        return false;
    }
}
