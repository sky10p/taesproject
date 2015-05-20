package taes.project.dressyourself.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.CameraActivity;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.adapter.AdapterCategoria;
import taes.project.dressyourself.classes.Categoria;
import taes.project.dressyourself.listeners.RecyclerItemClickListener;

/**
 * Created by isma on 17/05/15.
 */
public class NuevaPublicacionFragment extends Fragment implements CategoryPhotoDialogFragment.CategoriaDialogListener {

    private List<Categoria> categorias;
    private String categoria;
    private Button addPrenda;
    private List<Bitmap> fotos;
    private ImageView foto;
    private EditText titulo;
    private EditText texto;
    private Button publicarBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_nueva_plubicacion,container,false);
        addPrenda = (Button) v.findViewById(R.id.addPrenda);
        addPrenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPrenda();
            }
        });
        foto = (ImageView) v.findViewById(R.id.foto);
        titulo = (EditText) v.findViewById(R.id.titulo);
        texto = (EditText) v.findViewById(R.id.texto);
        publicarBtn = (Button) v.findViewById(R.id.publicarBtn);
        publicarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject publicacion = new ParseObject("Publicacion");
                publicacion.put("titulo", titulo.getText().toString());
                publicacion.put("texto", texto.getText().toString());
                if(fotos != null){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    fotos.get(0).compress(Bitmap.CompressFormat.PNG, 100, stream);
                    ParseFile file = new ParseFile("foto.jpg", stream.toByteArray());
                    publicacion.put("foto", file);
                }
                publicacion.put("createdBy", ParseUser.getCurrentUser());
                publicacion.saveInBackground();
                getFragmentManager().popBackStack();
            }
        });
        if(fotos == null) fotos = new ArrayList();
        else foto.setImageBitmap(fotos.get(0));
        for(Bitmap b : fotos)
        {
            Log.e("E", "Foto: "+ b.toString());
        }
        return v;
    }

    public void addPrenda() {
        categorias = Categoria.getAllByUser(ParseUser.getCurrentUser(), new AdapterCategoria.AdapterCategoriaCallback() {
            @Override
            public void onDataLoaded() {
                CategoryPhotoDialogFragment dialog = new CategoryPhotoDialogFragment();
                dialog.setCategorias(categorias);
                dialog.setCancelable(false);
                dialog.setDialogListener(NuevaPublicacionFragment.this);
                dialog.show(getActivity().getFragmentManager(), "Categorias dialog");
            }
        });
    }

    @Override
    public void onDialogAccept(String categoria) {
        Log.e("E", "Categoria seleccionada " + categoria);

        final GalleryPhotosCategory gallery = new GalleryPhotosCategory();
        Bundle bundle = new Bundle();
        bundle.putString("categoria", categoria);
        gallery.setArguments(bundle);
        RecyclerItemClickListener.OnItemClickListener listener = new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Log.e("E", "pos: " + position+"- " + gallery.mPhotoAlbum.get(position).getTitle());
                fotos.add(gallery.mPhotoAlbum.get(position).getImage());
                gallery.getFragmentManager().popBackStack();
            }
            @Override
            public void onItemLongClick(View view, final int position)
            {

            }
        };
        GalleryPhotosCategory.setItemClickListener(listener);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, gallery).addToBackStack(null).commit();

    }



}
