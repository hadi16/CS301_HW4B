package edu.up.cs301.qwirkle;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
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
 * @version April 11, 2018
 */
public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    private QwirkleGameState gameState; // The game state sent to player.
    private QwirkleTile[] myPlayerHand; // The player's hand
    private QwirkleTile[][] board; // The board
    private QwirkleRules rules = new QwirkleRules(); // For valid moves

    // Constant for 1000-millisecond delay
    private static final int TIME_TO_SLEEP = 0;

    /**
     * Constructor: QwirkleComputerPlayerDumb
     * Initializes the computer player.
     *
     * @param name the computer player's name
     */
    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }

    /**
     * Method: receiveInfo
     * Callback method when the game state has changed
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // Check whether the info received is for an illegal move (return)
        if (info instanceof IllegalMoveInfo||info instanceof NotYourTurnInfo) {
            return;
        }
        // Otherwise, must be a game state object.
        else if (!(info instanceof QwirkleGameState)) {
            return;
        }

        this.gameState = (QwirkleGameState)info;
        if (gameState.getTurn() != playerNum) {
            return;
        }

        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        // Make a random move based on the valid move algorithm.
        playRandomMove();
    }

    /**
     * Method: playRandomMove
     * Have the dumb AI place a random tile on the board whenever valid.
     */
    private void playRandomMove() {
        // Sleep for the human player.
        sleep(TIME_TO_SLEEP);

        //Iterate through each tile in the player's hand
        for (int i = 0; i < QwirkleGameState.HAND_NUM; i++){
            //Iterate through all x positions
            for (int x = 0; x < MainBoard.BOARD_WIDTH; x++){
                //Iterate through all y positions
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

        // If no valid moves, allow the tiles in the computer's hand to be
        // swapped out with random ones from the draw pile.
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
