package taes.project.dressyourself.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import taes.project.dressyourself.R;

/**
 * Created by pablo on 24/02/15.
 */
public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewHolder> {


    private ArrayList<Categoria> categoria;
    private class Categoria{
        public String nombre;


        public Categoria(String nombre){
            this.nombre=nombre;
        }
    }

    public AdapterCategoria(){
        categoria = new ArrayList<>();
        categoria.add(new Categoria("categoria1"));
        categoria.add(new Categoria("categoria2"));
        categoria.add(new Categoria("categoria3"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_categoria,parent,false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.nombre.setText(categoria.get(position).nombre);

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
        return categoria.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombre;
        public ViewHolder(View itemView) {
            super(itemView);

            nombre= (TextView) itemView.findViewById(R.id.txtNombreCategoria);
        }
    }
}
