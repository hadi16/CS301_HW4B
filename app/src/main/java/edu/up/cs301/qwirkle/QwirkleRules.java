package edu.up.cs301.qwirkle;

import android.util.Log;

import java.util.ArrayList;

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

        // True if animal OR color is equal, but not both.
        if (animal1.equals(animal2) && color1.equals(color2)) {
            return false;
        }
        if (animal1.equals(animal2) || color1.equals(color2)) {
            return true;
        }
        return false;
    }

    private boolean matchAdj(int x, int y, String dir, QwirkleTile[][] board) {
        // Step 1: Check for valid x & y position
        if (!(x>=0 && y>=0 && x<MainBoard.BOARD_WIDTH &&
                y<MainBoard.BOARD_HEIGHT)) {
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
        // True if animal is the same throughout line (otherwise false).
        boolean sameAnimal;
        String sameAttribute;

        // Note: this function is only called if the line has more than 1 element.
        QwirkleTile t1 = line.get(0);
        QwirkleTile t2 = line.get(1);

        String animal1 = t1.getQwirkleAnimal().toString();
        String color1 = t1.getQwirkleColor().toString();
        String animal2 = t2.getQwirkleAnimal().toString();
        String color2 = t2.getQwirkleColor().toString();

        if (animal1.equals(animal2) && color1.equals(color2)) {
            return false;
        }
        else if (animal1.equals(animal2)) {
            sameAnimal = true;
            sameAttribute = animal1;
        }
        else if (color1.equals(color2)) {
            sameAnimal = false;
            sameAttribute = color1;
        }
        else {
            return false;
        }

        boolean[] differentAttributes = new boolean[6];
        for (int i=0; i<differentAttributes.length; i++) {
            differentAttributes[i] = false;
        }
        for (int i=0; i<line.size(); i++) {
            QwirkleTile theTile = line.get(i);
            QwirkleAnimal animal = theTile.getQwirkleAnimal();
            QwirkleColor color = theTile.getQwirkleColor();

            if (sameAnimal) {
                if (!animal.toString().equals(sameAttribute)) return false;
                if (differentAttributes[color.ordinal()]) return false;
                else differentAttributes[color.ordinal()] = true;
            }
            else {
                if (!color.toString().equals(sameAttribute)) return false;
                if (differentAttributes[animal.ordinal()]) return false;
                else differentAttributes[animal.ordinal()] = true;
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
        if (empty) {
            return true;
        }

        // Step 3: Create lineEW & lineNS, then add tile at x,y to lineEW &
        // lineNS
        ArrayList<QwirkleTile> lineEW = new ArrayList<>();
        lineEW.add(tile);
        ArrayList<QwirkleTile> lineNS = new ArrayList<>();
        lineNS.add(tile);

        // Tile is added preliminarily to make the algorithm work.
        board[x][y] = tile;

        // Step 4: Find the tiles in line that tile is being added to
        lineNS = addAdj(x, y, lineNS, "N", board);
        lineNS = addAdj(x, y, lineNS, "S", board);
        lineEW = addAdj(x, y, lineEW, "E", board);
        lineEW = addAdj(x, y, lineEW, "W", board);

        // Tile is removed from board position after algorithm completes.
        board[x][y] = null;

        // Step 5: verify lines are valid
        if (lineNS.size() == 1 && lineEW.size() == 1) {
            return false;
        }
        // Check to make sure no lines can be longer than 6.
        if (lineNS.size() > 6 || lineEW.size() > 6) {
            return false;
        }
        if (lineNS.size() > 1) {
            if (!isValidLine(lineNS)) {
                return false;
            }
        }
        if (lineEW.size() > 1) {
            if (!isValidLine(lineEW)) {
                return false;
            }
        }

        // If all checks passed, then it must be a valid move.
        return true;
    }
}
