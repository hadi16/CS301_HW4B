package edu.up.cs301.qwirkle.player;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.QwirkleRules;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: QwirkleComputerPlayerDumb
 * The dumb computer player.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 14, 2018
 */
public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    private QwirkleTile[] myPlayerHand; // The player's hand
    private QwirkleTile[][] board; // The board
    private QwirkleRules rules = new QwirkleRules(); // For valid moves

    // Constant for 1000-millisecond delay
    private static final int TIME_TO_SLEEP = 1000;

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

        QwirkleGameState gameState = (QwirkleGameState)info;
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
        for (int tileIdx=0; tileIdx<myPlayerHand.length; tileIdx++) {
            QwirkleTile tile = myPlayerHand[tileIdx];
            if (tile == null) continue;

            //Iterate through all x positions
            for (int x=0; x<board.length; x++) {
                //Iterate through all y positions
                for (int y=0; y<board[x].length; y++) {
                    if (rules.isValidMove(x, y, tile, board)) {
                        PlaceTileAction action = new PlaceTileAction(this,
                                x, y, tileIdx);
                        game.sendAction(action);
                        return;
                    }
                }
            }
        }

        // If no valid moves, allow the tiles in the computer's hand to be
        // swapped out with random ones from the draw pile.
        Random rand = new Random();
        int idx = rand.nextInt(myPlayerHand.length);
        // To prevent a null position from being selected.
        while (myPlayerHand[idx] == null) {
            idx = rand.nextInt(myPlayerHand.length);
        }
        myPlayerHand[idx].setSelected(true);
        SwapTileAction sta = new SwapTileAction(this, myPlayerHand);
        game.sendAction(sta);
    }
}
