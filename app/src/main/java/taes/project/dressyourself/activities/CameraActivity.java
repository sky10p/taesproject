package taes.project.dressyourself.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.AdapterCategoria;
import taes.project.dressyourself.classes.Categoria;
import taes.project.dressyourself.fragments.CategoryPhotoDialogFragment;


public class CameraActivity extends ActionBarActivity implements CategoryPhotoDialogFragment.CategoriaDialogListener {

    //Entero que especifica la cantidad de fotos a tirar
    static final int REQUEST_IMAGE_CAPTURE = 0;
    //Camino en el cual esta guardada la foto
    private String mCurrentPhotoPath;
    
    private String RESIZE = "resize";

    //Lista de categorias a mostrar
    List<Categoria> categorias = null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPhoto();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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



    public void addPhoto() {

        categorias = Categoria.getAllByUser(ParseUser.getCurrentUser(), new AdapterCategoria.AdapterCategoriaCallback() {
            @Override
            public void onDataLoaded() {
                CategoryPhotoDialogFragment dialog = new CategoryPhotoDialogFragment();
                dialog.setCategorias(categorias);
                dialog.setCancelable(false);
                dialog.setDialogListener(CameraActivity.this);
                dialog.show(getFragmentManager(), "Categorias dialog");
            }
        });
    }





    //Crea una imagen unica en memoria destino  y la devuelve
    private File createImageFile(String category) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES +"/"+ParseUser.getCurrentUser().getUsername()+"/"+category);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    //invoca a la app camara y captura la imagen
    public void dispatchTakePictureIntent(String category) {
        //Si no existe algun softare para camara devuelve un mesnsaje denegando
        Context context = CameraActivity.this;
        PackageManager packageManager = context.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
            Toast.makeText(CameraActivity.this, "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(category);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("dispatchTakePicture", "dispatchTakePictureIntent: Error creando imagen: " + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            else{
                checkFile();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //unique name, can be pretty much whatever you want
            try {
                setPic();
            }catch(Exception e){
                Log.e("CameraCaptureResult", "Fallo capturando: "+ e.getMessage());

            }
        }
        else
            checkFile();
    }

    public void setPic() {
        //carga el layout correspondiente
        setContentView(R.layout.camera_activity);
        checkFile();
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);        
        Bitmap bMap = getBitmap();
        imageView.setImageBitmap(bMap);
    }

    public void checkFile(){
       File file = new File(mCurrentPhotoPath);
        if(file.length() == 0) {
            file.delete();
            this.finish();
        }
    }
  
    public Bitmap getBitmap() {

        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, o);

            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d(RESIZE, "scale = " + scale + ", orig-width: " + o.outWidth + ",orig-height: " + o.outHeight);

            Bitmap b = null;
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeFile(mCurrentPhotoPath, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d(RESIZE, "1th scale operation dimenions - width: " + width + ",height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeFile(mCurrentPhotoPath);
            }


            Log.d(RESIZE, "bitmap size - width: " +b.getWidth() + ", height: " +
                    b.getHeight());
            saveBitmap(b);
            return b;
        } catch (Exception e) {
            Log.e(RESIZE, e.getMessage(),e);
            return null;
        }
    
    }
    
    public void saveBitmap(Bitmap bit){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(mCurrentPhotoPath);
            bit.compress(Bitmap.CompressFormat.JPEG, 80, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            Log.d(RESIZE, e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.d(RESIZE, e.getMessage());
            }
        }
    }

    @Override
    public void onDialogAccept(String categoria) {
        dispatchTakePictureIntent(categoria);
    }
}
