package edu.up.cs301.qwirkle.player;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.qwirkle.CONST;
import edu.up.cs301.qwirkle.QwirkleGameState;
import edu.up.cs301.qwirkle.QwirkleRules;
import edu.up.cs301.qwirkle.action.PassAction;
import edu.up.cs301.qwirkle.action.PlaceTileAction;
import edu.up.cs301.qwirkle.action.SwapTileAction;
import edu.up.cs301.qwirkle.tile.QwirkleTile;
import edu.up.cs301.qwirkle.ui.MainBoard;
import edu.up.cs301.qwirkle.ui.SideBoard;

/**
 * Class: QwirkleHumanPlayer
 * A GUI that allows a human to play Qwirkle. Moves are made by selecting tiles
 * on the side board and placing them on the main board.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 14, 2018
 */

public class QwirkleHumanPlayer extends GameHumanPlayer
        implements View.OnTouchListener, View.OnClickListener {
    private GameMainActivity activity; // The activity
    private QwirkleGameState gameState; // The game state
    private QwirkleRules rules = new QwirkleRules(); // Instance of rules

    // Array of the current player's hand
    private QwirkleTile[] myPlayerHand;

    // Boards (View objects)
    private MainBoard mainBoard;
    private SideBoard sideBoard;

    // TextViews
    private TextView textViewTurnLabel;
    private TextView textViewMyScore;
    private TextView textViewTilesLeft;

    // Button for swapping.
    private Button buttonSwap;

    private int handSelectedIdx = -1; // The currently selected tile in hand.

    // Booleans
    private boolean swap = false; // Tells whether in swap mode.
    private boolean init = false; // Tells whether initialized constants.
    private boolean pass = false; // Tells whether in pass mode.

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
        // Initialize passed activity object
        this.activity = activity;

        // Set the GUI to the appropriate XML file.
        activity.setContentView(R.layout.qwirkle_human_player);

        // Set the player TextView to the user's name.
        TextView textViewPlayerLabel = (TextView)activity.findViewById(
                R.id.textViewPlayerLabel);
        textViewPlayerLabel.setText("My Name: " + name);

        // Initialize the TextViews by using findViewById.
        textViewTurnLabel = (TextView)activity.findViewById(
                R.id.textViewTurnLabel);
        textViewMyScore = (TextView)activity.findViewById(R.id.textViewPlayerScore);
        textViewTilesLeft = (TextView)activity.findViewById(R.id.textViewTilesLeft);

        // Initialize swap button, main board, and side board & set listeners.
        buttonSwap = (Button)activity.findViewById(R.id.buttonSwap);
        buttonSwap.setOnClickListener(this);
        Button buttonScores = (Button)activity.findViewById(R.id.buttonScores);
        buttonScores.setOnClickListener(this);
        mainBoard = (MainBoard)activity.findViewById(R.id.mainBoard);
        mainBoard.setOnTouchListener(this);
        sideBoard = (SideBoard)activity.findViewById(R.id.sideBoard);
        sideBoard.setOnTouchListener(this);
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

    /*
     * External Citation
     * Date: April 11 2018
     * Problem: Couldn't update the GUI correctly
     * Source:
     * https://github.com/srvegdahl/CounterGame/blob/master/app/src/main/java/
     * edu/up/cs301/counter/CounterHumanPlayer.java
     * Solution:
     * Used Vegdahl's code as reference
     */
    private void updateDisplay() {
        textViewTurnLabel.setText("Turn: " + allPlayerNames[gameState.getTurn()]);
        textViewMyScore.setText("My Score: " + gameState.getPlayerScore(playerNum));
        textViewTilesLeft.setText("Tiles Left: " + gameState.getTilesLeft());

        // Ensure swap button is initially enabled.
        buttonSwap.setEnabled(true);

        if (gameState.getTilesLeft() == 0) {
            swap = false;
            if (rules.validMovesExist(myPlayerHand, gameState.getBoard())) {
                // Disable the swap button if no tiles in draw pile.
                buttonSwap.setEnabled(false);
                pass = false;
            }
            else {
                buttonSwap.setText("Pass");
                pass = true;
            }
        }
    }

    private void setConstants() {
        // Constants for main board.
        CONST.RECTDIM_MAIN = mainBoard.getHeight() / CONST.BOARD_HEIGHT;
        CONST.OFFSET_MAIN = (mainBoard.getWidth() - (CONST.BOARD_WIDTH*CONST.RECTDIM_MAIN)) / 2;

        // Constants for side board.
        CONST.RECTDIM_SIDE = sideBoard.getHeight() / CONST.NUM_IN_HAND;
        CONST.OFFSET_SIDE = (sideBoard.getWidth() - CONST.RECTDIM_SIDE) / 2;

        // Initialize the bitmaps.
        QwirkleTile.initBitmaps(activity);

        init = true;
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
        if (info instanceof IllegalMoveInfo||info instanceof NotYourTurnInfo) {
            flash(Color.RED, 50);
            return;
        }
        else if (!(info instanceof QwirkleGameState)) {
            // if we do not have a QwirkleGameState, ignore
            return;
        }

        // initializes the game state and myPlayerHand
        this.gameState = (QwirkleGameState)info;
        this.myPlayerHand = gameState.getMyPlayerHand();

        // Update the display.
        updateDisplay();

        mainBoard.setGameState(gameState);
        sideBoard.setGameState(gameState);

        mainBoard.invalidate();
        sideBoard.invalidate();

        // Initialize the constants if needed.
        if (!init) setConstants();
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

        // If the main board is selected.
        if (v.getId() == R.id.mainBoard) {
            // Do nothing if in swap mode.
            if (swap) return false;

            // To prevent the user from selecting board before tile in hand
            if (handSelectedIdx == -1) return false;

            // Get where the board is selected
            int[] xyPos = getSelectedBoardIdx(x, y);
            if (xyPos == null) return false;

            // Create the PlaceTileAction object.
            PlaceTileAction pta = new PlaceTileAction(this, xyPos[0], xyPos[1],
                    handSelectedIdx);

            // Reset selected tile.
            myPlayerHand[handSelectedIdx].setSelected(false);
            handSelectedIdx = -1;
            mainBoard.setLegalMoves(null);

            // Send the action to the game.
            game.sendAction(pta);

            // Redraw the boards.
            mainBoard.invalidate();
            sideBoard.invalidate();

            // return true if touch has been registered
            return true;
        }
        // access the side board in order to allow touch detection
        else if (v.getId() == R.id.sideBoard) {
            // Get the yPos of the tile & check to make sure it is valid.
            int yPos = getSelectedHandIdx(x, y);
            if (yPos == -1) return false;

            QwirkleTile tileSelected = myPlayerHand[yPos];
            if (tileSelected == null) return false;

            // Revert the value of isSelected.
            tileSelected.setSelected(!tileSelected.isSelected());

            // To prevent multiple tiles from being selected when not in swap.
            if (!swap) {
                if (tileSelected.isSelected()) {
                    handSelectedIdx = yPos;
                    mainBoard.setLegalMoves(rules.getLegalMoves(tileSelected,
                            gameState.getBoard()));
                }
                else {
                    handSelectedIdx = -1;
                    mainBoard.setLegalMoves(null);
                }
                for (int i = 0; i < myPlayerHand.length; i++) {
                    if (yPos == i) continue;
                    if (myPlayerHand[i] != null) {
                        myPlayerHand[i].setSelected(false);
                    }
                }
            }

            // Redraw the boards.
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
        if (x < CONST.OFFSET_MAIN || x > CONST.RECTDIM_MAIN *
                CONST.BOARD_WIDTH + CONST.OFFSET_MAIN) {
            return null;
        }

        int[] xyPos = new int[2];
        // X position
        xyPos[0] = (x - CONST.OFFSET_MAIN) / CONST.RECTDIM_MAIN;
        // Y position
        xyPos[1] = y / CONST.RECTDIM_MAIN;

        // return position on main board as array of ints.
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
        if (x < CONST.OFFSET_SIDE || x > CONST.RECTDIM_SIDE *
                CONST.NUM_IN_HAND+ CONST.OFFSET_SIDE) {
            return -1;
        }
        // return the hand index selected
        return y / CONST.RECTDIM_SIDE;
    }

    private void showScoreBoard() {
        /*
        * External Citation
        * Date: April 15 2018
        * Problem: Wanted to create a custom view for the scoreboard.
        * Source:
        * https://stackoverflow.com/questions/40650215/
        * how-to-add-table-layout-on-alertdialog
        * Solution:
        * Used a slightly modified version of that code.
        */
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Context dialogContext = builder.getContext();
        LayoutInflater inflater = LayoutInflater.from(dialogContext);
        View scoreView = inflater.inflate(R.layout.qwirkle_scoreboard, null);
        builder.setView(scoreView);
        TableLayout tableLayout = (TableLayout)scoreView.findViewById(R.id.tableLayout);

        ArrayList currentWinners = gameState.getWinners();
        for (int i=0; i<allPlayerNames.length; i++) {
            /*
            * External Citation
            * Date: April 15 2018
            * Problem: Wanted to set bold font.
            * Source:
            * https://stackoverflow.com/questions/4792260/
            * how-do-you-change-text-to-bold-in-android
            * Solution:
            * Used Typeface.BOLD
            */
            TableRow tableRow = new TableRow(dialogContext);
            tableRow.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            boolean winner = currentWinners.contains(i);

            TextView textViewPlayerName = new TextView(dialogContext);
            textViewPlayerName.setTextColor(Color.BLACK);
            textViewPlayerName.setText(allPlayerNames[i]);
            textViewPlayerName.setTextSize(24f);
            if (winner) textViewPlayerName.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerName);

            TextView textViewPlayerId = new TextView(dialogContext);
            textViewPlayerId.setTextColor(Color.BLACK);
            textViewPlayerId.setText(Integer.toString(i));
            textViewPlayerId.setTextSize(24f);
            if (winner) textViewPlayerId.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerId);

            TextView textViewPlayerScore = new TextView(dialogContext);
            textViewPlayerScore.setTextColor(Color.BLACK);
            textViewPlayerScore.setText(Integer.toString(gameState.getPlayerScore(i)));
            textViewPlayerScore.setTextSize(24f);
            if (winner) textViewPlayerScore.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerScore);

            tableLayout.addView(tableRow);
        }

        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * callback method when something is clicked on the screen
     *
     * @param v
     *          the surface view
     */
    @Override
    public void onClick(View v) {
        // if the swap or scores button is not pressed, do nothing
        if (v.getId() != R.id.buttonSwap && v.getId() != R.id.buttonScores) {
            return;
        }

        if (v.getId() == R.id.buttonScores) {
            showScoreBoard();
            return;
        }

        if (v.getId() == R.id.buttonSwap && pass) {
            PassAction pa = new PassAction(this);
            game.sendAction(pa);
            return;
        }

        // If swap button is pressed and there is nothing to swap, do nothing.
        if (swap) {
            boolean somethingToSwap = false;
            for (QwirkleTile tile : myPlayerHand) {
                if (tile == null) continue;
                if (tile.isSelected()) {
                    somethingToSwap = true;
                }
            }

            if (somethingToSwap) {
                // If there is something to swap, call the SwapTileAction and
                // send to game to replace the selected tile with random one
                SwapTileAction sta = new SwapTileAction(this, myPlayerHand);
                game.sendAction(sta);

                // Reset isSelected in myPlayerHand.
                for (int i = 0; i < myPlayerHand.length; i++) {
                    if (myPlayerHand[i] != null) {
                        myPlayerHand[i].setSelected(false);
                    }
                }
            }
        }

        // Revert the swap boolean.
        swap = !swap;

        // If button clicked, make text "End" (otherwise keep it as "Swap")
        if (swap) {
            buttonSwap.setText("End");
            mainBoard.setLegalMoves(null);
        }
        else {
            buttonSwap.setText("Swap");
        }

        // Redraw after performing the action
        mainBoard.invalidate();
        sideBoard.invalidate();
    }
}
