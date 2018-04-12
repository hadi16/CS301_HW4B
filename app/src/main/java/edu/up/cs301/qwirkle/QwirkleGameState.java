package edu.up.cs301.qwirkle;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Contains the state of the Qwirkle game. Sent by the game when a player wants
 * to enquire about the state of the game. (E.g., to display it, or to help
 * figure out its next move.)
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleGameState extends GameState {
    // instance variables

    // Int that determines who's turn it currently is
    private int turn;

    // Int that determines the number of players currently playing
    private int numPlayers;

    // Arraylist of QwirkleTiles that represents the location of all the
    // other tiles not currently in play on the main or side boards
    private ArrayList<QwirkleTile> drawPile = new ArrayList<>();

    // The number of tiles a player can have in their hand is 6
    public static final int HAND_NUM = 6;

    // Array for the current state of the board
    private QwirkleTile board[][] = new QwirkleTile[MainBoard.BOARD_WIDTH]
            [MainBoard.BOARD_HEIGHT];

    // Array for the player hands
    private QwirkleTile playerHands[][];

    // Array to store each player's score (index corresponds to playerId)
    private int[] playerScores;

    // Array for each tile in the players' hand
    private QwirkleTile[] myPlayerHand;

    /**
     * Constructor for objects of class OwirkleGameState
     */
    public QwirkleGameState() {
        // initialize the state to be a brand new game for two players
        this.turn = 0;
        this.numPlayers = 2;
        initDrawPile();
        // randomizes each player's hands
        this.playerHands = new QwirkleTile[numPlayers][HAND_NUM];
        for (int i = 0; i < playerHands.length; i++) {
            for (int j = 0; j < playerHands[i].length; j++) {
                playerHands[i][j] = getRandomTile();
                playerHands[i][j].setyPos(j);
                playerHands[i][j].setMainBoard(false);
            }
        }
        // set all players' scores to 0 at the start of the game
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
    }

    /**
     * Copy constructor for class QwirkleGameState
     *
     * @param orig
     *          the QwirkleGameState that we want to access
     * @param playerId
     *          each unique player currently in the game
     */
    public QwirkleGameState(QwirkleGameState orig, int playerId) {
        // create a new QwirkleGameState that copies the values from the
        // original state
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();
        drawPile = null;

        // Give the board of new game state.
        board = orig.getBoard();

        // initializes each player's hand
        myPlayerHand = new QwirkleTile[HAND_NUM];
        for (int i = 0; i<HAND_NUM; i++) {
            myPlayerHand[i] = orig.getPlayerHands()[playerId][i];
        }

        // initializes each player's score
        playerScores = new int[orig.numPlayers];
        for (int i = 0; i<playerScores.length; i++) {
            playerScores[i] = orig.playerScores[i];
        }

        // draw the main board of the new state
        board = orig.getBoard();
    }

    /**
     * Initializes the drawpile to contain the 108 tiles used to deal out
     * to each player's hands
     */
    private void initDrawPile() {
        // adds the 36 unique Qwirkle tiles 3 times to the drawpile
        for (QwirkleAnimal animal : QwirkleAnimal.values()) {
            for (QwirkleColor color : QwirkleColor.values()) {
                for (int i = 0; i < 3; i++) {
                    drawPile.add(new QwirkleTile(animal, color));
                }
            }
        }
    }

    /**
     * Initializes each player's hands with random tiles from the drawpile
     */
    private void initPlayerHands() {
        // adds 6 random tiles to the player's hand at the beginning of the
        // game from the drawpile
        this.playerHands = new QwirkleTile[numPlayers][HAND_NUM];
        for (int i = 0; i < playerHands.length; i++) {
            for (int j = 0; j < playerHands[i].length; j++) {
                playerHands[i][j] = getRandomTile();
                playerHands[i][j].setyPos(j);
                playerHands[i][j].setMainBoard(false);
            }
        }
    }

    /**
     * Initializes each players' scores to 0 at the beginning of the game
     */
    private void initPlayerScores() {
        // set all scores to 0
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
    }

    /**
     * Add a Qwirkle tile to the draw pile after swapping
     *
     * @param tile
     *          the Qwirkle tile being swapped out
     */
    public void addToDrawPile(QwirkleTile tile) {
        // add the Qwirkle tile selected to be swapped out to the drawpile
        drawPile.add(tile);
    }

    /**
     * Returns a random tile from drawpile
     *
     * @return
     *          the Qwirkle Tile randomly selected from the drawpile to swap in
     */
    public QwirkleTile getRandomTile() {
        // if there are no more tiles in the drawpile, do nothing
        if (drawPile.size() == 0) {
            return null;
        }
        // randomly remove a tile from the drawpile and return it
        Random random = new Random();
        int i = random.nextInt(drawPile.size());
        QwirkleTile tile = drawPile.get(i);
        drawPile.remove(i);
        return tile;
    }

    /**
     * Change the turn of each player after they have completed their move
     */
    public void changeTurn() {
        // Change the turn based on the amount of players currently in game
        if (turn == numPlayers-1) {
            turn = 0;
        }
        else {
            turn++;
        }
    }

    /**
     * Check to see whether there are still tiles in the drawpile
     *
     * @return
     *          true if there are and false if there is none left
     */
    public boolean hasTilesInPile() {
        return drawPile.size() > 0;
    }

    // Getters
    /**
     * Return each players' turn
     *
     * @return
     *          a player's turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Return the number of players in game
     *
     * @return
     *          total number of players currently in game
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Return each players' hand
     *
     * @return
     *          the Qwirkle tiles in each players' hand based on different
     *          player IDs
     */
    public QwirkleTile[][] getPlayerHands() {
        return playerHands;
    }

    /**
     * Return the current board state
     *
     * @return
     *          the board state at a given moment
     */
    public QwirkleTile[][] getBoard() {
        return board;
    }

    /**
     * Return the current player's hand
     *
     * @return
     *          the tiles in the current player's hand
     */
    public QwirkleTile[] getMyPlayerHand() {
        return myPlayerHand;
    }

    /**
     * Return the computer player's score
     *
     * @return
     *          the scores of the AI at a given moment
     */
    public int getCompPlayerScores() {
        return playerScores[1];
    }

    /**
     * Return each players' score
     *
     * @return
     *          the scores of the players at a given moment
     */
    public int getMyPlayerScore() {
        return playerScores[0];
    }

    /**
     * Return each player's score
     *
     * @return
     *          the scores of each players at a given moment
     */
    public int[] getPlayerScores() {
        return playerScores;
    }



    // Setters
    /**
     * Set the current board state
     *
     * @param x
     *          x-position of a spot on the board
     * @param y
     *          y-position of a spot on the board
     * @param tile
     *          tile currently in a spot on the board
     */
    public void setBoardAtIdx(int x, int y, QwirkleTile tile) {
        // define the current state of the main board
        board[x][y] = tile;
        if (tile == null) return;
        board[x][y].setxPos(x);
        board[x][y].setyPos(y);
        board[x][y].setMainBoard(true);
    }

    /**
     * Set the player's hand
     *
     * @param playerIdx
     *          a specific player
     * @param handIdx
     *          a position in the player's hand
     * @param tile
     *          tile currently in the player's hand
     */
    public void setPlayerHandsAtIdx(int playerIdx, int handIdx, QwirkleTile tile) {
        // define the current condition of the player's hand
        playerHands[playerIdx][handIdx] = tile;
        if (tile == null) return;
        playerHands[playerIdx][handIdx].setyPos(handIdx);
        playerHands[playerIdx][handIdx].setMainBoard(false);
    }

    /**
     * Set the player's hand at a specific index
     *
     * @param idx
     *          specific position in the player's hand
     * @param isSelected
     *          the selected position in the player's hand
     */
    public void setPlayerHandsIsSelectedAtIdx(int idx, boolean isSelected) {
        // select an index in the player's hand
        myPlayerHand[idx].setSelected(isSelected);
    }

    /**
     * Set each players' scores
     *
     * @param playerIdx
     *          a specific player
     * @param isQwirkle
     *          the maximum points a player can get from completing a qwirkle
     */
    public void setPlayerScores(int playerIdx, boolean isQwirkle) {
        // if a player managed to complete a line of either the same animal
        // or the same color, that is a Qwirkle and it adds on an additional 6
        // points to the player's current score
        if (isQwirkle) {
            playerScores[playerIdx] +=6;
        }
        // add a point to each player's score for every tile placed
        playerScores[playerIdx] +=1;
    }
}
