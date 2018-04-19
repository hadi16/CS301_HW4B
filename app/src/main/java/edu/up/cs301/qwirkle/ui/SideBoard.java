package edu.up.cs301.qwirkle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import edu.up.cs301.qwirkle.CONST;
import edu.up.cs301.qwirkle.tile.QwirkleTile;

/**
 * Class: SideBoard
 * This class contains the code to draw the SideBoard.
 *
 * @author Alex Hadi
 * @author Michael Quach
 * @author Huy Nguyen
 * @author Stephanie Camacho
 * @version April 18, 2018
 */
public class SideBoard extends QwirkleView {
    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     */
    public SideBoard(Context context){
        super(context);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     * @param defStyleAttr The style attribute.
     */
    public SideBoard(Context context, @Nullable AttributeSet attrs,
                     int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     * @param defStyleAttr The style attribute.
     * @param defStyleRes The style resource.
     */
    public SideBoard(Context context, @Nullable AttributeSet attrs,
                     int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Method: onDraw
     * Given a Canvas object, draws SideBoards.
     * @param canvas Canvas object used to draw SideBoards onto view.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Sets background color to white or dark gray
        canvas.drawColor(nightMode ? Color.DKGRAY : Color.WHITE);

        // Draws the SideBoard.
        for (int i=0; i< CONST.NUM_IN_HAND; i++){
            canvas.drawRect(CONST.OFFSET_SIDE, i*CONST.RECTDIM_SIDE,
                    CONST.OFFSET_SIDE+CONST.RECTDIM_SIDE,
                    (i+1)*CONST.RECTDIM_SIDE, gridPaint);
        }

        // Tiles are drawn if game state isn't null.
        if (gameState == null) return;
        QwirkleTile[] myPlayerHand = gameState.getMyPlayerHand();
        for (QwirkleTile tile : myPlayerHand) {
            if (tile != null) drawTile(canvas, tile);
        }
    }
}
