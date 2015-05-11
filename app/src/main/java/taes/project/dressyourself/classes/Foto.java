package taes.project.dressyourself.classes;

import android.graphics.Bitmap;

/**
 * Created by Salomon on 01/05/2015.
 */
public class Foto {
    private Bitmap image;
    private String title;

    public Foto(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
