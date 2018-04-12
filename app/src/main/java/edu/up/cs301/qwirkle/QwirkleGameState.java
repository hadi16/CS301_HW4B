package edu.up.cs301.qwirkle;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleGameState
 * The game state of Qwirkle.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleGameState extends GameState {
    private int turn;
    private int numPlayers;
    private ArrayList<QwirkleTile> drawPile;

    // The number of tiles a player can have in their hand is 6
    public static final int HAND_NUM = 6;

    // Array for the current state of the board.
    private QwirkleTile board[][];

    // Array for the player hands.
    private QwirkleTile playerHands[][];

    // Array to store each player's score (index corresponds to playerId).
    private int[] playerScores;

    private QwirkleTile[] myPlayerHand;

    public QwirkleGameState(int numPlayers) {
        this.turn = 0;
        this.numPlayers = numPlayers;
        this.drawPile = new ArrayList<>();
        this.board =
                new QwirkleTile[MainBoard.BOARD_WIDTH][MainBoard.BOARD_HEIGHT];

        initDrawPile();
        initPlayerHands();
        initPlayerScores();
    }

    public QwirkleGameState(QwirkleGameState orig, int playerId) {
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();
        drawPile = null;

        board = new QwirkleTile[MainBoard.BOARD_WIDTH][MainBoard.BOARD_HEIGHT];
        for (int i = 0; i<board.length; i++) {
            for (int j = 0; j<board[i].length; j++) {
                if (orig.board[i][j] != null) {
                    board[i][j] = new QwirkleTile(orig.board[i][j]);
                }
            }
        }

        myPlayerHand = new QwirkleTile[HAND_NUM];
        for (int i = 0; i<HAND_NUM; i++) {
            myPlayerHand[i] = orig.getPlayerHands()[playerId][i];
        }

        playerScores = new int[orig.numPlayers];
        for (int i = 0; i<playerScores.length; i++) {
            playerScores[i] = orig.playerScores[i];
        }
    }

    private void initDrawPile() {
        for (QwirkleAnimal animal : QwirkleAnimal.values()) {
            for (QwirkleColor color : QwirkleColor.values()) {
                for (int i = 0; i < 3; i++) {
                    drawPile.add(new QwirkleTile(animal, color));
                }
            }
        }
    }

    private void initPlayerHands() {
        this.playerHands = new QwirkleTile[numPlayers][HAND_NUM];
        for (int i = 0; i < playerHands.length; i++) {
            for (int j = 0; j < playerHands[i].length; j++) {
                playerHands[i][j] = getRandomTile();
                playerHands[i][j].setyPos(j);
                playerHands[i][j].setMainBoard(false);
            }
        }
    }

    private void initPlayerScores() {
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
    }

    public void addToDrawPile(QwirkleTile tile) {
        drawPile.add(tile);
    }

    public QwirkleTile getRandomTile() {
        if (drawPile.size() == 0) {
            return null;
        }
        Random random = new Random();
        int i = random.nextInt(drawPile.size());
        QwirkleTile tile = drawPile.get(i);
        drawPile.remove(i);
        return tile;
    }

    public void changeTurn() {
        if (turn == numPlayers-1) {
            turn = 0;
        }
        else {
            turn++;
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
    public QwirkleTile[] getMyPlayerHand() {
        return myPlayerHand;
    }

    public int[] getPlayerScores() {
        return playerScores;
    }

    // Setters
    public void setBoardAtIdx(int x, int y, QwirkleTile tile) {
        board[x][y] = tile;
        if (tile == null) return;
        board[x][y].setxPos(x);
        board[x][y].setyPos(y);
        board[x][y].setMainBoard(true);
    }

    public void setPlayerHandsAtIdx(int playerIdx, int handIdx, QwirkleTile tile) {
        playerHands[playerIdx][handIdx] = tile;
        if (tile == null) return;
        playerHands[playerIdx][handIdx].setyPos(handIdx);
        playerHands[playerIdx][handIdx].setMainBoard(false);
    }

    /**
     * Set each players' scores
     *
     * @param playerIdx a specific player
     * @param isQwirkle True if a Qwirkle, otherwise false.
     */
    public void setPlayerScores(int playerIdx, boolean isQwirkle) {
        // if a player managed to complete a line of either the same animal
        // or the same color, that is a Qwirkle and it adds on an additional 6
        // points to the player's current score
        if (isQwirkle) {
            playerScores[playerIdx] +=6;
        }
        playerScores[playerIdx] +=1;
    }
}
