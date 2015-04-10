package taes.project.dressyourself;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ver si el usuario actual es un usuario anónimo
        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
            // si es anónimo le mostramos el lógin
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            // no es anónimo le llevamos a la aplicación principal
            Intent intent = new Intent(this,DressYourSelfActivity.class);
            startActivity(intent);
            finish();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Decodificacion de imagen
     * Si es para mostrar en una vista pasar dimensiones de dicha vista
     * Si es para hacer Parse pasar dimensiones maximas permitidas para la subida
     * @param anchura anchura de la imagen devuelta
     * @param altura altura de la imagen devuelta
     * @return bitmap decodificada
     */
    private Bitmap decodificarImagen(int anchura, int altura) {

        //Borrar (Para no dar error)
        String mCurrentPhotoPath = "";

        // Dimensiones del bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        //mCurrentPhotoPath es la ruta de la imagen a decodificar
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoAnchura = bmOptions.outWidth;
        int photoAltura = bmOptions.outHeight;

        // Determina la escala
        int scaleFactor = Math.min(photoAnchura/anchura, photoAltura/altura);

        // Decodificacion de la imagen
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        return bitmap;
    }


}
