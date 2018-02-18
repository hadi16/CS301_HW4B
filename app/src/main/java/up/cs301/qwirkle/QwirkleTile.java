package up.cs301.qwirkle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class QwirkleTile {
    private Bitmap tile;
    private int xPos;
    private int yPos;
    private int dimension;
    private int offset;

    QwirkleTile(int xPos, int yPos, int dimension, int offset, Bitmap tile) {
        this.tile = Bitmap.createScaledBitmap(tile, dimension, dimension, false);
        this.xPos = xPos;
        this.yPos = yPos;
        this.dimension = dimension;
        this.offset = offset;
    }

    void drawTile(Canvas canvas, Paint paint) {
        canvas.drawBitmap(tile, xPos*dimension+offset, yPos*dimension, paint);
    }
}
