package edu.up.cs301.qwirkle;

import android.content.Context;
import android.media.MediaPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;

/**
 * Class contains the sound effects of the game
 *
 * @author Alex Hadi
 * @author Micheal Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version 4/26/2018
 */
public class QwirkleGameSound {
    /*
    External Citation:
    Date: 4/26/2018
    Problem: Don't know how to add sounds
    Resource:
        https://developer.android.com/reference/android/media/MediaPlayer
    Solution: Use android developer page as a reference
    */
    //Instance variables of sounds
    private MediaPlayer errorSound = new MediaPlayer();
    private MediaPlayer qwirkleSound = new MediaPlayer();
    private MediaPlayer placeTileSound = new MediaPlayer();
    private MediaPlayer swapSound = new MediaPlayer();

    /**
     * Ctor
     * @param a pass in a GameMainActivity to get the context
     */
    public QwirkleGameSound(GameMainActivity a){
        //Create a GameMainActivity
        GameMainActivity activity = a;
        //Get the reference of the context
        Context ctx = activity.getApplicationContext();
        //Assign the sounds
        errorSound = MediaPlayer.create(ctx, R.raw.error_sound);
        qwirkleSound = MediaPlayer.create(ctx, R.raw.qwirkle_sound);
        placeTileSound = MediaPlayer.create(ctx, R.raw.place_tile_sound);
        swapSound = MediaPlayer.create(ctx, R.raw.swap_sound);
    }

    //Methods that simply plays the sound
    public void playErrorSound(){
        errorSound.start();
    }
    public void playQwirkleSound() { qwirkleSound.start(); }
    public void playPlaceTileSound() { placeTileSound.start(); }
    public void playSwapSound() { swapSound.start(); }
}
