package up.cs301.qwirkle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Hashtable;

public class QwirkleBoard extends View {
    private static final int scaleDim = 12;
    private QwirkleTile board[][] = new QwirkleTile[scaleDim][scaleDim];
    private Hashtable<String, Bitmap> bitmapHashtable = new Hashtable<>();

    private Paint blackPaint = new Paint();

    private String[] QwirkleColor =
            {"blue", "green", "orange", "purple", "yellow", "red"};

    private String[] QwirkleAnimal =
            {"bat", "owl", "snake", "dog", "bird", "fox"};

    public QwirkleBoard(Context context){
        super(context);
        generalInit();
    }

    public QwirkleBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        generalInit();
    }

    public QwirkleBoard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        generalInit();
    }

    public QwirkleBoard(Context context, AttributeSet attrs, int defStyleAttr,
                        int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        generalInit();
    }

    private void generalInit(){
        setWillNotDraw(false);
        createBitmapHashTable();
    }

    @Override
    public void onDraw(Canvas canvas){
        int rectDim = canvas.getHeight() / scaleDim;

        canvas.drawColor(Color.WHITE);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3.0f);
        blackPaint.setStyle(Paint.Style.STROKE);

        int offset = (canvas.getWidth() - (scaleDim*rectDim)) / 2;
        for (int i = 0; i< scaleDim; i++){
            for (int j = 0; j< scaleDim; j++){
                canvas.drawRect(i*rectDim+offset, j*rectDim,
                        (i+1)*rectDim+offset, (j+1)*rectDim, blackPaint);
            }
        }

        board[3][2] = new QwirkleTile(3, 2, rectDim, offset, getBitmap("dog", "red"));
        board[3][3] = new QwirkleTile(3, 3, rectDim, offset, getBitmap("dog", "blue"));
        board[3][4] = new QwirkleTile(3, 4, rectDim, offset, getBitmap("dog", "green"));
        board[3][5] = new QwirkleTile(3, 5, rectDim, offset, getBitmap("dog", "yellow"));
        board[2][5] = new QwirkleTile(2, 5, rectDim, offset, getBitmap("bird", "yellow"));
        board[4][5] = new QwirkleTile(4, 5, rectDim, offset, getBitmap("snake", "yellow"));
        board[5][5] = new QwirkleTile(5, 5, rectDim, offset, getBitmap("fox", "yellow"));
        board[5][6] = new QwirkleTile(5, 6, rectDim, offset, getBitmap("fox", "blue"));
        board[5][7] = new QwirkleTile(5, 7, rectDim, offset, getBitmap("fox", "green"));
        board[3][6] = new QwirkleTile(3, 6, rectDim, offset, getBitmap("dog", "orange"));
        board[3][7] = new QwirkleTile(3, 7, rectDim, offset, getBitmap("dog", "purple"));

        for (QwirkleTile[] x : board) {
            for (QwirkleTile tile : x) {
                if (tile != null) tile.drawTile(canvas, blackPaint);
            }
        }
    }

    private Bitmap getBitmap(String animal, String color) {
        return bitmapHashtable.get(animal+"_"+color);
    }

    private void createBitmapHashTable() {
        for (String animal: QwirkleAnimal) {
            for (String color: QwirkleColor) {
                String idName = animal+"_"+color;
                int id = getResources().getIdentifier(idName, "drawable",
                        this.getClass().getPackage().getName());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
                if (bitmap != null) bitmapHashtable.put(idName, bitmap);
            }
        }
    }
}
