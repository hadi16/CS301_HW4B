package edu.up.cs301.qwirkle.player;

import java.util.Collections;
import java.util.Hashtable;

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
 * Class: QwirkleComputerPlayerSmart
 * A complex computer version of a Qwirkle player. Since this is meant to act as
 * a "hard-mode" opponent for the human player, the actions of this AI include
 * detecting and calculating the best possible score when it comes to placing
 * a tile on the board. The main objective of this AI is to gather as many
 * points as possible with the least amount of tiles.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 19, 2018
 */
public class QwirkleComputerPlayerSmart extends GameComputerPlayer {
    // instance variables
    private QwirkleTile[] myPlayerHand; // The player's hand
    private QwirkleTile[][] board; // The board
    private QwirkleGameState gameState; // The game state
    private QwirkleRules rules = new QwirkleRules(); // For valid moves

    /**
     * Constructor: QwirkleComputerPlayerSmart
     * Initializes the smart computer player.
     *
     * @param name the computer player's name
     */
    public QwirkleComputerPlayerSmart(String name) {
        super(name);
    }

    /**
     * Method: receiveInfo
     * Callback method when the game's state has changed
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
        gameState = (QwirkleGameState)info;
        if (gameState.getTurn() != playerNum) {
            return;
        }

        // Initialize board and myPlayerHand.
        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        // Make a random move based on the valid move algorithm.
        playSmartMove();
    }

    /**
     * Method: playSmartMove
     * Action method to determine the next move the smart AI will perform that
     */
    private void playSmartMove() {
        // Sleep for the computer player.
        sleep(CONST.COMPUTER_PLAYER_TIME_TO_SLEEP);

        //ArrayList that stores all scores and its corresponding PlaceTileAction
        Hashtable<Integer, PlaceTileAction> allScores = new Hashtable<>();
        for (int handIdx=0; handIdx<myPlayerHand.length; handIdx++) {
            QwirkleTile tile = myPlayerHand[handIdx];
            if (tile == null) continue;
            for (int x=0; x<board.length; x++) {
                for (int y=0; y<board[x].length; y++) {
                    if (rules.isValidMove(x, y , tile, board)) {
                        int points = rules.getPoints();
                        PlaceTileAction pta = new PlaceTileAction(this, x, y,
                                handIdx);
                        allScores.put(points, pta);
                    }
                }
            }
        }

        // If the size of allScores isn't 0, valid moves exist.
        if (allScores.size() != 0) {
            /*
            * External Citation
            * Date: April 14 2018
            * Problem: Want to find the max key in the Hashtable.
            * Source:
            * https://stackoverflow.com/questions/24200973/
            * how-to-find-highest-key-value-from-hashmap
            * Solution:
            * Used Collections.max with allScores.keySet().
            */
            // Gets the max score in the Hashtable.
            int maxScore = Collections.max(allScores.keySet());
            PlaceTileAction placeTileAction = allScores.get(maxScore);
            game.sendAction(placeTileAction);
        }

        // If no tiles to swap, just pass.
        if (gameState.getTilesLeft() == 0) {
            PassAction pa = new PassAction(this);
            game.sendAction(pa);
            return;
        }

        // If tiles are in draw pile, allow tiles in the computer's hand to be
        // swapped out with random ones from the draw pile.
        int numToSwap;
        if (gameState.getTilesLeft() > myPlayerHand.length) {
            numToSwap = myPlayerHand.length;
        }
        else {
            numToSwap = gameState.getTilesLeft();
        }
        for (int i=0; i<numToSwap; i++) {
            myPlayerHand[i].setSelected(true);
        }
        SwapTileAction sta = new SwapTileAction(this, myPlayerHand);
        game.sendAction(sta);
    }
}
