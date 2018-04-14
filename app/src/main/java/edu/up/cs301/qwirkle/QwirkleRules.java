package edu.up.cs301.qwirkle;

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
 * @version April 14, 2018
 */
public class QwirkleRules {
    private boolean isQwirkle = false;
    private int point = 0;
    private boolean isFirstTurn;
    /*
    External Citation
    Date: 14 April 2018
    Problem: Wanted to have x & y position in each enum element
    Resource:
    https://stackoverflow.com/questions/19600684/
    java-enum-with-multiple-value-types
    Solution: Used a constructor with x & y int values.
    */
    private enum Direction {
        NORTH(0,-1),
        EAST(1,0),
        SOUTH(0, 1),
        WEST(-1,0);

        private int x;
        private int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

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

    private boolean isSameAnimal(ArrayList<QwirkleTile> line) {
        // This function assumes that there are at least two tiles in the line
        // (otherwise must not be called, as it will throw exception)
        QwirkleTile tile1 = line.get(0);
        QwirkleTile tile2 = line.get(1);

        return tile1.getQwirkleAnimal().equals(tile2.getQwirkleAnimal());
    }

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

        // Step 2: If entire board is empty, return true (any placement ok)
        boolean empty = true;
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                if (board[i][j] != null) {
                    empty = false;
                }
            }
        }
        isFirstTurn = true;
        if (empty) return true;
        isFirstTurn = false;

        // Step 3: Create lineEW & lineNS. Add tile to each line.
        ArrayList<QwirkleTile> lineNS = new ArrayList<>();
        lineNS.add(tile);
        ArrayList<QwirkleTile> lineEW = new ArrayList<>();
        lineEW.add(tile);

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
        //Set the score for this tile
        this.isQwirkle = isQwirkle(lineNS, lineEW);
        setPoints(x, y, tile, board);

        // If all checks passed, then it must be a valid move.
        return true;
    }

    /**
     * Method setIsQwirkle
     * Check if see if the arraylist of tile is 6 tiles long
     * That's a qwirkle
     * @param lineNS Vertical line of QwirkleTile
     * @param lineEW Horicontal line of QwirkleTile
     */
    private boolean isQwirkle(ArrayList<QwirkleTile> lineNS, ArrayList<QwirkleTile> lineEW) {
        if(lineNS.size() == 6 || lineEW.size() == 6) {
            return true;
        }
        return false;
    }
    /**
     * Method: setPoints
     * Set how many points are being earned by placing a tile
     * Points are set by how many adjacent tiles are matched
     * @param x x position of tile being placed
     * @param y y position of tile being placed
     * @param tile tile to be placed
     * @param board Qwirkle board
     */
    private void setPoints(int x, int y, QwirkleTile tile, QwirkleTile[][] board) {
        //Starts off with 0 points
        this.point = 0;
        //Each tiles match get 1 point
        if((x > 0) && (match(tile, board[x-1][y]))) {
            this.point++;
        }
        if((x < CONST.BOARD_WIDTH) && (match(tile, board[x+1][y]))) {
            this.point++;
        }
        if((y > 0) && (match(tile, board[x][y-1]))) {
            this.point++;
        }
        if((y < CONST.BOARD_HEIGHT) && (match(tile, board[x][y+1]))) {
            this.point++;
        }
    }
    /**
     * Method: match
     * Check if it's the same animal or same color but not both
     * @param tile1 Qwirkle tile 1
     * @param tile2 Qwirklke tile 2
     * @return true if it's the same animal or same color
     */
    private boolean match(QwirkleTile tile1, QwirkleTile tile2) {
        if(tile1 == null || tile2 == null ) {
            return false;
        }
        if(tile1.getQwirkleAnimal().equals(tile2.getQwirkleAnimal()) ||
            tile1.getQwirkleColor().equals(tile2.getQwirkleColor())) {
            return true;
        }
        return false;
    }

    //Getters
    public boolean isQwirkle() { return this.isQwirkle;}
    public int getPoint() {
        return this.point;
    }
    public boolean isFirstTurn() {
        return isFirstTurn;
    }
}
