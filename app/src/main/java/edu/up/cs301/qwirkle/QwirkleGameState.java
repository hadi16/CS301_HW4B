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
    private ArrayList<QwirkleTile> drawPile = new ArrayList<>();

    // The number of tiles a player can have in their hand is 6
    public static final int HAND_NUM = 6;

    // Array for the current state of the board.
    private QwirkleTile board[][] = new QwirkleTile[MainBoard.BOARD_WIDTH]
            [MainBoard.BOARD_HEIGHT];

    // Array for the player hands.
    private QwirkleTile playerHands[][];

    // Array to store each player's score (index corresponds to playerId).
    private int[] playerScores;

    private QwirkleTile[] myPlayerHand;

    public QwirkleGameState() {
        this.turn = 0;
        //TODO: FIX THIS
        this.numPlayers = 2;
        initDrawPile();
        this.playerHands = new QwirkleTile[numPlayers][HAND_NUM];
        for (int i = 0; i < playerHands.length; i++) {
            for (int j = 0; j < playerHands[i].length; j++) {
                playerHands[i][j] = getRandomTile();
                playerHands[i][j].setyPos(j);
                playerHands[i][j].setMainBoard(false);
            }
        }
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
    }

    public QwirkleGameState(QwirkleGameState orig, int playerId) {
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();
        drawPile = null;

        board = new QwirkleTile[MainBoard.BOARD_WIDTH][MainBoard.BOARD_HEIGHT];
        for (int i = 0; i<board.length; i++) {
            for (int j = 0; j<board[i].length; j++) {
                if (board[i][j] != null) {
                    QwirkleTile oldTile = orig.getBoard()[i][j];
                    board[i][j] = new QwirkleTile(oldTile.getxPos(), oldTile.getyPos(), oldTile.getQwirkleAnimal(), oldTile.getQwirkleColor());
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
    public void setPlayerHandsIsSelectedAtIdx(int idx, boolean isSelected) {
        myPlayerHand[idx].setSelected(isSelected);
    }
}
