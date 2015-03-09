package taes.project.dressyourself.animation;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by pablo on 9/03/15.
 */
public class LayoutAnimation extends Animation {

    int layout_width;
    int layout_height;
    int inicialHeight;
    int inicialWidth;
    View view;

    public LayoutAnimation(Context c,int width, int height, View v){
        view=v;
        inicialHeight=view.getLayoutParams().height;
        inicialWidth=c.getResources().getDisplayMetrics().widthPixels;

        layout_height= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, c.getResources().getDisplayMetrics());;
        layout_width= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, c.getResources().getDisplayMetrics());;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().height = inicialHeight+(int) ((layout_height-inicialHeight) * interpolatedTime);
        view.getLayoutParams().width=inicialWidth+(int)((layout_width-inicialWidth)*interpolatedTime);
        view.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
