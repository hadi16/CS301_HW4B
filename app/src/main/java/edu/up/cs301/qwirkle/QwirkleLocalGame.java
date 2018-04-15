package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.action.PassAction;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * The QwirkleLocalGame class for a Qwirkle game. Defines and enforces the game
 * rules; handles interactions with players.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 14, 2018
 */
public class QwirkleLocalGame extends LocalGame {
    // the game state
    private QwirkleGameState gameState;

    //the rules of the game
    private QwirkleRules rules = new QwirkleRules();

    @Override
    public void start(GamePlayer[] players) {
        /*
        External Citation
        Date: 11 April 2018
        Problem: Needed some guidance regarding the local game
        Resource:
        https://github.com/srvegdahl/HeartsApplication/
        blob/master/app/src/main/java/edu/up/cs301/slapjack/SJLocalGame.java
        Solution: I used Professor Vegdahl's code as reference.
        */

        // The game is initialized from here to allow for
        // an arbitrary number of players.
        super.start(players);
        this.gameState = new QwirkleGameState(players.length);
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

        QwirkleTile[][] playerHands = gameState.getPlayerHands();
        for (int playerId=0; playerId<players.length; playerId++) {
            if (rules.validMovesExist(playerHands[playerId], gameState.getBoard())) {
                return null;
            }
        }

        ArrayList<Integer> winners = gameState.getWinners();

        if (winners.size() == 0) return null;
        else if (winners.size() == 1) {
            return playerNames[winners.get(0)] + " won.";
        }
        else if (winners.size() == 2) {
            return playerNames[winners.get(0)] + " and " +
                    playerNames[winners.get(1)] + " won.";
        }
        else {
            String message = "";
            for (int i=0; i<winners.size(); i++) {
                int playerId = winners.get(i);
                // First iteration in the loop
                if (i == 0) {
                    message = playerNames[playerId];
                }
                if (i == winners.size()-1) {
                    message += ", and " + playerNames[playerId] + " won.";
                }
                else {
                    message += ", " + playerNames[playerId];
                }
            }
            return message;
        }
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
            int x = pta.getXPos();
            int y = pta.getYPos();
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
            int points = rules.getPoints();
            int newScore = gameState.getPlayerScore(playerIdx) + points;
            gameState.setPlayerScore(playerIdx, newScore);

            // replace the tile in the player's hand with a random one from the
            // draw pile, then change the turn
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

            // Swap all of the tiles.
            ArrayList<Integer> tilesToSwap = sta.getSwapIdx();
            int playerId = getPlayerIdx(sta.getPlayer());
            for (int i : tilesToSwap) {
                QwirkleTile tileToSwap =
                        gameState.getPlayerHands()[playerId][i];
                gameState.addToDrawPile(tileToSwap);
                gameState.setPlayerHandsAtIdx(playerId, i,
                        gameState.getRandomTile());
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
}

