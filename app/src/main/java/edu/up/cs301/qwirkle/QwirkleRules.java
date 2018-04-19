package edu.up.cs301.qwirkle;

import android.graphics.Point;

import java.util.ArrayList;

import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: QwirkleRules
 * This class checks if the move is valid by checking if the moves are valid.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 19, 2018
 */
public class QwirkleRules {
    private ArrayList<QwirkleTile> lineNS; // the line north and south.
    private ArrayList<QwirkleTile> lineEW; // the line east and west.

    /**
     * Enum: Direction
     * Represents North, South, East, or West direction.
     */
    private enum Direction {
        /*
        External Citation
        Date: 14 April 2018
        Problem: Wanted to have x & y position in each enum element
        Resource:
        https://stackoverflow.com/questions/19600684/
        java-enum-with-multiple-value-types
        Solution: Used a constructor with x & y int values.
        */
        NORTH(0,-1),
        EAST(1,0),
        SOUTH(0, 1),
        WEST(-1,0);

        private int x; // The x position in the direction
        private int y; // The y position in the direction.

        /**
         * Constructor: Direction
         * Initializes the direction.
         * @param x The x value to move by (-1, 0, or 1).
         * @param y The y value to move by (-1, 0, or 1).
         */
        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Method: getX
         * @return The x value to move by (-1, 0, or 1).
         */
        public int getX() {
            return x;
        }

        /**
         * Method: getY
         * @return The y value to move by (-1, 0, or 1).
         */
        public int getY() {
            return y;
        }
    }

    /**
     * Method: createLine
     * Create the line of tiles.
     * @param x The x position to start at.
     * @param y The y position to start at.
     * @param line The line to create (ArrayList of tiles).
     * @param dir The direction to go (enum).
     * @param board The board of tiles.
     */
    private void createLine(int x, int y, ArrayList<QwirkleTile> line,
                            Direction dir, QwirkleTile[][] board) {
        // Increment x & y by direction first.
        x += dir.getX();
        y += dir.getY();

        // To prevent array out of bounds.
        if (!(x>=0 && y>=0 && x<CONST.BOARD_WIDTH && y<CONST.BOARD_HEIGHT)) {
            return;
        }

        while (board[x][y] != null) {
            //Add tile to the line
            line.add(board[x][y]);

            // Increment by the direction in x & y.
            x += dir.getX();
            y += dir.getY();

            // To prevent array out of bounds.
            if (!(x>=0 && y>=0 && x<CONST.BOARD_WIDTH && y<CONST.BOARD_HEIGHT)) {
                return;
            }
        }
    }

    /**
     * Method: isSameAnimal
     * Determines if the tiles in the line should have the same animal or not.
     * @param line The ArrayList of tiles in the line.
     * @return True (should be same animal), otherwise false.
     */
    private boolean isSameAnimal(ArrayList<QwirkleTile> line) {
        // This function assumes that there are at least two tiles in the line
        // (otherwise must not be called, as it will throw exception)
        QwirkleTile tile1 = line.get(0);
        QwirkleTile tile2 = line.get(1);

        return tile1.getQwirkleAnimal().equals(tile2.getQwirkleAnimal());
    }

    /**
     * Method: isValidLine
     * Determines if a given line of tiles is valid
     *
     * @param line The ArrayList of tiles that represents the line.
     * @param isSameAnimal Whether they should all have the same animal.
     * @return True (line is valid), otherwise false.
     */
    private boolean isValidLine(ArrayList<QwirkleTile> line,
                                boolean isSameAnimal) {
        /*
        External Citation
        Date: 11 April 2018
        Problem: Could not get the index of something in an enum.
        Resource:
        https://stackoverflow.com/questions/14769655/
        use-of-ordinal-inside-java-enum-definition
        Solution: Used the ordinal feature of enums.
        */
        if (isSameAnimal) {
            QwirkleAnimal animal1 = line.get(0).getQwirkleAnimal();
            boolean[] differentColors = new boolean[6];
            for (QwirkleTile tile : line) {
                if (!tile.getQwirkleAnimal().equals(animal1)) {
                    return false;
                }
                QwirkleColor color = tile.getQwirkleColor();
                if (differentColors[color.ordinal()]) {
                    return false;
                }
                else {
                    differentColors[color.ordinal()] = true;
                }
            }
        }
        else {
            QwirkleColor color1 = line.get(0).getQwirkleColor();
            boolean[] differentAnimals = new boolean[6];
            for (QwirkleTile tile : line) {
                if (!tile.getQwirkleColor().equals(color1)) {
                    return false;
                }
                QwirkleAnimal animal = tile.getQwirkleAnimal();
                if (differentAnimals[animal.ordinal()]) {
                    return false;
                }
                else {
                    differentAnimals[animal.ordinal()] = true;
                }
            }
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

        // Step 2: Create lineEW & lineNS. Add tile to each line.
        this.lineNS = new ArrayList<>();
        lineNS.add(tile);
        this.lineEW = new ArrayList<>();
        lineEW.add(tile);

        // Step 3: If entire board is empty, return true (any placement ok)
        boolean empty = true;
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                if (board[i][j] != null) {
                    empty = false;
                }
            }
        }
        // Require that the first tile be placed in the center of the board.
        if (empty) {
            return x==board.length/2 && y==board[0].length/2;
        }

