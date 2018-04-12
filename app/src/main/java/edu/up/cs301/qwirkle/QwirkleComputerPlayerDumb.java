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
 * A simple computer version of a Qwirkle player. Since this is meant to act as
 * an "easy-mode" opponent for the human player, the actions of this AI include
 * placing random tiles whenever they are valid, without taking into account of
 * scoring and win condition.
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

    /**
     * Constructor for objects of class QwirkleComputerPlayerDumb
     *
     * @param name
     *          the computer player's name
     */
    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }

    /**
     * callback method when the game state has changed
     *
     * @param info
     *          the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // Check whether the rules of the illegal move algorithm and
        // turn algorithm apply to the Dumb AI.
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            return;
        }
        else if (!(info instanceof QwirkleGameState)) {
            return;
        }

        this.gameState = (QwirkleGameState)info;

        if (gameState.getTurn() != playerNum) {
            return;
        }

        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        // make a random move, based on the valid positions
        playRandomMove();
    }

    /**
     * Have the dumb AI place a random tile on the door whenever valid, only on
     * its turn
     */
    private void playRandomMove() {
        // Check each tile in the hand to the whole board to see if there's a
        // valid move

        // Iterate through each tile in the player's hand
        for (int i = 0; i < QwirkleGameState.HAND_NUM; i++){
            //Iterate through each x position
            for (int x = 0; x < MainBoard.BOARD_WIDTH; x++){
                //Iterate through each y position
                for (int y = 0; y < MainBoard.BOARD_HEIGHT; y++) {
                    // if there is a valid move on the board, place the tile
                    // in that spot, using the PlaceTileAction command
                    if (rules.isValidMove(x, y, myPlayerHand[i], board)) {
                        PlaceTileAction action = new PlaceTileAction(this, x, y, i);
                        game.sendAction(action);
                        return;
                    }
                }
            }
        }

        // Allow the tiles in the computer's hand to be swapped out with random
        // ones from the drawpile.
        boolean[] swap = new boolean[QwirkleGameState.HAND_NUM];
        for (int i=0; i<swap.length; i++) {
            swap[i] = false;
        }
        int idx = new Random().nextInt(swap.length);
        swap[idx] = true;
        SwapTileAction sta = new SwapTileAction(this, swap);

        //Call the SwapTileAction method to switch out tiles from a hand
        game.sendAction(sta);
    }
}
