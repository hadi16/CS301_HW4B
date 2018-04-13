package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.action.PassAction;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * The QwirkleLocalGame class for a Qwirkle game. Defines and enforces the game
 * rules; handles interactions with players.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleLocalGame extends LocalGame {
    // instance variables

    // the game's state
    private QwirkleGameState gameState;

    //the rules of the game
    private QwirkleRules rules = new QwirkleRules();

    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
        this.gameState = new QwirkleGameState(players.length);
    }

    /**
     * Constructor for the QwirkleLocalGame
     */
    public QwirkleLocalGame() {
        /*
        External Citation
        Date: 11 April 2018
        Problem: Needed some guidance regarding the local game
        Resource:
        https://github.com/srvegdahl/HeartsApplication/
        blob/master/app/src/main/java/edu/up/cs301/slapjack/SJLocalGame.java
        Solution: I used Professor Vegdahl's code as reference.
        */

        // initializes a new game state
        this.gameState = new QwirkleGameState(4);
    }

    /**
     * Notify the given player that its state has changed. This should involve
     * sending a GameInfo object to the player. If the game is not a perfect-
     * information game, this method should remove any information from the game
     * that the player is not allowed to know.
     *
     * @param p
     *        the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //make a copy of the state, and send it to the player
        p.sendInfo(new QwirkleGameState(gameState, getPlayerIdx(p)));
    }

    /**
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     *      True if the player is allowed to move;
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return gameState.getTurn() == playerIdx;
    }

    /**
     * Tell whether the game has ended or if there is a winner
     *
     * @return
     *      Whether the game has ended or not
     */
    @Override
    protected String checkIfGameOver() {
        // Check to see whether the game has ended or not, or if there are
        // no valid moves left to complete
        if (gameState.hasTilesInPile()) return null;
        if (validMovesExist()) return null;
        if(gameState.hasTilesInPile()) return null;
        if(!gameState.hasTilesInPile()) {
             if (!validMovesExist()) {
                int highestScore = gameState.getPlayerScore(0);
                for (int playerId = 0; playerId < gameState.getNumPlayers(); playerId++) {
                    if (gameState.getPlayerScore(playerId) > highestScore) {
                        highestScore = gameState.getPlayerScore(playerId);
                        }
                    }
                for (int playerId = 0; playerId < gameState.getNumPlayers(); playerId++) {
                    if (highestScore == gameState.getPlayerScore(playerId)) {
                        return playerNames[playerId];
                        }
                    }
                }
            }

        return null;
    }

    /**
     * Make a move on behalf of a player.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return
     *          Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        // get the x- and y- position of a tile in the player's hand
        if (action instanceof PlaceTileAction) {
            PlaceTileAction pta = (PlaceTileAction) action;
            int x = pta.getxPos();
            int y = pta.getyPos();
            int handIdx = pta.getHandIdx();

            // place the tile from the player's hand to the board
            QwirkleTile[][] playerHands = gameState.getPlayerHands();
            int playerIdx = getPlayerIdx(pta.getPlayer());
            QwirkleTile tile = playerHands[playerIdx][handIdx];

            // if the placement is not valid, do nothing
            if (!rules.isValidMove(x, y, tile, gameState.getBoard())) {
                return false;
            }

            // draw the new tile on the board and update the score accordingly
            gameState.setBoardAtIdx(x, y, tile);
            gameState.setPlayerScores(playerIdx, false);

            // replace the tile in the player's hand with a random one from the
            // drawpile, then change the turn
            gameState.setPlayerHandsAtIdx(playerIdx, handIdx,
                    gameState.getRandomTile());
            gameState.changeTurn();

            // return true if a move has been made
            return true;
        }
        // swap the tiles selected in the player's hand with random ones
        // from the drawpile
        else if (action instanceof SwapTileAction) {
            SwapTileAction sta = (SwapTileAction) action;
            boolean[] tilesToSwap = sta.getSwapIdx();
            for (int i=0; i<tilesToSwap.length; i++) {
                boolean swapThisTile = tilesToSwap[i];
                if (swapThisTile) {
                    int playerId = getPlayerIdx(sta.getPlayer());
                    QwirkleTile tileToSwap =
                            gameState.getPlayerHands()[playerId][i];
                    gameState.addToDrawPile(tileToSwap);
                    gameState.setPlayerHandsAtIdx(playerId, i,
                            gameState.getRandomTile());
                }
            }

            // end the current player's turn
            gameState.changeTurn();

            // return true if a swap has been made, and false otherwise.
            return true;
        }

        else if (action instanceof PassAction) {
            gameState.changeTurn();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check to see whether there are valid moves on the board to place certain
     * Qwirkle tiles
     *
     * @return
     *          true if there are valid moves on the board for a QwirkleTime
     */
    private boolean validMovesExist() {
        // check each position on the board and return the valid spots for a
        // selected Qwirkle tile.
        for (int playerId=0; playerId<gameState.getNumPlayers(); ++playerId) {
            QwirkleTile[] playerHand = gameState.getPlayerHands()[playerId];
            QwirkleTile[][] board = gameState.getBoard();
            for (QwirkleTile tileInHand : playerHand) {
                for (int x = 0; x < MainBoard.BOARD_WIDTH; x++) {
                    for (int y = 0; y < MainBoard.BOARD_HEIGHT; y++) {
                        if (rules.isValidMove(x ,y, tileInHand, board)) {
                            return true;
                        }
                    }
                }
            }
        }

        // return false if there are no more valid moves.
        return false;
    }

}

