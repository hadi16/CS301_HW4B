package edu.up.cs301.qwirkle;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;


import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleComputerPlayerDumb
 * The dumb computer player.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    //Instance variables
    private QwirkleGameState gameState;
    private QwirkleTile[] myPlayerHand;
    private QwirkleTile[][] board;
    private QwirkleRules rules = new QwirkleRules();
    private GameMainActivity activity = null;
    private TextView textViewTurnLabel = null;
    private Handler guiHandler;
    private boolean isWinner;

    // In milliseconds
    private static final int TIME_TO_SLEEP = 1000;

    /**
     * Constructor for objects of class QwirkleComputerPlayerDumb
     *
     * @param name
     *          the computer player's name
     */
    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }
    @Override
    public void setAsGui(GameMainActivity a) {
        this.activity = a;
        activity.setContentView(R.layout.qwirkle_human_player);
        this.guiHandler = new Handler();
        this.textViewTurnLabel = (TextView)activity.findViewById(R.id.textViewTurnLabel);

        if(gameState != null) {
            updateDisplay();
        }
    }

    protected void updateDisplay() {
        if (guiHandler != null) {
            guiHandler.post(
                    new Runnable() {
                        public void run() {
                            if (textViewTurnLabel != null && gameState != null) {
                                textViewTurnLabel.setText("Current Turn: " + allPlayerNames[playerNum]);
                            }
                        }
                    }
            );
        }
    }



    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            return;
        }
        else if (!(info instanceof QwirkleGameState)) {
            return;
        }

        this.gameState = (QwirkleGameState)info;
        updateDisplay();
        if (gameState.getTurn() != playerNum) {
            return;
        }

        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        playRandomMove();
    }

    private void playRandomMove() {
        sleep(TIME_TO_SLEEP);

        //Check each tile in the hand to the whole board to see if there's a valid move
        //Iterate through each tile in the player's hand
        // Check each tile in the hand w/ board to see if there's a valid move
        // Iterate through each tile in the player's hand
        for (int i = 0; i < QwirkleGameState.HAND_NUM; i++){
            //Iterate through all x position
            for (int x = 0; x < MainBoard.BOARD_WIDTH; x++){
                //Iterate through all y position
                for (int y = 0; y < MainBoard.BOARD_HEIGHT; y++) {
                    if (rules.isValidMove(x, y, myPlayerHand[i], board)) {
                        PlaceTileAction action = new PlaceTileAction(this,
                                x, y, i);
                        game.sendAction(action);
                        return;
                    }
                }
            }
        }

        boolean[] swap = new boolean[QwirkleGameState.HAND_NUM];
        for (int i=0; i<swap.length; i++) {
            swap[i] = false;
        }
        int idx = new Random().nextInt(swap.length);
        swap[idx] = true;
        SwapTileAction sta = new SwapTileAction(this, swap);

        game.sendAction(sta);
    }
}
