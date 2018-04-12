package edu.up.cs301.qwirkle;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;
import edu.up.cs301.qwirkle.ui.SideBoard;

/**
 * A GUI that allows a human to play Qwirkle. Moves are made by selecting tiles
 * on the side board and placing them on the main board.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @version April 10, 2018
 */

public class QwirkleHumanPlayer extends GameHumanPlayer
        implements View.OnTouchListener, View.OnClickListener {
    // instance variables

    // the current activity
    private GameMainActivity activity;

    // the current game state
    private QwirkleGameState gameState;

    // Array of the current player's hand
    private QwirkleTile[] myPlayerHand;

    // the main board
    private MainBoard mainBoard;

    // the side board
    private SideBoard sideBoard;

    // the Textview for the turns
    private TextView textViewTurnLabel;

    // the Textview for the scores
    private TextView myScoreView;

    // the Textview for the score board
    private TextView scoreBoardView;

    // Array of the selected tiles from the player's hand
    private boolean[] isSelectedBoolArr = new boolean[QwirkleGameState.HAND_NUM];

    // boolean that tells whether swap was used
    private boolean swap = false;

    // button used to determine swapping
    private Button buttonSwap;


    /**
     * Constructor for QwirkleHumanPlayer
     *
     * @param name
     *          The player's name.
     */
    public QwirkleHumanPlayer(String name) {
        super(name);
    }

    /**
     * sets the current player as the activity's GUI
     *
     * @param activity
     *          an action that can be performed by the human
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        // remember the activity and initialize it
        this.activity = activity;
        QwirkleTile.initBitmaps(activity);

        // load the layout resource for the new configuration
        activity.setContentView(R.layout.qwirkle_human_player);

        // set the Textview to display each pleyer's name on their turn.
        TextView textViewPlayerLabel = (TextView)activity.findViewById(R.id.textViewPlayerLabel);
        textViewPlayerLabel.setText("My Name: " + name);

        // initialize the Textviews going on the interface.
        textViewTurnLabel = (TextView)activity.findViewById(R.id.textViewTurnLabel);

        // initialize the score to 0
        myScoreView = (TextView)activity.findViewById(R.id.textViewPlayerScore);

        // initialize the scoreboard to 0
        scoreBoardView = (TextView)activity.findViewById(R.id.textViewScoreboardLabel);



        // initialize the swap button, main board, and side board.
        buttonSwap = (Button)activity.findViewById(R.id.buttonSwap);
        buttonSwap.setOnClickListener(this);
        mainBoard = (MainBoard)activity.findViewById(R.id.mainBoard);
        mainBoard.setOnTouchListener(this);
        sideBoard = (SideBoard)activity.findViewById(R.id.sideBoard);
        sideBoard.setOnTouchListener(this);
    }

    /**
     * Display each player's names for "Current Turn: "
     */
    @Override
    protected void initAfterReady() {
        super.initAfterReady();
    }

    /**
     * The top view of the current state
     *
     * @return
     *         the top view of the project
     */
    @Override
    public View getTopView() {
        return activity.findViewById(R.id.top_gui_layout);
    }
    /**
     * External Citation
     * Date: April 11 2018
     * Problem: Couldn't update the GUI correctly
     * Source:
     *      https://github.com/srvegdahl/CounterGame/blob/master/app/src/main/java/edu/up/cs301/counter/CounterHumanPlayer.java
     * Solution:
     *      Use vegdahl's code as reference
     */

    protected void updateDisplay() {
        textViewTurnLabel.setText("Current Turn: " + allPlayerNames[playerNum]);
        myScoreView.setText("My Score: " + gameState.getMyPlayerScore());
        scoreBoardView.setText("Scoreboard:\n"+name+ gameState.getMyPlayerScore() + "\n"+"Computer: " + gameState.getCompPlayerScores());
    }

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     *           the message;
     */
    @Override
    public void receiveInfo(GameInfo info) {
        // if the move was out of turn or otherwise illegal, flast the screen
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            flash(Color.RED, 50);
            return;
        }
        else if (!(info instanceof QwirkleGameState)) {
            // if we do not have a QwirkleGameState, ignore
            return;
        }

        // initializes the game state and redraws it accordingly
        this.gameState = (QwirkleGameState)info;
        this.myPlayerHand = gameState.getMyPlayerHand();

        mainBoard.setGameState(gameState);
        sideBoard.setGameState(gameState);
        updateDisplay();

        mainBoard.invalidate();
        sideBoard.invalidate();
    }

    /**
     * callback method when the screen is touched. We're looking for a screen
     * touch (which we'll detect on the "down" movement onto a Qwirkle board
     * spot)
     *
     * @param v
     *          the surface view
     * @param event
     *          the motion event that was detected
     * @return
     *          true if the touch was registered
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "down" event
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both values are in the
        // range 0..2)
        int x = (int)event.getX();
        int y = (int)event.getY();

        // access the main board in order to allow touch
        // detection
        if (v.getId() == R.id.mainBoard) {
            // To prevent the user from selecting board before tile in hand
            QwirkleTile handSelected = null;
            int handSelectedIdx = -1;
            for (int i=0; i<myPlayerHand.length; i++) {
                QwirkleTile tile = myPlayerHand[i];
                if (isSelectedBoolArr[i]) {
                    handSelected = tile;
                    handSelectedIdx = i;
                    break;
                }
            }
            // if the hand a player selected in empty, do nothing
            if (handSelected == null) return false;

            // Get where the board is selected
            int[] xyPos = getSelectedBoardIdx(x, y);
            if (xyPos == null) {
                return false;
            }

            // send the PlaceTileAction to allow for tiles to be placed on
            // the main board
            PlaceTileAction pta = new PlaceTileAction(this, xyPos[0], xyPos[1],
                    handSelectedIdx);
            game.sendAction(pta);

            // redraw after each iteration
            mainBoard.invalidate();
            sideBoard.invalidate();

            // return true if touch has been registered
            return true;
        }
        // access the side board in order to allow touch detection
        else if (v.getId() == R.id.sideBoard) {
            // if swap has been selected, allow the tile to be accessed in the
            // player's hand. If there is nothing there, do nothing.
            if (swap) {
                int yPos = getSelectedHandIdx(x, y);
                if (yPos == -1) return false;

                isSelectedBoolArr[yPos] = true;
                myPlayerHand[yPos].setSelected(true);
            }
            else {
                int yPos = getSelectedHandIdx(x, y);
                if (yPos == -1) return false;

                for (int i = 0; i < isSelectedBoolArr.length; i++) {
                    isSelectedBoolArr[i] = false;
                    myPlayerHand[i].setSelected(false);
                }
                isSelectedBoolArr[yPos] = true;
                myPlayerHand[yPos].setSelected(true);
            }

            mainBoard.invalidate();
            sideBoard.invalidate();

            return true;
        }

        return false;
    }

    /**
     * return a position in main board while taking into account of the offset
     *
     * @param x
     *          x-position of spot on board
     * @param y
     *          y-position of spot on board
     * @return
     *          position of main board
     */
    private int[] getSelectedBoardIdx(int x, int y) {
        // if the position selected is outside the main board, do nothing
        if (x < QwirkleTile.OFFSET_MAIN || x > QwirkleTile.RECTDIM_MAIN *
                MainBoard.BOARD_WIDTH + QwirkleTile.OFFSET_MAIN) {
            return null;
        }

        int[] xyPos = new int[2];
        // X position
        xyPos[0] = (x - QwirkleTile.OFFSET_MAIN) / QwirkleTile.RECTDIM_MAIN;
        // Y position
        xyPos[1] = y / QwirkleTile.RECTDIM_MAIN;

        // return position on main board
        return xyPos;
    }

    /**
     * return a position in player's hand while taking into account of the
     * offset
     *
     * @param x
     *          x-position of spot in hand
     * @param y
     *          y-position of spot in hand
     * @return
     *          position in player's hand
     */
    private int getSelectedHandIdx(int x, int y) {
        // if the position selected is outside the side board, do nothing
        if (x < QwirkleTile.OFFSET_SIDE || x > QwirkleTile.RECTDIM_SIDE *
                QwirkleGameState.HAND_NUM + QwirkleTile.OFFSET_SIDE) {
            return -1;
        }
        // return the hand index selected
        return y / QwirkleTile.RECTDIM_SIDE;
    }

    /**
     * callback method when something is clicked on the screen
     *
     * @param v
     *          the surface view
     */
    @Override
    public void onClick(View v) {
        // if the swap button is not pressed, do nothing
        if (v.getId() != R.id.buttonSwap) return;
        // if the swap button is pressed and there is nothing to swap,
        // do nothing.
        if (swap) {
            boolean somethingToSwap = false;
            for (boolean selected: isSelectedBoolArr) {
                if (selected) {
                    somethingToSwap = true;
                }
            }
            if (!somethingToSwap) return;

            // if there is something to swap, call the SwapTileAction class and
            // send it to the game to replace the selected tile with a random
            // one.
            SwapTileAction sta = new SwapTileAction(this, isSelectedBoolArr);
            game.sendAction(sta);
            for (int i = 0; i< isSelectedBoolArr.length; i++) {
                isSelectedBoolArr[i] = false;
                myPlayerHand[i].setSelected(false);
            }
        }

        // if swap has not been clicked, keep the button as "Swap"
        swap = !swap;
        if (!swap) {
            buttonSwap.setText("Swap");
        }
        // if swap has been clicked, change the text of the button to "End"
        else {
            buttonSwap.setText("End");
        }

        // redraw after performing the action
        mainBoard.invalidate();
        sideBoard.invalidate();
    }
}
