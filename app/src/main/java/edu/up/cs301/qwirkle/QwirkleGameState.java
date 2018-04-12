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
 * to enquire about the state of the game. (i.e. to display it or to help
 * figure out its next move)
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 11, 2018
 */
public class QwirkleGameState extends GameState {
    private int turn; // The current turn
    private int numPlayers; // The number of players

    // ArrayList of QwirkleTiles that represents all tiles that
    // haven't been played or dealt to players.
    private ArrayList<QwirkleTile> drawPile = new ArrayList<>();

    // A constant for number of tiles a player can have in their hand
    public static final int HAND_NUM = 6;

    // Array for the current state of the board
    private QwirkleTile board[][] = new QwirkleTile[MainBoard.BOARD_WIDTH]
            [MainBoard.BOARD_HEIGHT];

    // Array for all player hands
    private QwirkleTile playerHands[][];

    // Array to store all player scores (index corresponds to playerId)
    private int[] playerScores;

    // Array for each tile in the current player's hand
    private QwirkleTile[] myPlayerHand;

    /**
     * Constructor: QwirkleGameState
     * Initializes the game state with the given number of players.
     *
     * @param numPlayers the number of players.
     */
    public QwirkleGameState(int numPlayers) {
        // initialize the state to be a brand new game
        this.turn = 0;
        this.numPlayers = numPlayers;
        this.drawPile = new ArrayList<>();
        this.board =
                new QwirkleTile[MainBoard.BOARD_WIDTH][MainBoard.BOARD_HEIGHT];

        // Initialize the draw pile, player hands, and player scores.
        initDrawPile();
        initPlayerHands();
        initPlayerScores();
    }

    /**
     * Constructor: QwirkleGameState
     * Copy constructor for the game state.
     *
     * @param orig The original QwirkleGameState object.
     * @param playerId The player ID to copy the game state for.
     */
    public QwirkleGameState(QwirkleGameState orig, int playerId) {
        // Copy the integers.
        turn = orig.getTurn();
        numPlayers = orig.getNumPlayers();

        // Hide the draw pile from the user.
        drawPile = null;

        // Copy the board to the new game state.
        board = new QwirkleTile[MainBoard.BOARD_WIDTH][MainBoard.BOARD_HEIGHT];
        for (int i = 0; i<board.length; i++) {
            for (int j = 0; j<board[i].length; j++) {
                if (orig.board[i][j] != null) {
                    // Use QwirkleTile's copy constructor.
                    board[i][j] = new QwirkleTile(orig.board[i][j]);
                }
            }
        }

        // Copy each player's hand
        myPlayerHand = new QwirkleTile[HAND_NUM];
        for (int i = 0; i<HAND_NUM; i++) {
            myPlayerHand[i] = orig.getPlayerHands()[playerId][i];
        }

        // Copy the player scores.
        playerScores = new int[orig.numPlayers];
        for (int i = 0; i<playerScores.length; i++) {
            playerScores[i] = orig.playerScores[i];
        }
    }

    /**
     * Method: initDrawPile
     * Initializes the draw pile to contain the 108 tiles used to deal out
     * to the players.
     */
    private void initDrawPile() {
        // Adds the 36 unique Qwirkle tiles 3 times to the draw pile.
        for (QwirkleAnimal animal : QwirkleAnimal.values()) {
            for (QwirkleColor color : QwirkleColor.values()) {
                for (int i = 0; i < 3; i++) {
                    drawPile.add(new QwirkleTile(animal, color));
                }
            }
        }
    }

    /**
     * Method: initPlayerHands
     * Initializes each player's hands with random tiles from the draw pile.
     */
    private void initPlayerHands() {
        // adds 6 tiles to each player's hand randomly.
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
     * Method: initPlayerScores
     * Initializes each player's scores to 0 at the beginning of the game
     */
    private void initPlayerScores() {
        // Set all scores to 0
        this.playerScores = new int[numPlayers];
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 0;
        }
    }

    /**
     * Method: addToDrawPile
     * Public helper method to add a tile to the draw pile (after swapping).
     *
     * @param tile the Qwirkle tile being added back to the draw pile.
     */
    public void addToDrawPile(QwirkleTile tile) {
        // add the Qwirkle tile selected to be swapped out to the draw pile.
        drawPile.add(tile);
    }

    /**
     * Method: getRandomTile
     * Gets a random tile from the draw pile.
     *
     * @return the Qwirkle Tile randomly selected from the draw pile.
     */
    public QwirkleTile getRandomTile() {
        // If there are no more tiles in the draw pile, return null.
        if (drawPile.size() == 0) {
            return null;
        }

        // Randomly remove a tile from the draw pile and return it.
        Random random = new Random();
        int i = random.nextInt(drawPile.size());
        QwirkleTile tile = drawPile.get(i);
        drawPile.remove(i);
        return tile;
    }

    /**
     * Method: changeTurn
     * Changes the current turn.
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
     * Method: hasTilesInPile
     * Check to see whether there are still tiles in the draw pile.
     *
     * @return True if there are tiles left (otherwise false)
     */
    public boolean hasTilesInPile() {
        return drawPile.size() > 0;
    }
    /**
     * Method: getTurn
     * Gets the current turn.
     *
     * @return the current turn
     */
    public int getTurn() {
        return turn;
    }
    /**
     * Method: getNumPlayers
     * Return the number of players in the game
     *
     * @return total number of players in game
     */
    public int getNumPlayers() {
        return numPlayers;
    }
    /**
     * Method: getPlayerHands
     * Return the array of player hands.
     *
     * @return the Qwirkle tiles in each player's hand as an int[].
     */
    public QwirkleTile[][] getPlayerHands() {
        return playerHands;
    }
    /**
     * Method: getBoard
     * Gets the board.
     *
     * @return the board
     */
    public QwirkleTile[][] getBoard() {
        return board;
    }
    /**
     * Method: getMyPlayerHand
     * Return the current player's hand
     *
     * @return the tiles in the current player's hand
     */
    public QwirkleTile[] getMyPlayerHand() {
        return myPlayerHand;
    }
    /**
     * Method: getCompPlayerScores
     * Gets the computer player's score
     *
     * @return the scores of the AI at a given moment
     */
    public int getCompPlayerScores() {
        return playerScores[1];
    }
    /**
     * Method: getMyPlayerScore
     * Get the human player's score
     *
     * @return the human player's score.
     */
    public int getMyPlayerScore() {
        return playerScores[0];
    }
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
    public void setPlayerHandsAtIdx(int playerIdx, int handIdx,
                                    QwirkleTile tile) {
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
