package taes.project.dressyourself.animation;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by pablo on 9/03/15.
 */
public class VisibilityAnimationListener implements Animation.AnimationListener {

    View view;
    int typeStart;
    int typeEnd;

    public VisibilityAnimationListener(View v, int typeStart, int typeEnd){
       view=v;
       this.typeStart=typeStart;
        this.typeEnd=typeEnd;
    }




    @Override
    public void onAnimationStart(Animation animation) {

        view.setVisibility(typeStart);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        view.setVisibility(typeEnd);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
