package edu.up.cs301.qwirkle.player;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
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
import edu.up.cs301.qwirkle.ui.QwirkleView;
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
 * @version April 19, 2018
 */
public class QwirkleHumanPlayer extends GameHumanPlayer
        implements View.OnTouchListener, View.OnClickListener,
        CompoundButton.OnCheckedChangeListener{

    // instance variables
    private GameMainActivity activity; // The activity.
    private QwirkleGameState gameState; // The game state.
    private QwirkleRules rules = new QwirkleRules(); // For valid moves.
    private QwirkleTile[] myPlayerHand; // Current player's hand.

    // Boards (View objects)
    private MainBoard mainBoard;
    private SideBoard sideBoard;

    // TextViews
    private TextView textViewPlayerLabel;
    private TextView textViewTurnLabel;
    private TextView textViewMyScore;
    private TextView textViewTilesLeft;
    private TextView textViewMessageBoard;

    // Switch for night mode
    private Switch switchDarkMode;

    // Button for swapping.
    private Button buttonSwap;

    // The currently selected tile in hand.
    private int handSelectedIdx = -1;

    // Booleans
    private boolean swap = false; // Tells whether in swap mode.
    private boolean init = false; // Tells whether initialized constants.
    private boolean pass = false; // Tells whether in pass mode.

    /**
     * Constructor: QwirkleHumanPlayer
     * @param name The player's name.
     */
    public QwirkleHumanPlayer(String name) {
        super(name);
    }

    /**
     * Method: setAsGui
     * sets the current player as the activity's GUI
     *
     * @param activity The current activity object.
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        // Initialize passed activity object
        this.activity = activity;

        // Set the GUI XML file.
        activity.setContentView(R.layout.qwirkle_human_player);

        // Set the player TextView to the user's name.
        textViewPlayerLabel =
                (TextView)activity.findViewById(R.id.textViewPlayerLabel);
        textViewPlayerLabel.setText("My Name: " + name);

        // Initialize the TextViews by using findViewById.
        textViewTurnLabel =
                (TextView)activity.findViewById(R.id.textViewTurnLabel);
        textViewMyScore =
                (TextView)activity.findViewById(R.id.textViewPlayerScore);
        textViewTilesLeft =
                (TextView)activity.findViewById(R.id.textViewTilesLeft);
        textViewMessageBoard =
                (TextView)activity.findViewById(R.id.textViewMessageBoard);

        // Initialize swap button, main board, and side board & set listeners.
        buttonSwap = (Button)activity.findViewById(R.id.buttonSwap);
        buttonSwap.setOnClickListener(this);
        Button buttonScores = (Button)activity.findViewById(R.id.buttonScores);
        buttonScores.setOnClickListener(this);
        mainBoard = (MainBoard)activity.findViewById(R.id.mainBoard);
        mainBoard.setOnTouchListener(this);
        sideBoard = (SideBoard)activity.findViewById(R.id.sideBoard);
        sideBoard.setOnTouchListener(this);

        /*
         * External Citation
         * Date: April 17 2018
         * Problem: didn't know what kind of listener switch button used
         * for switch button
         * Source:
         * https://android--code.blogspot.com/2015/08/
         * android-switch-button-listener.html
         * Solution: Used setOnCheckedChangeListener
        */
        // initialize switch button & set listener to listen for changes
        switchDarkMode = (Switch) activity.findViewById(R.id.switchNightMode);
        switchDarkMode.setOnCheckedChangeListener(this);
    }

    /**
     * Method: onCheckedChanged
     * Determines whether Night Mode has been activated
     *
     * @param buttonView button to switch between Night Mode and regular mode
     * @param isChecked checks to see if button has been touched
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /*
         * External Citation
         * Date: April 17 2018
         * Problem: didn't know how to get activity's
         * content view to change background color
         * Source:
         * https://stackoverflow.com/questions/5273436/
         * how-to-get-activitys-content-view
         * Solution: used sample line of code
         */

        // Update background color based on if night mode switch is on
        View contentView = activity.findViewById(android.R.id.content);
        contentView.setBackgroundColor(isChecked ? Color.DKGRAY : Color.WHITE);

        // Set the night mode boolean.
        mainBoard.setNightModeAndUpdateColor(isChecked);
        sideBoard.setNightModeAndUpdateColor(isChecked);

        // Update TextView color and switch color.
        int viewColor = isChecked ? Color.WHITE : Color.BLACK;

        // Set the color of all the TextViews.
        textViewPlayerLabel.setTextColor(viewColor);
        textViewTurnLabel.setTextColor(viewColor);
        textViewMyScore.setTextColor(viewColor);
        textViewTilesLeft.setTextColor(viewColor);
        textViewMessageBoard.setTextColor(viewColor);

        // Set the switch text and color.
        switchDarkMode.setTextColor(viewColor);
        switchDarkMode.setText("Night Mode: " + (isChecked ? "ON" : "OFF"));

        // Invalidate both boards.
        mainBoard.invalidate();
        sideBoard.invalidate();
    }

    /**
     * The top view of the current state
     *
     * @return the top view of the project
     */
    @Override
    public View getTopView() {
        return activity.findViewById(R.id.top_gui_layout);
    }

    /**
     * Method: updateDisplay
     * Update display of the turn, score, tiles, and previous action
     */
    private void updateDisplay() {
        /*
            * External Citation
            * Date: April 11 2018
            * Problem: Couldn't update the GUI correctly
            * Source:
            * https://github.com/srvegdahl/CounterGame/blob/master/app/src/
            * main/java/edu/up/cs301/counter/CounterHumanPlayer.java
            * Solution:
            * Used Vegdahl's code as reference
        */
        // Update turn
        textViewTurnLabel.setText
                ("Turn: " + allPlayerNames[gameState.getTurn()]);
        // Update my score
        textViewMyScore.setText
                ("My Score: " + gameState.getPlayerScore(playerNum));
        // Update number of tiles left.
        textViewTilesLeft.setText("Tiles Left: " + gameState.getTilesLeft());
        // Update message board.
        textViewMessageBoard.setText(gameState.getMessageBoardString());

        // Ensure swap button is initially enabled. Then update if needed.
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

    /**
     * Method: setConstants
     * Sets class constants related to board size and initializes bitmaps
     */
    private void setConstants() {
        // Constants for main board.
        CONST.RECTDIM_MAIN = mainBoard.getHeight() / CONST.BOARD_HEIGHT;
        CONST.OFFSET_MAIN =
                (mainBoard.getWidth()-(CONST.BOARD_WIDTH*CONST.RECTDIM_MAIN))/2;

        // Constants for side board.
        CONST.RECTDIM_SIDE = sideBoard.getHeight() / CONST.NUM_IN_HAND;
        CONST.OFFSET_SIDE = (sideBoard.getWidth() - CONST.RECTDIM_SIDE) / 2;

        // Initialize the bitmaps.
        QwirkleView.initBitmaps(activity);

        init = true;
    }

    /**
     * Method: receiveInfo
     * Callback method, called when player gets a message
     *
     * @param info the message;
     */
    @Override
    public void receiveInfo(GameInfo info) {
        // if the move was out of turn or otherwise illegal, flash the screen
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

        //update gameState for main and side board
        mainBoard.setGameState(gameState);
        sideBoard.setGameState(gameState);

        // Initialize the constants if needed.
        if (!init) setConstants();

        mainBoard.invalidate();
        sideBoard.invalidate();
    }

    /**
     * Method: onTouch
     * Callback method when the screen is touched. We're looking for a screen
     * touch (which we'll detect on the "down" movement onto a Qwirkle board
     * spot)
     *
     * @param v the surface view
     * @param event the motion event that was detected
     * @return true if the touch was registered
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Ignore if it isn't your turn or it is not "ACTION_DOWN"
        if (gameState.getTurn() != playerNum) return false;
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        // get the x and y coordinates of the touch-location
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
            Point coordinates = getSelectedBoardIdx(x, y);
            if (coordinates == null) return false;

            // Create the PlaceTileAction object.
            PlaceTileAction pta = new PlaceTileAction(this, coordinates.x,
                    coordinates.y, handSelectedIdx);

            // Reset selected tile.
            myPlayerHand[handSelectedIdx].setSelected(false);
            handSelectedIdx = -1;
            mainBoard.setLegalMoves(null);

            // Send the action to the game & invalidate boards.
            game.sendAction(pta);
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

            //if there is no tile where the user touches, return false
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
            //Redraw the boards
            mainBoard.invalidate();
            sideBoard.invalidate();
            return true;
        }

        return false;
    }

    /**
     * return a Point in main board while taking into account of the offset
     *
     * @param x x-position of spot on board
     * @param y y-position of spot on board
     * @return position of main board
     */
    private Point getSelectedBoardIdx(int x, int y) {
        // if the position selected is outside the main board, do nothing
        if (x < CONST.OFFSET_MAIN || x > CONST.RECTDIM_MAIN *
                CONST.BOARD_WIDTH + CONST.OFFSET_MAIN) {
            return null;
        }

        // Return position on main board as an instance of Point.
        Point boardCoordinates = new Point();
        boardCoordinates.x = (x - CONST.OFFSET_MAIN) / CONST.RECTDIM_MAIN;
        boardCoordinates.y = y / CONST.RECTDIM_MAIN;
        return boardCoordinates;
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

    /**
     * Method: showScoreBoard
     * Displays the scoreboard with each player's current score in the form
     * of a table
     */
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

        //set up/build scoreboard in the layout of a table
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Context dialogContext = builder.getContext();
        LayoutInflater inflater = LayoutInflater.from(dialogContext);
        View scoreView = inflater.inflate(R.layout.qwirkle_scoreboard, null);
        builder.setView(scoreView);
        TableLayout tableLayout =
                (TableLayout)scoreView.findViewById(R.id.tableLayout);

        // Set the background color of the score board.
        tableLayout.setBackgroundColor(
                switchDarkMode.isChecked() ? Color.DKGRAY : Color.WHITE);

        // To change the color of the text with dark mode.
        int textColor = switchDarkMode.isChecked() ? Color.WHITE : Color.BLACK;

        // Set the textColor value to the label TextViews.
        TextView scoreBoardIdLabel =
                (TextView)scoreView.findViewById(R.id.scoreBoardIdLabel);
        scoreBoardIdLabel.setTextColor(textColor);
        TextView scoreBoardNameLabel =
                (TextView)scoreView.findViewById(R.id.scoreBoardNameLabel);
        scoreBoardNameLabel.setTextColor(textColor);
        TextView scoreBoardTypeLabel =
                (TextView)scoreView.findViewById(R.id.scoreBoardTypeLabel);
        scoreBoardTypeLabel.setTextColor(textColor);
        TextView scoreBoardScoreLabel =
                (TextView)scoreView.findViewById(R.id.scoreBoardScoreLabel);
        scoreBoardScoreLabel.setTextColor(textColor);

        // To determine if they are the winner.
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

            // See if they are the current winner.
            boolean winner = currentWinners.contains(i);

            //sets up player Id, checks if player is winning
            //bolds id if they are winning
            TextView textViewPlayerId = new TextView(dialogContext);
            textViewPlayerId.setTextColor(textColor);
            textViewPlayerId.setText(Integer.toString(i));
            textViewPlayerId.setTextSize(24f);
            if (winner) textViewPlayerId.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerId);

            //sets up player name, checks if player is winning
            //bolds id if they are winning
            TextView textViewPlayerName = new TextView(dialogContext);
            textViewPlayerName.setTextColor(textColor);
            textViewPlayerName.setText(allPlayerNames[i]);
            textViewPlayerName.setTextSize(24f);
            if (winner) textViewPlayerName.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerName);

            //sets up player type, checks if player is winning
            //bolds type if they are winning
            TextView textViewPlayerType = new TextView(dialogContext);
            textViewPlayerType.setTextColor(textColor);
            textViewPlayerType.setText(gameState.getPlayerTypeAtIdx(i));
            textViewPlayerType.setTextSize(24f);
            if (winner) textViewPlayerType.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerType);

            //sets up player score, checks if player is winning
            //bolds score if they are winning
            TextView textViewPlayerScore = new TextView(dialogContext);
            textViewPlayerScore.setTextColor(textColor);
            textViewPlayerScore.setText
                    (Integer.toString(gameState.getPlayerScore(i)));
            textViewPlayerScore.setTextSize(24f);
            if (winner) textViewPlayerScore.setTypeface(null, Typeface.BOLD);
            tableRow.addView(textViewPlayerScore);

            //add row in table for each player
            tableLayout.addView(tableRow);
        }

        // Show the scoreboard as a popup that can be closed
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Method: onClick
     * callback method when something is clicked on the screen
     *
     * @param v the surface view
     */
    @Override
    public void onClick(View v) {
        // Only allow the scoreboard to be accessed when not your turn.
        if (v.getId() != R.id.buttonScores && gameState.getTurn() != playerNum) {
            return;
        }

        // if the swap or scores button is not pressed, do nothing
        if (v.getId() != R.id.buttonSwap && v.getId() != R.id.buttonScores) {
            return;
        }

        //if scores button is pressed, call showScoreBoard
        if (v.getId() == R.id.buttonScores) {
            showScoreBoard();
            return;
        }

        //if swap button is pass button and it is pressed,
        // create new PassAction and send it to the game
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
