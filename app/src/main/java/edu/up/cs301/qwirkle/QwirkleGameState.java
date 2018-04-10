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

    public QwirkleGameState() {
        this.turn = 0;
        this.numPlayers = 1;
        this.playerScores[0] = 0;
        this.playerScores[1] = 0;
        for (int i=0; i<numPlayers; i++) {
            for (int j=0; j<HAND_NUM; j++) {
                playerHands[i][j] = drawPile.get((int) Math.random()*36);
            }
        }
    }

    public QwirkleGameState(QwirkleGameState orig) {
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();
        drawPile = new ArrayList<>();
        for (int i = 0; i<drawPile.size(); i++) {
            QwirkleTile oldTile = orig.getDrawPile().get(i);
            QwirkleTile newTile = new QwirkleTile(oldTile.getQwirkleAnimal(), oldTile.getQwirkleColor());
            drawPile.add(newTile);
        }
        board = new QwirkleTile[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i<board.length; i++) {
            for (int j = 0; j<board[i].length; j++) {
                if (board[i][j] == null) {
                    continue;
                }
                else {
                    QwirkleTile oldTile = orig.getBoard()[i][j];
                    board[i][j] = new QwirkleTile(oldTile.getxPos(), oldTile.getyPos(), oldTile.getQwirkleAnimal(), oldTile.getQwirkleColor());
                }
            }
        }
        playerHands = new QwirkleTile[orig.numPlayers][QwirkleGameState.HAND_NUM];
        for (int i = 0; i<playerHands.length; i++) {
            for (int j= 0; j<playerHands[i].length; j++) {
                playerHands[i][j] = orig.getPlayerHands()[i][j];
            }
        }
        playerScores = new int[orig.numPlayers];
        for (int i = 0; i<playerScores.length; i++) {
            playerScores[i] = orig.getPlayerScores()[i];
        }
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
