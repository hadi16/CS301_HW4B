package edu.up.cs301.qwirkle.player;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.qwirkle.CONST;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.QwirkleRules;
import edu.up.cs301.qwirkle.action.PassAction;
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
 * @version April 19, 2018
 */
public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    // instance variables
    private QwirkleTile[] myPlayerHand; // The player's hand
    private QwirkleTile[][] board; // The board
    private QwirkleGameState gameState; // The game state
    private QwirkleRules rules = new QwirkleRules(); // For valid moves

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

        // Initialize game state and check for valid turn.
        this.gameState = (QwirkleGameState)info;
        if (this.gameState.getTurn() != this.playerNum) {
            return;
        }

        // Initialize board and myPlayerHand.
        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        // Make a random move based on the valid move algorithm.
        playRandomMove();
    }

    /**
     * Method: playRandomMove
     * Have the dumb AI place a random tile on the board whenever valid.
     * Otherwise, it will swap or pass.
     */
    private void playRandomMove() {
        // Sleep for the computer player.
        sleep(CONST.COMPUTER_PLAYER_TIME_TO_SLEEP);

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

        // If there are no tiles to swap, just pass.
        if (gameState.getTilesLeft() == 0) {
            PassAction pa = new PassAction(this);
            game.sendAction(pa);
            return;
        }

        // If there are tiles left, allow one tile in the computer's hand to be
        // swapped out with a random one from the draw pile.
        int idx = new Random().nextInt(myPlayerHand.length);
        myPlayerHand[idx].setSelected(true);
        SwapTileAction sta = new SwapTileAction(this, myPlayerHand);
        game.sendAction(sta);
    }
}
