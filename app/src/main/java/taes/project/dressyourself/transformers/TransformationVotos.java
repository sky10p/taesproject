package taes.project.dressyourself.transformers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.squareup.picasso.Transformation;

/**
 * Created by pablo on 21/05/15.
 */
public class TransformationVotos implements Transformation {

    private String gText;
    private Context context;
    private final int newWidth=130;
    private final int newHeight=130;

    public TransformationVotos(Context c,String texto){
        context = c;
        gText=texto;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {


        Bitmap output=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight());

        Canvas canvas = new Canvas(output);


        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels

        paint.setTextSize((int) (14 *  context.getResources().getDisplayMetrics().density));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2;


        canvas.drawText(gText, x, y, paint);


        if(output!=bitmap){
            bitmap.recycle();
        }


        return output;
    }

    @Override
    public String key() {
        return "transformacionvotos";
    }
}
