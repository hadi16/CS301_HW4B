package edu.up.cs301.qwirkle;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Contains the state of the Qwirkle game. Sent by the game when a player wants
 * to enquire about the state of the game. (i.e. to display it or to help
 * figure out its next move)
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 14, 2018
 */
public class QwirkleGameState extends GameState {
    private int turn; // The current turn
    private int numPlayers; // The number of players
    private int tilesLeft; // Number of tiles left in the draw pile.

    // ArrayList of QwirkleTiles that represents all tiles that
    // haven't been played or dealt to players.
    private ArrayList<QwirkleTile> drawPile = new ArrayList<>();

    // Array for the current state of the board
    private QwirkleTile board[][];

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
        this.board = new QwirkleTile[CONST.BOARD_WIDTH][CONST.BOARD_HEIGHT];

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
        turn = orig.turn;
        numPlayers = orig.numPlayers;

        // Hide the draw pile from the user, but send number of tiles left.
        drawPile = null;
        tilesLeft = orig.drawPile.size();

        // Copy the board to the new game state.
        board = new QwirkleTile[orig.board.length][orig.board[0].length];
        for (int i = 0; i<board.length; i++) {
            for (int j = 0; j<board[i].length; j++) {
                if (orig.board[i][j] != null) {
                    // Use QwirkleTile's copy constructor.
                    board[i][j] = new QwirkleTile(orig.board[i][j]);
                }
            }
        }

        // Copy just the current player's hand.
        myPlayerHand = new QwirkleTile[CONST.NUM_IN_HAND];
        for (int i=0; i<myPlayerHand.length; i++) {
            myPlayerHand[i] = orig.playerHands[playerId][i];
        }

        // Copy the player scores.
        playerScores = new int[orig.playerScores.length];
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
                for (int i=0; i<3; i++) {
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
        this.playerHands = new QwirkleTile[numPlayers][CONST.NUM_IN_HAND];
        for (int i = 0; i < playerHands.length; i++) {
            for (int j = 0; j < playerHands[i].length; j++) {
                playerHands[i][j] = getRandomTile();
                playerHands[i][j].setYPos(j);
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
     * Method: getMyPlayerScore
     * Get the scores at index
     *
     * @return the human player's score.
     */
    public int getPlayerScore(int playerIdx) {
        return playerScores[playerIdx];
    }

    public int getTilesLeft() {
        return tilesLeft;
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
        board[x][y] = tile;
        if (tile == null) return;
        board[x][y].setXPos(x);
        board[x][y].setYPos(y);
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
        playerHands[playerIdx][handIdx] = tile;
        if (tile == null) return;
        playerHands[playerIdx][handIdx].setYPos(handIdx);
        playerHands[playerIdx][handIdx].setMainBoard(false);
    }

    /**
     * Method: setPlayerScore
     * Set one of the player's scores.
     *
     * @param playerIdx the specific player idx
     */
    public void setPlayerScore(int playerIdx, int score) {
        this.playerScores[playerIdx] = score;
    }
}
