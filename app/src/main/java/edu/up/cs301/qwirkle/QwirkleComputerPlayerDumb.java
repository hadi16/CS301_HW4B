package edu.up.cs301.qwirkle;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
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
    private QwirkleTile[] myPlayerHand;
    private QwirkleTile[][] board;
    private int myPlayerScore;
    private boolean myTurn;

    public QwirkleComputerPlayerDumb(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            return;
        }
        else if (!(info instanceof QwirkleGameState)) {
            return;
        }

        this.gameState = (QwirkleGameState)info;
        this.board = gameState.getBoard();
        this.myPlayerHand = gameState.getMyPlayerHand();

        playRandomMove();
    }

    private void playRandomMove(){
        //Check each tile in the hand to the whole board to see if there's a valid move
        //Iterate through each tile in the player's hand
        for (int i = 0; i < QwirkleGameState.HAND_NUM; i++){
            //Iterate through all x position
            for(int x = 0; x < MainBoard.BOARD_WIDTH; x++){
                //Iterate through all y position
                for(int y = 0; y < MainBoard.BOARD_HEIGHT; y++){
                    PlaceTileAction action = new PlaceTileAction(this, x, y, i);
                    game.sendAction(action);
                }
            }
        }
        //No valid move on the board
        //Swap tile

        //End turn
        myTurn = false;
    }
}
