package edu.up.cs301.test;

import org.junit.Test;

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
        QwirkleTile tile1 = new QwirkleTile(QwirkleAnimal.dog, QwirkleColor.blue);
        QwirkleTile tile2 = new QwirkleTile(QwirkleAnimal.owl, QwirkleColor.green);
        QwirkleTile tile3 = new QwirkleTile(QwirkleAnimal.fox, QwirkleColor.orange);
    }

    @Test
    public void getRandomTile() throws Exception {

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