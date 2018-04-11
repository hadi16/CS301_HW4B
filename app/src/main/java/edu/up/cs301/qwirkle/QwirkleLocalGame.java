package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: QwirkleLocalGame
 * The local game of Qwirkle.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleLocalGame extends LocalGame {
    private QwirkleGameState gameState;
    private QwirkleRules rules = new QwirkleRules();

    public QwirkleLocalGame() {
        // TODO: Fix number of players
        this.gameState = new QwirkleGameState(2);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new QwirkleGameState(gameState, getPlayerIdx(p)));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return gameState.getTurn() == playerIdx;
    }

    @Override
    protected String checkIfGameOver() {
        if (gameState.hasTilesInPile()) return null;
        if (validMovesExist()) return null;
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof PlaceTileAction) {
            PlaceTileAction pta = (PlaceTileAction) action;
            int x = pta.getxPos();
            int y = pta.getyPos();
            int handIdx = pta.getHandIdx();

            QwirkleTile[][] playerHands = gameState.getPlayerHands();
            int playerIdx = getPlayerIdx(pta.getPlayer());
            QwirkleTile tile = playerHands[playerIdx][handIdx];

            if (!rules.isValidMove(x, y, tile, gameState.getBoard())) {
                return false;
            }

            gameState.setBoardAtIdx(x, y, tile);
            int currentScore = gameState.getPlayerScores()[playerIdx];
            gameState.setPlayerScores(playerIdx, currentScore, false);

            gameState.setPlayerHandsAtIdx(playerIdx, handIdx, gameState.getRandomTile());
            gameState.changeTurn();

            return true;
        }
        else if (action instanceof SwapTileAction) {
            SwapTileAction sta = (SwapTileAction) action;
            boolean[] tilesToSwap = sta.getSwapIdx();
            for (int i=0; i<tilesToSwap.length; i++) {
                boolean swapThisTile = tilesToSwap[i];
                if (swapThisTile) {
                    int playerId = getPlayerIdx(sta.getPlayer());
                    QwirkleTile tileToSwap = gameState.getPlayerHands()[playerId][i];
                    gameState.addToDrawPile(tileToSwap);
                    gameState.setPlayerHandsAtIdx(playerId, i, gameState.getRandomTile());
                }
            }

            gameState.changeTurn();

            return true;
        }
        else {
            return false;
        }
    }

    private boolean validMovesExist() {
        for (int playerId=0; playerId<gameState.getNumPlayers(); ++playerId) {
            QwirkleTile[] playerHand = gameState.getPlayerHands()[playerId];
            QwirkleTile[][] board = gameState.getBoard();
            for (QwirkleTile tileInHand : playerHand) {
                for (QwirkleTile[] tileArr : board) {
                    for (QwirkleTile tile : tileArr) {
                        if (rules.isValidMove(tile.getxPos(), tile.getyPos(), tileInHand, board)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}
