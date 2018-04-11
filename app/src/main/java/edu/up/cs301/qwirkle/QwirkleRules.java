package edu.up.cs301.qwirkle;

import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleRules
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 11, 2018
 */
public class QwirkleRules {
    private boolean match(QwirkleTile tile1, QwirkleTile tile2) {
        String animal1 = tile1.getQwirkleAnimal().toString();
        String color1 = tile1.getQwirkleColor().toString();
        String animal2 = tile2.getQwirkleAnimal().toString();
        String color2 = tile2.getQwirkleColor().toString();

        if (animal1.equals(animal2) && color1.equals(color2)) {
            return false;
        }
        if (animal1.equals(animal2) || color1.equals(color2)) {
            return true;
        }
        return false;
    }

    private boolean matchAdj(int x, int y, String dir, QwirkleTile[][] board) {
        System.out.println(x);
        System.out.println(y);
        System.out.println("ok then");
        // Step 1: Check for valid x & y position
        if (!(x>=0 && y>=0 && x<MainBoard.BOARD_WIDTH &&
                y<MainBoard.BOARD_HEIGHT)) {
            System.out.println("hi there");
            return false;
        }

        // Step 2: Get tile1 at x, y (return false if not found)
        QwirkleTile tile1 = board[x][y];
        if (tile1 == null) {
            return false;
        }

        // Step 3: Get tile2 one step in dir (return false if not found)
        QwirkleTile tile2 = null;
        if (dir.equals("E") && (x+1) < MainBoard.BOARD_WIDTH) {
            tile2 = board[x+1][y];
        } else if (dir.equals("W") && (x-1) >= 0) {
            tile2 = board[x-1][y];
        } else if (dir.equals("N") && (y-1) >= 0) {
            tile2 = board[x][y-1];
        } else if (dir.equals("S") && (y+1) < MainBoard.BOARD_HEIGHT) {
            tile2 = board[x][y+1];
        }
        if (tile2 == null) {
            System.out.println("im here");
            return false;
        }

        // Step 4: return the match of tile1, tile2
        return match(tile1, tile2);
    }

    private ArrayList<QwirkleTile> addAdj(int x, int y, ArrayList<QwirkleTile> line, String dir,
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
        return line;
    }

    private boolean isValidLine(ArrayList<QwirkleTile> line) {
        for (int i=0; i<line.size(); ++i) {
            for (int j=0; j<line.size(); ++j) {
                System.out.println("here");
                if (!(match(line.get(i), line.get(j)))) return false;
            }
        }
        return true;
    }

    public boolean isValidMove(int x, int y, QwirkleTile tile,
                               QwirkleTile[][] board) {
        System.out.println("here we goooo");
        System.out.println(x);
        System.out.println(y);
        System.out.println("donnnnne");
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
        if (empty) {
            System.out.println("im empty");
            return true;
        }

        // Step 3: Create lineEW & lineNS, then add tile at x,y to lineEW &
        // lineNS
        ArrayList<QwirkleTile> lineEW = new ArrayList<>();
        lineEW.add(tile);
        ArrayList<QwirkleTile> lineNS = new ArrayList<>();
        lineNS.add(tile);

        // Step 4: Find the tiles in line that tile is being added to
        lineNS = addAdj(x, y, lineNS, "N", board);
        lineNS = addAdj(x, y, lineNS, "S", board);
        lineEW = addAdj(x, y, lineEW, "E", board);
        lineEW = addAdj(x, y, lineEW, "W", board);

        // Step 5: verify lines are valid
        if (lineNS.size() == 1 && lineEW.size() == 1) {
            //System.out.println("im here again");
            return false;
        }
        if (lineNS.size() > 1) {
            if (!isValidLine(lineNS)) {
                System.out.println("im northg");
                return false;
            }
        }
        if (!(lineEW.size() > 1)) {
            if (!isValidLine(lineEW)) {
                System.out.println("im east");
                return false;
            }
        }

        // If all checks passed, then it must be a valid move.
        return true;
    }
}
