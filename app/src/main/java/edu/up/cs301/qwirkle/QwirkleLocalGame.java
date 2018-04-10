package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleLocalGame
 * The local game of Qwirkle.
 *
 * @author Alex Hadi
 * @author Stephanie Camacho
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 5, 2018
 */
public class QwirkleLocalGame extends LocalGame {
    private QwirkleGameState gameState;

    public QwirkleLocalGame() {
        this.gameState = new QwirkleGameState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
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
            return true;
        }
        else if (action instanceof SwapTileAction) {
            SwapTileAction sta = (SwapTileAction) action;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validMovesExist() {
        for (int playerId=0; playerId<gameState.getNumPlayers(); ++playerId) {
            QwirkleTile[] playerHand = gameState.getPlayerHands()[playerId];
            for (QwirkleTile tileInHand : playerHand) {
                for (QwirkleTile[] tiles : gameState.getBoard()) {
                    for (QwirkleTile tile : tiles) {
                        if (isValidMove(tile.getxPos(), tile.getyPos(), tile,
                                gameState.getBoard())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean match(QwirkleTile tile1, QwirkleTile tile2) {
        // XOR operator (or, but not both)
        return tile1.getQwirkleAnimal().equals(tile2.getQwirkleAnimal()) ^
                tile1.getQwirkleColor().equals(tile2.getQwirkleColor());
    }

    private boolean matchAdj(int x, int y, String dir, QwirkleTile[][] board) {
        // Step 1: Check for valid x & y position
        if (!(x>=0 && y>=0 && x< MainBoard.BOARD_WIDTH &&
                y<MainBoard.BOARD_HEIGHT)) {
            return false;
        }

        // Step 2: Get tile1 at x, y (return false if not found)
        QwirkleTile tile1 = board[x][y];
        if (tile1 == null) return false;

        // Step 3: Get tile2 one step in dir (return false if not found)
        QwirkleTile tile2 = null;
        if (dir.equals("E") && (x+1) < MainBoard.BOARD_WIDTH) {
            tile2 = board[x+1][y];
        } else if (dir.equals("W") && (x-1) >= 0) {
            tile2 = board[x-1][y-1];
        } else if (dir.equals("N") && (y-1) >= 0) {
            tile2 = board[x][y-1];
        } else if (dir.equals("S") && (y+1) < MainBoard.BOARD_HEIGHT) {
            tile2 = board[x][y+1];
        }
        if (tile2 == null) return false;

        // Step 4: return the match of tile1, tile2
        return match(tile1, tile2);
    }

    private void addAdj(int x, int y, ArrayList<QwirkleTile> line, String dir,
                        QwirkleTile[][] board) {
        int currentX = x;
        int currentY = y;
        while (matchAdj(currentX, currentY, dir, board)) {
            switch (dir) {
                case "E":
                    currentX++;
                    break;
                case "W":
                    currentX--;
                    break;
                case "N":
                    currentY--;
                    break;
                case "S":
                    currentY++;
                    break;
            }
            line.add(board[currentX][currentY]);
        }
    }

    private boolean isValidLine(ArrayList<QwirkleTile> line) {
        for (int i=0; i<line.size(); ++i) {
            for (int j=0; j<line.size(); ++j) {
                if (!(match(line.get(i), line.get(j)))) return false;
            }
        }
        return true;
    }

    public boolean isValidMove(int x, int y, QwirkleTile tile,
                               QwirkleTile[][] board) {
        // Step 1: Check to see that x,y is empty spot on board
        if (board[x][y] != null) return false;

        // Step 2: If entire board is empty, then return true (any placement is
        // ok)
        boolean empty = true;
        for (QwirkleTile[] tileArr : board) {
            for (QwirkleTile t : tileArr) {
                if (t != null) empty = false;
            }
        }
        if (empty) return true;

        // Step 3: Create lineEW & lineNS, then add tile at x,y to lineEW &
        // lineNS
        ArrayList<QwirkleTile> lineEW = new ArrayList<>();
        lineEW.add(board[x][y]);
        ArrayList<QwirkleTile> lineNS = new ArrayList<>();
        lineNS.add(board[x][y]);

        // Step 4: Find the tiles in line that tile is being added to
        addAdj(x, y, lineNS, "N", board);
        addAdj(x, y, lineNS, "S", board);
        addAdj(x, y, lineEW, "E", board);
        addAdj(x, y, lineEW, "W", board);

        // Step 5: verify lines are valid
        if (lineNS.size() == 1 && lineEW.size() == 1) return false;
        if (lineNS.size() > 1) {
            if (!isValidLine(lineNS)) return false;
        }
        if (!(lineEW.size() > 1)) {
            if (!isValidLine(lineEW)) return false;
        }

        // If all checks passed, then it must be a valid move.
        return true;
    }
}
