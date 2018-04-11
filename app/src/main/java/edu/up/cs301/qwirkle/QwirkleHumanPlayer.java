package edu.up.cs301.qwirkle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
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
    private String playerName;
    private TextView textViewTurnLabel;
    private TextView textViewScoreLabel;

    /**
     * Constructor: QwirkleHumanPlayer
     * Creates a new QwirkleHumanPlayer.
     *
     * @param name The player's name.
     */
    public QwirkleHumanPlayer(String name) {
        super(name);
        this.playerName = name;
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.activity = activity;
        QwirkleTile.initBitmaps(activity);
        activity.setContentView(R.layout.qwirkle_human_player);

        TextView textViewPlayerLabel = (TextView)activity.findViewById(R.id.textViewPlayerLabel);
        textViewPlayerLabel.setText("My Name: " + name);

        textViewTurnLabel = (TextView)activity.findViewById(R.id.textViewTurnLabel);
        textViewScoreLabel = (TextView)activity.findViewById(R.id.textViewPlayerScore);

        buttonSwap = (Button)activity.findViewById(R.id.buttonSwap);
        mainBoard = (MainBoard)activity.findViewById(R.id.mainBoard);
        mainBoard.setOnTouchListener(this);
        sideBoard = (SideBoard)activity.findViewById(R.id.sideBoard);
        sideBoard.setOnTouchListener(this);
    }

    @Override
    protected void initAfterReady() {
        super.initAfterReady();
        textViewTurnLabel.setText("Current Turn: "+allPlayerNames[playerNum]);
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
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        int x = (int)event.getX();
        int y = (int)event.getY();

        Log.i("X-position", Integer.toString(x));
        Log.i("Y-position", Integer.toString(y));

        if (v.getId() == R.id.mainBoard) {
            // To prevent the user from selecting board before a tile in the hand
            QwirkleTile[] myPlayerHand = state.getMyPlayerHand();
            QwirkleTile handSelected = null;
            int handSelectedIdx = -1;
            for (int i=0; i<myPlayerHand.length; i++) {
                QwirkleTile tile = myPlayerHand[i];
                if (tile.isSelected()) {
                    handSelected = tile;
                    handSelectedIdx = i;
                    break;
                }
            }
            if (handSelected == null) return false;

            // Get where board is selected.
            int[] xyPos = getSelectedBoardIdx(x, y);
            if (xyPos == null) {
                return false;
            }

            PlaceTileAction pta = new PlaceTileAction(this, xyPos[0], xyPos[1], handSelectedIdx);
            state.setBoardAtIdx(x/QwirkleTile.RECTDIM_MAIN,y/QwirkleTile.RECTDIM_MAIN, handSelected);
            //state.setPlayerHandsAtIdx(0, handSelectedIdx, state.getRandomTile());
            game.sendAction(pta);
            mainBoard.invalidate();


            return true;
        }
        else if (v.getId() == R.id.sideBoard) {
            int yPos = getSelectedHandIdx(x, y);
            if (yPos == -1) return false;

            state.resetMyPlayerHandIsSelected();
            state.setMyPlayerHandIsSelectedAtIdx(yPos, true);
            sideBoard.invalidate();
            return false;
        }

        return false;
    }

    private int[] getSelectedBoardIdx(int x, int y) {
        if (x < QwirkleTile.OFFSET_MAIN || x > QwirkleTile.RECTDIM_MAIN * MainBoard.BOARD_WIDTH + QwirkleTile.OFFSET_MAIN) {
            return null;
        }

        int[] xyPos = new int[2];
        // X position
        xyPos[0] = (x - QwirkleTile.OFFSET_MAIN) / QwirkleTile.RECTDIM_MAIN;
        // Y position
        xyPos[1] = y / QwirkleTile.RECTDIM_MAIN;

        return xyPos;
    }

    private int getSelectedHandIdx(int x, int y) {
        if (x < QwirkleTile.OFFSET_SIDE || x > QwirkleTile.RECTDIM_SIDE * QwirkleGameState.HAND_NUM + QwirkleTile.OFFSET_SIDE) {
            return -1;
        }

        return y / QwirkleTile.RECTDIM_SIDE;
    }
}
