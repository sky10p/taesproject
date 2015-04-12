package taes.project.dressyourself;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import taes.project.dressyourself.adapter.AdapterCategoria;
import taes.project.dressyourself.classes.Categoria;


public class CameraActivity extends ActionBarActivity {

    //Entero que especifica la cantidad de fotos a tirar
    static final int REQUEST_IMAGE_CAPTURE = 0;
    //Camino en el cual esta guardada la foto
    private String mCurrentPhotoPath;
    
    private String RESIZE = "resize";

    //categoria a crear
    private EditText cat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPhoto();
        setContentView(R.layout.camera_activity);

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


    @Override
    protected Dialog onCreateDialog(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(id != 0) {

            List<Categoria> list = new AdapterCategoria().categorias;
            final ArrayList<String> itemss = new ArrayList<String>(list.size());
            for (int i = 0; i< list.size(); i++){
                itemss.add(((Categoria) list.get(i)).getNombre());
            }
            final CharSequence[] charSequenceItems = itemss.toArray(new CharSequence[itemss.size()]);
                builder.setTitle("Seleccione la Categoría");
            builder.setSingleChoiceItems(charSequenceItems, -1, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int item) {
                    dispatchTakePictureIntent();
                    dialog.cancel();
                }
            });
        }
        else
        {
            LayoutInflater inflater = this.getLayoutInflater();
            View dialog = inflater.inflate(R.layout.dialog_insertar_categoria, null);

            cat = (EditText) dialog.findViewById(R.id.textCategoria);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(dialog)
                    // Add action buttons
                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Categoria categoria = new Categoria();
                            categoria.put("nombre", cat.getText().toString());
                            categoria.put("createdBy", ParseUser.getCurrentUser());
                            categoria.saveInBackground();
                            CameraActivity.this.finish();
                            Intent intent = new Intent(CameraActivity.this, CameraActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            CameraActivity.this.finish();
                        }
                    });
        }
        return builder.create();
    }
    private class CategorySize extends AsyncTask<Void, Void, List<Categoria>> {
        protected synchronized List<Categoria> doInBackground(Void... params) {
            AdapterCategoria adapter = new AdapterCategoria();
            adapter.notifyDataSetChanged();
            List<Categoria> lista = adapter.categorias;

            Log.v("TEST", Integer.toString(lista.size())+" doinbackground");
            return lista;
        }
        protected void onPostExecute(List<Categoria> result) {
            Log.v("TEST", Integer.toString(result.size()));
            showDialog(result.size());
        }
    }
    private class Categorys extends AsyncTask<URL, Integer, List> {
        protected List<Categoria> doInBackground(URL... urls) {
            List <Categoria> lista = new AdapterCategoria().categorias;
            return lista;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(List<Categoria> result) {
            //showDialog(result);
        }
    }



    public void addPhoto() {

        /*
        // Funcionalidad correcta
        showDialog(new AdapterCategoria().getItemCount());
        */

        // Hasta correccion codigo con datos de prueba
        //showDialog(new AdapterCategoria().getItemCount());
        //showDialog(3);
        new CategorySize().execute();
    }





    //Crea una imagen unica en memoria destino  y la devuelve
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES +"/"+ParseUser.getCurrentUser().getUsername());
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
    private void dispatchTakePictureIntent() {
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
                photoFile = createImageFile();
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

}
