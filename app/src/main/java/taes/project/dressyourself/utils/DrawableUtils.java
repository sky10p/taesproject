package taes.project.dressyourself.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import taes.project.dressyourself.listeners.onDrawableLoaded;

/**
 * Created by Pablo on 20/05/2015.
 */
public class DrawableUtils {

    public static void getDrawableFromUrl(final String link, final onDrawableLoaded drawableLoaded) {


        AsyncTask<Void,Void,Boolean> task= new AsyncTask<Void, Void, Boolean>() {

            private Drawable drawable;
            private URL url;
            @Override
            protected Boolean doInBackground(Void... params) {

                url= null;

                try {
                    url = new URL(link);
                    InputStream in = (InputStream) url.getContent();
                    drawable = Drawable.createFromStream(in,"header");

                } catch (IOException e) {
                    drawableLoaded.someError(e);
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean correcto) {
                super.onPostExecute(correcto);
                if(correcto){
                    drawableLoaded.done(drawable);
                }
            }
        };

        task.execute();




    }
}
