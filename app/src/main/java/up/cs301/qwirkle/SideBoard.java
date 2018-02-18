package up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Class: SideBoard
 * This class contains the code to draw both SideBoards.
 * Inherits from QwirkleView.
 *
 * @author Alex Hadi
 * @version February 18, 2018
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
    public SideBoard(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    /**
     * Constructor: SideBoard
     * @param context Object holds the current context of the view.
     * @param attrs Object holds the attributes for the view.
     */
    public SideBoard(Context context, AttributeSet attrs, int defStyleAttr,
                     int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Method: onDraw
     * Given a Canvas object, draws SideBoards.
     * @param canvas Canvas object used to draw SideBoards onto view.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Offset needed to center the SideBoard.
        int rectDim = canvas.getHeight() / 6;
        int offset = (canvas.getWidth() - rectDim) / 2;

        // Sets background color to white.
        canvas.drawColor(Color.WHITE);

        // Draws the SideBoard.
        for (int i = 0; i< 6; i++){
            canvas.drawRect(offset, i*rectDim, offset+rectDim, (i+1)*rectDim,
                    blackPaint);
        }
    }
}