        // Step 4: Find the tiles in line that tile is being added to.
        createLine(x, y, lineNS, Direction.NORTH, board);
        createLine(x, y, lineNS, Direction.SOUTH, board);
        createLine(x, y, lineEW, Direction.EAST, board);
        createLine(x, y, lineEW, Direction.WEST, board);

        // Step 5: verify lines are valid
        if (lineNS.size() == 1 && lineEW.size() == 1) {
            return false;
        }
        if (lineNS.size() > 1) {
            boolean sameAnimalNS = isSameAnimal(lineNS);
            if (!isValidLine(lineNS, sameAnimalNS)) {
                return false;
            }
        }
        if (lineEW.size() > 1) {
            boolean sameAnimalEW = isSameAnimal(lineEW);
            if (!isValidLine(lineEW, sameAnimalEW)) {
                return false;
            }
        }

        // If all checks passed, then it must be a valid move.
        return true;
    }

    /**
     * Method: getPoints
     * Gets how many points are being earned by placing a tile.
     * Assumes that isValidMove is run first.
     * @return The amount of points as an integer.
     */
    public int getPoints() {
        // Starts off with 0 points
        int points = 0;

        // Only applies to the first move.
        if (lineNS.size() == 1 && lineEW.size() == 1) points += 1;

        // Give points for number of tiles in the line.
        if (lineNS.size() > 1) points += lineNS.size();
        if (lineEW.size() > 1) points += lineEW.size();

        // 6 points for a Qwirkle
        if (lineNS.size() == 6) points += 6;
        if (lineEW.size() == 6) points += 6;

        return points;
    }

    /**
     * Method: getLegalMoves
     * Returns the legal moves for a given tile as an ArrayList of Points.
     * @param tile The tile for the move.
     * @param board The Qwirkle board.
     * @return The ArrayList of all the possible legal moves.
     */
    public ArrayList<Point> getLegalMoves(QwirkleTile tile,
                                          QwirkleTile[][] board) {
        ArrayList<Point> legalMoves = new ArrayList<>();
        for (int x=0; x<board.length; x++) {
            for (int y=0; y<board[x].length; y++) {
                if (isValidMove(x, y, tile, board)) {
                    legalMoves.add(new Point(x, y));
                }
            }
        }
        return legalMoves;
    }

    /**
     * Method: validMovesExist
     * Check to see whether there are valid moves on the board for given player.
     * @param playerHand The player hand to check.
     * @param board The board.
     *
     * @return true if there are valid moves on the board for the player.
     */
    public boolean validMovesExist(QwirkleTile[] playerHand,
                                   QwirkleTile[][] board) {
        for (QwirkleTile tileInHand : playerHand) {
            if (tileInHand == null) continue;
            for (int x=0; x<board.length; x++) {
                for (int y=0; y<board[x].length; y++) {
                    if (isValidMove(x ,y, tileInHand, board)) {
                        return true;
                    }
                }
            }
        }

        // return false if there are no more valid moves.
        return false;
    }

    /**
     * Method: getNumQwirkles
     * @return the amount of Qwirkles as an integer.
     */
    public int getNumQwirkles() {
        if (this.lineNS.size() == 6 && this.lineEW.size() == 6) return 2;
        if (this.lineNS.size() == 6) return 1;
        if (this.lineEW.size() == 6) return 1;
        return 0;
    }
}
