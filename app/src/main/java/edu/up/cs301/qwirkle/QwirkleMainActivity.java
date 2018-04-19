package edu.up.cs301.qwirkle;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;
import edu.up.cs301.qwirkle.player.QwirkleComputerPlayerDumb;
import edu.up.cs301.qwirkle.player.QwirkleComputerPlayerSmart;
import edu.up.cs301.qwirkle.player.QwirkleHumanPlayer;

/**
 * Class: QwirkleMainActivity
 * This class contains the code to interact with the QwirkleMainActivity XML.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 18, 2018
 */
public class QwirkleMainActivity extends GameMainActivity {
    // Have a high port number to avoid conflicts.
    private static final int PORT_NUMBER = 12236;

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
        playerTypes.add(new GamePlayerType("Dumb AI") {
            public GamePlayer createPlayer(String name) {
                return new QwirkleComputerPlayerDumb(name);
            }});

        // a smart computer player type (player type 2)
        playerTypes.add(new GamePlayerType("Smart AI") {
            public GamePlayer createPlayer(String name) {
                return new QwirkleComputerPlayerSmart(name);
            }});

        // Create a game configuration class for Qwirkle
        // - player types as given above
        // - from 1 to 2 players
        // - name of game is "Qwirkle"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Qwirkle",
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
