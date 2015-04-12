package taes.project.dressyourself.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.classes.Categoria;

/**
 * Created by pablo on 24/02/15.
 */
public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewHolder> {

    public interface AdapterCategoriaCallback {
        public void onDataLoaded();
    }

    public List<Categoria> categorias;

    public AdapterCategoria(){
        this.categorias = new ArrayList<>();
        this.categorias = Categoria.getAllByUser(ParseUser.getCurrentUser(), new AdapterCategoriaCallback(){
            @Override
            public void onDataLoaded(){
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.nombre.setText(categorias.get(position).getNombre());

        /*AsyncTask<Bitmap, Void, Palette> palette=Palette.generateAsync(drawableBitMap.getBitmap(), new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch=palette.getVibrantSwatch();
                if(swatch!=null){
                    holder.titulo.setBackgroundColor(swatch.getRgb());
                    holder.titulo.setTextColor(swatch.getTitleTextColor());
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtNombreCategoria);
        }
    }

}
