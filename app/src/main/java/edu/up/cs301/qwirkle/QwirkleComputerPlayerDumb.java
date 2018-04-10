package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;

/**
 * Class: QwirkleComputerPlayerDumb
 * The dumb computer player.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */
public class QwirkleComputerPlayerDumb extends GameComputerPlayer {
    //Instance variables
    private QwirkleGameState gameState;
    private QwirkleLocalGame localGame;
    private QwirkleTile[] playerHand;
    private int playerScore;
    private QwirkleTile[][] board;
    private boolean myTurn;

    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        //Check if it's the correct instance of the game
        if (!(info instanceof QwirkleGameState)) return;
        //Get the current game state
        gameState = (QwirkleGameState)info;
        //Check for the player's turn
        if (gameState.getTurn() != this.playerNum) return;
        //Play a random move
        playRandomMove();
        PlaceTileAction action = new PlaceTileAction((GamePlayer)info);
    }

    private void playRandomMove(){
        //If it's not the player's turn, exit out of the code
        if(!myTurn) return;

        //Check each tile in the hand to the whole board to see if there's a valid move
        //Iterate through each tile in the player's hand
        for (int i = 0; i < 6; i++){
            //Iterate through all x position
            for(int x = 0; x < MainBoard.BOARD_WIDTH; x++){
                //Iterate through all y position
                for(int y = 0; y < MainBoard.BOARD_HEIGHT; y++){
                    if(localGame.isValidMove(x,y, playerHand[i], board)) {
                        //Place tile
                        //Update the score
                        return;
                    }
                }
            }
        }
        //No valid move on the board
        //Swap tile

        //End turn
        myTurn = false;
    }
}
