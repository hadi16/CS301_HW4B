package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.ProxyPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.action.PassAction;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.player.QwirkleComputerPlayerDumb;
import edu.up.cs301.qwirkle.player.QwirkleComputerPlayerSmart;
import edu.up.cs301.qwirkle.player.QwirkleHumanPlayer;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * The QwirkleLocalGame class for a Qwirkle game. Defines and enforces the game
 * rules; handles interactions with players.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 19, 2018
 */
public class QwirkleLocalGame extends LocalGame {
    private QwirkleGameState gameState; // the game state.
    private QwirkleRules rules = new QwirkleRules(); // for legal moves.

    /**
     * Method: start
     * The start method is overridden to support an arbitrary amount of players.
     * @param players The array of GamePlayer objects.
     */
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

        // The game is initialized here to allow for arbitrary number of players.
        super.start(players);

        // To allow for scoreboard types functionality.
        String[] playerTypes = new String[players.length];
        for (int i=0; i<players.length; i++) {
            GamePlayer player = players[i];
            if (player instanceof QwirkleHumanPlayer) {
                playerTypes[i] = "Human";
            } else if (player instanceof QwirkleComputerPlayerDumb) {
                playerTypes[i] = "Dumb AI";
            } else if (player instanceof QwirkleComputerPlayerSmart) {
                playerTypes[i] = "Smart AI";
            } else if (player instanceof ProxyPlayer){
                playerTypes[i] = "Network Player";
            } else {
                playerTypes[i] = "";
            }
        }

        // Initialize the game state.
        this.gameState = new QwirkleGameState(players.length, playerTypes);
    }

    /**
     * Method: sendUpdatedStateTo
     * Notify the given player that its state has changed. This should involve
     * sending a GameInfo object to the player. If the game is not a perfect-
     * information game, this method should remove any information from the game
     * that the player is not allowed to know.
     *
     * @param p the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // Added by Nux to prevent race condition with null game state.
        while (gameState == null) {
            try {
                Thread.sleep(1);
            } catch(InterruptedException ie) {
                // Don't do anything.
            }
        }

        //make a copy of the state, and send it to the player
        p.sendInfo(new QwirkleGameState(gameState, getPlayerIdx(p)));
    }

    /**
     * Method: canMove
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx the player's player-number (ID)
     * @return True if the player is allowed to move (otherwise false)
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return gameState.getTurn() == playerIdx;
    }

    /**
     * Method: checkIfGameOver
     * Tell whether the game has ended or if there is a winner
     *
     * @return Whether the game has ended (null string) or not
     */
    @Override
    protected String checkIfGameOver() {
        // Check to see whether the game has ended or not, or if there are
        // no valid moves left to complete
        if (gameState.hasTilesInPile()) return null;

        // Check for any remaining valid moves.
        QwirkleTile[][] playerHands = gameState.getPlayerHands();
        for (int playerId=0; playerId<players.length; playerId++) {
            if (rules.validMovesExist(playerHands[playerId],
                    gameState.getBoard())) {
                return null;
            }
        }

        // Check for the winner(s) and return the appropriate message.
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
                // Last iteration in the loop.
                else if (i == winners.size()-1) {
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
     * Method: makeMove
     * Make a move on behalf of a player.
     *
     * @param action The move that the player has sent to the game
     * @return Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        // Get the current player (for message board string).
        String playerName = playerNames[getPlayerIdx(action.getPlayer())];

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
            //Set the recent action
            gameState.setPlayerHandsAtIdx(playerIdx, handIdx,
                    gameState.getRandomTile());

            // Set the message board string
            String message;
            message = String.format("%s placed a tile and received %d points.",
                    playerName, points);
            int numQwirkles = rules.getNumQwirkles();
            if (numQwirkles == 1) {
                message += " They got a Qwirkle!";
            } else if (numQwirkles == 2) {
                message += " They got two Qwirkles!";
            }
            gameState.setMessageBoardString(message);

            // Change the turn
            gameState.changeTurn();
            return true;
        }
        // swap the tiles selected in the player's hand with random ones
        // from the draw pile
        else if (action instanceof SwapTileAction) {
            SwapTileAction sta = (SwapTileAction) action;

            // Swap all of the tiles in the ArrayList.
            ArrayList<Integer> tilesToSwap = sta.getSwapIdx();
            int playerId = getPlayerIdx(sta.getPlayer());
            for (int i : tilesToSwap) {
                QwirkleTile tileToSwap =
                        gameState.getPlayerHands()[playerId][i];
                gameState.addToDrawPile(tileToSwap);
                gameState.setPlayerHandsAtIdx(playerId, i,
                        gameState.getRandomTile());
            }

            // Set the message board string & change the turn.
            String message = String.format("%s swapped %d tiles.", playerName,
                    tilesToSwap.size());
            gameState.setMessageBoardString(message);
            gameState.changeTurn();
            return true;
        }
        else if (action instanceof PassAction) {
            // Set the message board string & change the turn.
            gameState.setMessageBoardString(playerName + " passed.");
            gameState.changeTurn();
            return true;
        }
        else {
            return false;
        }
    }
}

