package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.tile.Tile;

/**
 * Created by Alex Hadi on 4/1/2018.
 */

public class QwirkleGameState extends GameState {
    private int turn;
    private int numPlayers;
    private ArrayList<Tile> drawPile = new ArrayList<>();

    // Number of rows and columns for the board.
    private static final int BOARD_WIDTH = 24;
    private static final int BOARD_HEIGHT = 16;

    // The number of tiles a player can have in their hand is 6
    private static final int HAND_NUM = 6;

    // Array for the current state of the board.
    private Tile board[][] = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

    // Array for the player hands.
    private Tile playerHands[][];

    // Array to store each player's score (index corresponds to playerId).
    private int[] playerScores;

    public QwirkleGameState() {

    }

    private boolean match(Tile tile1, Tile tile2) {
        // XOR operator (or, but not both)
        return tile1.getQAnimal().equals(tile2.getQAnimal()) ^
                tile1.getQColor().equals(tile2.getQColor());
    }

    private boolean matchAdj(int x, int y, String dir) {
        // Step 1: Check for valid x & y position
        if (!(x>=0 && y>=0 && x<BOARD_WIDTH && y<BOARD_HEIGHT)) return false;

        // Step 2: Get tile1 at x, y (return false if not found)
        Tile tile1 = board[x][y];
        if (tile1 == null) return false;

        // Step 3: Get tile2 one step in dir (return false if not found)
        Tile tile2 = null;
        if (dir.equals("E") && (x+1) < BOARD_WIDTH) {
            tile2 = board[x+1][y];
        } else if (dir.equals("W") && (x-1) >= 0) {
            tile2 = board[x-1][y-1];
        } else if (dir.equals("N") && (y-1) >= 0) {
            tile2 = board[x][y-1];
        } else if (dir.equals("S") && (y+1) < BOARD_HEIGHT) {
            tile2 = board[x][y+1];
        }
        if (tile2 == null) return false;

        // Step 4: return the match of tile1, tile2
        return match(tile1, tile2);
    }

    private void addAdj(int x, int y, ArrayList<Tile> line, String dir) {
        int currentX = x;
        int currentY = y;
        while (matchAdj(currentX, currentY, dir)) {
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

    private boolean isValidLine(ArrayList<Tile> line) {
        for (int i=0; i<line.size(); ++i) {
            for (int j=0; j<line.size(); ++j) {
                if (!(match(line.get(i), line.get(j)))) return false;
            }
        }
        return true;
    }

    public boolean validMoveAlgorithm(int x, int y, Tile tile, Tile[][] board) {
        // Step 1: Check to see that x,y is empty spot on board
        if (board[x][y] != null) return false;

        // Step 2: If entire board is empty, then return true (any placement is ok)
        boolean empty = true;
        for (Tile[] tileArr : board) {
            for (Tile t : tileArr) {
                if (t != null) empty = false;
            }
        }
        if (empty) return true;

        // Step 3: Create lineEW & lineNS, then add tile at x,y to lineEW & lineNS
        ArrayList<Tile> lineEW = new ArrayList<>();
        lineEW.add(board[x][y]);
        ArrayList<Tile> lineNS = new ArrayList<>();
        lineNS.add(board[x][y]);

        // Step 4: Find the tiles in line that tile is being added to
        addAdj(x, y, lineNS, "N");
        addAdj(x, y, lineNS, "S");
        addAdj(x, y, lineEW, "E");
        addAdj(x, y, lineEW, "W");

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

    public int getTurn() {
        return turn;
    }
}
