package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleRules
 * This class checks if the move is valid by checking if the moves are valid.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 11, 2018
 */
public class QwirkleRules {
    /**
     * Method: match
     * Check 2 tiles to see if they are the same animal or same color
     * @param tile1 first tile to be check
     * @param tile2 second tile to be check
     * @return true the tiles are the same color or same animal
     */
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
    /**
     * Method: matchAdj
     * Check the tiles in a direction to see if they match
     * @param x x position on the board
     * @param y y position on the board
     * @param dir direction to check in
     * @param board the board of the game
     * @return true if the adjacent tile match
     */
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
    /**
     * Method: addAdj
     * Method that adds the correct tile to the line
     * @param x x position of tile on the board
     * @param y y postion of tile on the board
     * @param line the arraylist to add tile in
     * @param dir direction to add tile
     * @param board board of the game
     * @return updated arraylist
     */
    private ArrayList<QwirkleTile> addAdj(int x, int y,
                                          ArrayList<QwirkleTile> line,
                                          String dir, QwirkleTile[][] board) {
        int currentX = x;
        int currentY = y;
        //Go in all 4 directions to see which line to add tile
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
            //Add tile to the line
            line.add(board[currentX][currentY]);
        }
        //Return the updated line
        return line;
    }
    /**
     * Method: isValidLine
     * Method to check if the lime is valid for the tile to go in
     * @param line ArrayList of QwirkleTile
     * @return true if line it's the correct line
     */
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
        //See if they have different attributes
        boolean[] differentAttributes = new boolean[6];
        for (int i=0; i<differentAttributes.length; i++) {
            differentAttributes[i] = false;
        }
        for (int i=0; i<line.size(); i++) {
            QwirkleTile theTile = line.get(i);
            QwirkleAnimal animal = theTile.getQwirkleAnimal();
            QwirkleColor color = theTile.getQwirkleColor();

            /*
            External Citation
            Date: 11 April 2018
            Problem: Could not get the index of something in an enum.
            Resource:
            https://stackoverflow.com/questions/14769655/
            use-of-ordinal-inside-java-enum-definition
            Solution: Used the ordinal feature of enums.
            */
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

    /**
     * Method checkCornerCase
     * Check to see if the tile is at the corner of 2 tiles
     * @param x x position of tile
     * @param y y position of tile
     * @param tileToPlace tile to be place
     * @param board board of the game
     * @return true if it's a coner case
     */
    private boolean checkCornerCase(int x, int y, QwirkleTile tileToPlace,
                                    QwirkleTile[][] board) {
        //Check north
        QwirkleTile tileN = board[x][y-1];
        if (tileN != null) {
            if (!match(tileToPlace, tileN)) return false;
        }
        //Check south
        QwirkleTile tileS = board[x][y+1];
        if (tileS != null) {
            if (!match(tileToPlace, tileS)) return false;
        }
        //Check west
        QwirkleTile tileW = board[x-1][y];
        if (tileW != null) {
            if (!match(tileToPlace, tileW)) return false;
        }

        //Check east
        QwirkleTile tileE = board[x+1][y];
        if (tileE != null) {
            if (!match(tileToPlace, tileE)) return false;
        }

        return true;
    }

    /**
     * Method: isValidMove
     * When the user is placing a piece on the board,
     * check to see if that move is valid
     *
     * @param x x position of tile being put
     * @param y y position of tile being put
     * @param tile tile being put
     * @param board board to be put
     * @return return true if move is valid
     */
    public boolean isValidMove(int x, int y, QwirkleTile tile,
                               QwirkleTile[][] board) {
        // Step 1: Check to see that x,y is empty spot on board
        if (board[x][y] != null) return false;

        // Step 2: If entire board is empty, then return true (any placement ok)
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
        if (lineEW.size() > 1 && lineNS.size() > 1) {
            if (!checkCornerCase(x, y, tile, board)) {
                return false;
            }
        }

        // If all checks passed, then it must be a valid move.
        return true;
    }
}
