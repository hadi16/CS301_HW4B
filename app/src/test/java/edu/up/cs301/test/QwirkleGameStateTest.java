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
    public void testAddToDrawPile() throws Exception {
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Smart AI";
        QwirkleGameState gameState = new QwirkleGameState(playerTypes.length, playerTypes);

        int initialTilesLeft = gameState.getTilesLeft();
        QwirkleTile tile = new QwirkleTile(QwirkleAnimal.dog, QwirkleColor.blue);
        gameState.addToDrawPile(tile);
        int finalTilesLeft = gameState.getTilesLeft();
        assertEquals(initialTilesLeft+1, finalTilesLeft);
    }

    @Test
    public void testGetRandomTile() throws Exception {
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        QwirkleGameState gameState = new QwirkleGameState(playerTypes.length, playerTypes);
        assertNotNull(gameState.getRandomTile());
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