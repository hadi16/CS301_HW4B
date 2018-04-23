package edu.up.cs301.test;

import org.junit.Test;

import edu.up.cs301.game.Game;
import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.tile.QwirkleAnimal;
import edu.up.cs301.qwirkle.tile.QwirkleColor;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

import static org.junit.Assert.*;

/**
 * Class: QwirkleGameStateTest
 * This class test all of the methods in the game state to see whether they are
 * working as expected.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 20, 2018
 */
public class QwirkleGameStateTest {
    @Test
    public void addToDrawPile() throws Exception {
        String[] playerTypes = new String[2];
        playerTypes[0] = "local human player";
        playerTypes[1] = "easy AI player";
        QwirkleGameState testGameState = new QwirkleGameState(2, playerTypes );
        QwirkleTile tile1 = new QwirkleTile(QwirkleAnimal.dog, QwirkleColor.blue);
        int tilesLeft1 = testGameState.getTilesLeft();
        testGameState.addToDrawPile(tile1);
        int tilesLeft2 = testGameState.getTilesLeft();
        assertEquals(tilesLeft1+1, tilesLeft2);
    }

    @Test
    public void getRandomTile() throws Exception {
        String[] playerTypes = new String[2];
        playerTypes[0] = "local human player";
        playerTypes[1] = "easy AI player";
        QwirkleGameState testGameState = new QwirkleGameState(2, playerTypes);
        assertNotNull(testGameState.getRandomTile());
    }

    @Test
    public void changeTurn() throws Exception {

    }

    @Test
    public void hasTilesInPile() throws Exception {

    }

    @Test
    public void getTurn() throws Exception {

    }

    @Test
    public void getPlayerHands() throws Exception {

    }

    @Test
    public void getBoard() throws Exception {

    }

    @Test
    public void getMyPlayerHand() throws Exception {

    }

    @Test
    public void getPlayerScore() throws Exception {

    }

    @Test
    public void getTilesLeft() throws Exception {

    }

    @Test
    public void getPlayerTypeAtIdx() throws Exception {

    }

    @Test
    public void getWinners() throws Exception {

    }

    @Test
    public void setBoardAtIdx() throws Exception {

    }

    @Test
    public void setPlayerHandsAtIdx() throws Exception {

    }

    @Test
    public void setPlayerScore() throws Exception {

    }

    @Test
    public void getMessageBoardString() throws Exception {

    }

    @Test
    public void setMessageBoardString() throws Exception {

    }

}