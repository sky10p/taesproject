package taes.project.dressyourself.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import taes.project.dressyourself.R;
import taes.project.dressyourself.listeners.onDrawableLoaded;
import taes.project.dressyourself.utils.DrawableUtils;

/**
 * Created by pablo on 24/02/15.
 */
public class AdapterConjunto extends RecyclerView.Adapter<AdapterConjunto.ViewHolder> {


    private ArrayList<ConjuntoRopa> conjunto;
    private Context context;

    public void setConjuntos(ArrayList<ConjuntoRopa> conjuntos) {
        this.conjunto = conjuntos;
        notifyDataSetChanged();
    }

    public static class ConjuntoRopa{
        public String titulo;
        public String descripcion;
        public String url;


        public ConjuntoRopa(String titulo, String descripcion, String url){
            this.titulo=titulo;
            this.descripcion=descripcion;
            this.url=url;
        }


    }

    public AdapterConjunto(Context c){
        conjunto = new ArrayList<>();
        context=c;

        /*String descripcion="Esto es una prueba de descripción";
        conjunto.add(new ConjuntoRopa("conjunto1",descripcion, context.getResources().getDrawable(R.drawable.conjunto1)));
        conjunto.add(new ConjuntoRopa("conjunto2",descripcion,context.getResources().getDrawable(R.drawable.conjunto2)));
        conjunto.add(new ConjuntoRopa("conjunto3", descripcion,context.getResources().getDrawable(R.drawable.conjunto3)));*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_publicaciones_ropa,parent,false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DrawableUtils.getDrawableFromUrl(conjunto.get(position).url, new onDrawableLoaded() {
            @Override
            public void someError(IOException e) {

            }

            @Override
            public void done(Drawable drawable) {
                holder.imagen.setImageDrawable(drawable);
                BitmapDrawable bitMapDrawable= (BitmapDrawable) drawable;
                Palette.from(bitMapDrawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch != null) {
                            holder.titulo.setBackgroundColor(swatch.getRgb());
                            holder.titulo.setTextColor(swatch.getTitleTextColor());
                        }
                    }
                });
            }
        });

        holder.titulo.setText(conjunto.get(position).titulo);
        holder.descripcion.setText(conjunto.get(position).descripcion);





    }

    @Override
    public int getItemCount() {
        return conjunto.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView titulo;
        public TextView descripcion;
        public ViewHolder(View itemView) {
            super(itemView);

            imagen= (ImageView) itemView.findViewById(R.id.imgConjunto);
            titulo= (TextView) itemView.findViewById(R.id.txtTituloConjunto);
            descripcion= (TextView) itemView.findViewById(R.id.txtDescripcion);
        }
    }
}
