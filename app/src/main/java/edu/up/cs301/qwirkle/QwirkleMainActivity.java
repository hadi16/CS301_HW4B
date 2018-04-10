package edu.up.cs301.qwirkle;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * Class: QwirkleMainActivity
 * This class contains the code to interact with the QwirkleMainActivity XML.
 *
 * @author Alex Hadi
 * @version February 24, 2018
 */
public class QwirkleMainActivity extends GameMainActivity {
    private static final int PORT_NUMBER = 2236;

    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();

        // a human player player type (player type 0)
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new QwirkleHumanPlayer(name);
            }});

        // a dumb computer player type (player type 1)
        playerTypes.add(new GamePlayerType("Computer Player (Dumb)") {
            public GamePlayer createPlayer(String name) {
                return new QwirkleComputerPlayerDumb(name);
            }});

        // a smart computer player type (player type 2)
        playerTypes.add(new GamePlayerType("Computer Player (Smart)") {
            public GamePlayer createPlayer(String name) {
                return new QwirkleComputerPlayerSmart(name);
            }});

        // Create a game configuration class for Qwirkle
        // - player types as given above
        // - from 1 to 2 players
        // - name of game is "Qwirkle"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 1, 2, "Qwirkle",
                PORT_NUMBER);

        // Add the default players to the configuration
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a dumb computer
        // player

        // Set the default remote-player setup:
        // - player name: "Remote Player"
        // - IP code: (empty string)
        // - default player type: human player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        // Return the configuration
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {
        return new QwirkleLocalGame();
    }
}
