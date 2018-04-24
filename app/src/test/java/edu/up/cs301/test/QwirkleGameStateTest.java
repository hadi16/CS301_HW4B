package edu.up.cs301.test;

import org.junit.Test;

import java.util.ArrayList;

import edu.up.cs301.game.Game;
import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.QwirkleLocalGame;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.player.QwirkleHumanPlayer;
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
        //set up and create game state
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Smart AI";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //check drawPile size, add a tile to it, then assert that drawPile size
        //has increased by 1
        int initialTilesLeft = gameState.getDrawPile().size();
        QwirkleTile tile =
                new QwirkleTile(QwirkleAnimal.dog, QwirkleColor.blue);
        gameState.addToDrawPile(tile);
        assertTrue(initialTilesLeft+1 == gameState.getDrawPile().size());
    }

    @Test
    public void testGetRandomTile() throws Exception {
        //set up and create game state
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //assert that getRandomTile returns a non null object
        assertNotNull(gameState.getRandomTile());
    }

    @Test
    public void testChangeTurn() throws Exception {
        //set up and create game state
        String[] playerTypes = new String[4];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        playerTypes[2] = "Smart AI";
        playerTypes[3] = "Network Player";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //set turn to 0, call changeTurn, then assert that turn = 1
        gameState.setTurn(0);
        gameState.changeTurn();
        assertEquals(gameState.getTurn(), 1);

        //corner case: set turn to 3, call changeTurn, then assert that turn = 0
        gameState.setTurn(3);
        gameState.changeTurn();
        assertEquals(gameState.getTurn(),0);
    }

    @Test
    public void testHasTilesInPile() throws Exception {
        //set up and create game state
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //if drawPile size is not 0 is true, hasTilesInPile should also be true
        assertEquals(gameState.getDrawPile().size() != 0,
                gameState.hasTilesInPile());
    }

    @Test
    public void testGetWinners() throws Exception {
        //set up and create game state
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //set up player scores for a tie
        gameState.setPlayerScore(0,108);
        gameState.setPlayerScore(1,108);

        //create an array list with 0 and 1
        ArrayList <Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);

        //get winners array list should be equal to list that was just created
        assertTrue(list.equals(gameState.getWinners()));

        //check if it works with only one winner
        gameState.setPlayerScore(0,55);
        list.remove(0);
        assertTrue(list.equals(gameState.getWinners()));

    }

    @Test
    public void testSetBoardAtIdx() throws Exception {
        //set up and create game state
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //create new qwirkle tile and call setBoardAtIdx
        QwirkleTile tile =
                new QwirkleTile(QwirkleAnimal.dog, QwirkleColor.blue);
        gameState.setBoardAtIdx(1,3, tile);

        //assert that tile at board index is equal to qwirkle tile created
        assertEquals(gameState.getBoard()[1][3], tile);

    }

    @Test
    public void testSetPlayerHandsAtIdx() throws Exception {
        //set up and create game state
        String[] playerTypes = new String[2];
        playerTypes[0] = "Human";
        playerTypes[1] = "Dumb AI";
        QwirkleGameState gameState =
                new QwirkleGameState(playerTypes.length, playerTypes);

        //create new qwirkle tile and call setPlayerHandsAtIdx
        QwirkleTile tile =
                new QwirkleTile(QwirkleAnimal.owl, QwirkleColor.yellow);
        gameState.setPlayerHandsAtIdx(0,4,tile);

        //assert that tile in player hand is equal to qwirkle tile created
        assertEquals(gameState.getPlayerHands()[0][4], tile);

    }
}