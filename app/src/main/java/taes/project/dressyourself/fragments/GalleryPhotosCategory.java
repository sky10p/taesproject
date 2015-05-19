package taes.project.dressyourself.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.adapter.AdapterGaleria;
import taes.project.dressyourself.classes.Foto;
import taes.project.dressyourself.listeners.RecyclerItemClickListener;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class GalleryPhotosCategory extends  android.support.v4.app.Fragment {




    private static String TAG = "galleryPhotosCategory";
    public String mCurrentCategoryPath;
    AsyncTaskLoadFiles myAsyncTaskLoadFiles;

    public RecyclerView listarFotoPorCategoria;
    private RecyclerView.LayoutManager mLayoutManagerFotoCPorCategoria;
    AdapterGaleria mAdapter;
    ArrayList<Foto> mPhotoAlbum;
    private FloatingButtonCameraFragment fragmentCamera;

    public static RecyclerItemClickListener.OnItemClickListener clickListener;

    public static void setItemClickListener(RecyclerItemClickListener.OnItemClickListener listener)
    {
        clickListener = listener;
    }

    public GalleryPhotosCategory() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Creado el view");
        String categoria=this.getArguments().getString("categoria");
        mCurrentCategoryPath = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES +"/"+ParseUser.getCurrentUser().getUsername()+"/"+this.getArguments().getString("categoria")).getAbsolutePath();
        View v=inflater.inflate(R.layout.recycler_photo_view, container, false);

        fragmentCamera=new FloatingButtonCameraFragment();
        getFragmentManager().beginTransaction().replace(R.id.floatingButtonFragment,fragmentCamera).commit();
        ((DressYourSelfActivity)getActivity()).setFloatingButton(fragmentCamera);

        ((DressYourSelfActivity)getActivity()).getSupportActionBar().setTitle(categoria);
        // Prepare the data source:
        mPhotoAlbum = listaFotosPorCategoria();
        // Instantiate the adapter and pass in its data source:
        Log.d(TAG, "Cargado disenno");
        mAdapter = new AdapterGaleria(mPhotoAlbum);
        Log.d(TAG, "Despues del gridview, count adapter: " + mAdapter.getItemCount());
        // Get our RecyclerView layout:
        listarFotoPorCategoria = (RecyclerView) v.findViewById(R.id.recyclerPhotoView);
        Log.d(TAG, "Despues del gridview");
        // Plug the adapter into the RecyclerView:
        listarFotoPorCategoria.setAdapter(mAdapter);
        mLayoutManagerFotoCPorCategoria = new GridLayoutManager(getActivity(),3);
        listarFotoPorCategoria.setLayoutManager(mLayoutManagerFotoCPorCategoria);
        if(clickListener != null)
        {
            listarFotoPorCategoria.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), listarFotoPorCategoria, clickListener));
        }
        return v;
    }

    public class AsyncTaskLoadFiles extends AsyncTask<Void, String, Void> {

        File targetDirector;
        AdapterGaleria myTaskAdapter;
        Foto foto;

        public AsyncTaskLoadFiles(AdapterGaleria adapter) {
            myTaskAdapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            String ExternalStorageDirectoryPath = Environment
                    .getExternalStorageDirectory().getAbsolutePath();

            String targetPath = mCurrentCategoryPath;
            targetDirector = new File(targetPath);
            myTaskAdapter.clear();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            File[] files = targetDirector.listFiles();
            for (File file : files) {
                publishProgress(file.getAbsolutePath());
                if (isCancelled()) break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            foto = new Foto(decodeSampledBitmapFromUri(values[0],220,220), values[0]);
            myTaskAdapter.add(foto);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            myTaskAdapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

    }


    public ArrayList<Foto> listaFotosPorCategoria(){
        Log.d(TAG, "listaFotosPorCategoria");
        ArrayList<Foto> fotos = new ArrayList<Foto>();
        File imageDir = new File(mCurrentCategoryPath);
        File [] mediaFiles;
        if((imageDir.exists())) {
            mediaFiles = imageDir.listFiles();
            Log.d("Length of images", "" + mediaFiles.length);
            for(File file : mediaFiles)
            {
                Log.d(TAG, "Foto nombre: " + file.getAbsolutePath());
                fotos.add(new Foto(convertToBitmap(file), readFileName(file)));
                //Log.d(TAG + "bmpArray Size", ""+bmpArray.size());
                //Log.d(TAG, "call to convertToBitmap");
            }//for
        }
        else
        {
            Log.d(TAG, "No existe el file:" + mCurrentCategoryPath);
        }
        Log.d(TAG, "casi finalizando data: "+ fotos.size());
        return fotos;
    }

    public static Bitmap convertToBitmap(File file)
    {

        Bitmap bmp = null;
        try
        {
            bmp = decodeSampledBitmapFromUri(file.getAbsolutePath(),220,220);//BitmapFactory.decodeStream(url.openStream());
            //bmp.recycle();
        }catch(Exception e)
        {
            Log.e(TAG, "Exception: " + e.toString());
        }//catch
        return bmp;
    }//convertToBitmap

    public static String readFileName(File file){
        String name = file.getName();
        return name;
    }//readFileName


    public static Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
                                             int reqHeight) {

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public static int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height
                        / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }


}
