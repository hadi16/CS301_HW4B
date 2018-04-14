package edu.up.cs301.qwirkle.player;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.qwirkle.CONST;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.QwirkleRules;
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
 * @version April 14, 2018
 */
public class QwirkleComputerPlayerSmart extends GameComputerPlayer {
    private QwirkleTile[] myPlayerHand; // The player's hand
    private QwirkleTile[][] board; // The board
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

        QwirkleGameState gameState = (QwirkleGameState)info;
        if (gameState.getTurn() != playerNum) {
            return;
        }

        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        // Make a random move based on the valid move algorithm.
        playSmartMove();
    }

    public void playSmartMove() {
        //ArrayList that stores all score that can be earn
        ArrayList<Integer> allScore = new ArrayList<>();
        for(QwirkleTile tile : myPlayerHand) {
            if(tile == null) continue;
            for(int x = 0; x < CONST.BOARD_WIDTH; x++) {
                for(int y = 0; y < CONST.BOARD_HEIGHT; y++) {
                    if(rules.isValidMove(x, y , tile, board)) {
                        int point = rules.getPoint();
                        allScore.add(point);
                    }
                }
            }
        }
        //Get the highest score in the array
        int highest = allScore.get(0);
        for(int i = 0; i < allScore.size(); i++) {
            if(allScore.get(i) > highest) {
                highest = allScore.get(i);
            }
        }
        //Check all the available moves again to see which one give the highest score
        //Then play that move
        for(int tileIdx = 0; tileIdx < myPlayerHand.length; tileIdx++) {
            QwirkleTile tile = myPlayerHand[tileIdx];
            if(tile == null) continue;
            for(int x = 0; x < CONST.BOARD_WIDTH; x++) {
                for(int y = 0; y < CONST.BOARD_HEIGHT; y++) {
                    if(rules.isValidMove(x, y, tile, board)) {
                        if(rules.getPoint() == highest) {
                            PlaceTileAction pta = new PlaceTileAction(this, x, y, tileIdx);
                            game.sendAction(pta);
                            return;
                        }
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
