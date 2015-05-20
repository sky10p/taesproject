package taes.project.dressyourself.listeners;

import android.graphics.drawable.Drawable;

import java.io.IOException;

/**
 * Created by Pablo on 20/05/2015.
 */
public interface onDrawableLoaded {
    void someError(IOException e);

    void done(Drawable drawable);
}
