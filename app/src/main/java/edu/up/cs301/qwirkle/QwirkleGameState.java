package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleGameState
 * The game state of Qwirkle.
 *
 * @author Alex Hadi
 * @author Stephanie Camacho
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 9, 2018
 */

public class QwirkleGameState extends GameState {
    private int turn;
    private int numPlayers;
    private ArrayList<QwirkleTile> drawPile = new ArrayList<>();

    // The number of tiles a player can have in their hand is 6
    private static final int HAND_NUM = 6;

    // Array for the current state of the board.
    private QwirkleTile board[][] = new QwirkleTile[MainBoard.BOARD_WIDTH]
            [MainBoard.BOARD_HEIGHT];

    // Array for the player hands.
    private QwirkleTile playerHands[][];

    // Array to store each player's score (index corresponds to playerId).
    private int[] playerScores;

    public QwirkleGameState() {
        this.turn = 0;
        this.numPlayers = 1;
        this.playerHands = new QwirkleTile[numPlayers][HAND_NUM];
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
    }

    public QwirkleGameState(QwirkleGameState orig) {
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();

        drawPile = new ArrayList<>();
        for (int i = 0; i<drawPile.size(); i++) {
            QwirkleTile oldTile = orig.drawPile.get(i);
            QwirkleTile newTile = new QwirkleTile(oldTile.getQwirkleAnimal(), oldTile.getQwirkleColor());
            drawPile.add(newTile);
        }

        board = new QwirkleTile[MainBoard.BOARD_WIDTH][MainBoard.BOARD_HEIGHT];
        for (int i = 0; i<board.length; i++) {
            for (int j = 0; j<board[i].length; j++) {
                if (board[i][j] != null) {
                    QwirkleTile oldTile = orig.board[i][j];
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
            playerScores[i] = orig.playerScores[i];
        }
    }

    public boolean hasTilesInPile() {
        return drawPile.size() > 0;
    }

    // Getters
    public int getTurn() {
        return turn;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public QwirkleTile[][] getPlayerHands() {
        return playerHands;
    }
    public QwirkleTile[][] getBoard() {
        return board;
    }
}
