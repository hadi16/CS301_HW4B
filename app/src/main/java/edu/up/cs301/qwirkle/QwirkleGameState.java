package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Created by Alex Hadi on 4/1/2018.
 */

public class QwirkleGameState extends GameState {
    private int turn;
    private int numPlayers;
    private ArrayList<QwirkleTile> drawPile = new ArrayList<>();

    // Number of rows and columns for the board.
    public static final int BOARD_WIDTH = 24;
    public static final int BOARD_HEIGHT = 16;

    // The number of tiles a player can have in their hand is 6
    private static final int HAND_NUM = 6;

    // Array for the current state of the board.
    private QwirkleTile board[][] = new QwirkleTile[BOARD_WIDTH][BOARD_HEIGHT];

    // Array for the player hands.
    private QwirkleTile playerHands[][];

    // Array to store each player's score (index corresponds to playerId).
    private int[] playerScores;

    public QwirkleGameState(QwirkleGameState orig) {
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();
        board = orig.getBoard();
        playerHands = orig.getPlayerHands();
        playerScores = orig.getPlayerScores();
    }

    public boolean hasTilesInPile() {
        return drawPile.size() > 0;
    }

    public int getTurn() {
        return turn;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<QwirkleTile> getDrawPile() {
        return drawPile;
    }

    public int getHandNum() {
        return HAND_NUM;
    }

    public QwirkleTile[][] getPlayerHands() {
        return playerHands;
    }

    public QwirkleTile[][] getBoard() {
        return board;
    }

    public int[] getPlayerScores() {
        return playerScores;
    }
}
