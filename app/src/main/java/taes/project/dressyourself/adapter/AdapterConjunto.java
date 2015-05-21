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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import taes.project.dressyourself.R;
import taes.project.dressyourself.listeners.onDrawableLoaded;
import taes.project.dressyourself.transformers.TransformationVotos;
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
        public int votos;



        public ConjuntoRopa(String titulo, String descripcion, String url){
            this.titulo=titulo;
            this.descripcion=descripcion;
            this.url=url;
            this.votos=0;
        }

        public ConjuntoRopa(String titulo, String descripcion, String url, int votos){
            this.titulo=titulo;
            this.descripcion=descripcion;
            this.url=url;
            this.votos=votos;
        }

        public String getVotos(){
            return String.valueOf(votos);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Picasso.with(context).load(conjunto.get(position).url).into(holder.imagen, new Callback() {
            @Override
            public void onSuccess() {
                BitmapDrawable bitMapDrawable= (BitmapDrawable) holder.imagen.getDrawable();
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

            @Override
            public void onError() {

            }
        });

        //Picasso.with(context).load(R.drawable.me_gusta).transform(new TransformationVotos(context,"1")).into(holder.btnMeGusta);



        Picasso.with(context).load(R.drawable.me_gusta).resize(300, 300).transform(new TransformationVotos(context, conjunto.get(position).getVotos())).into(holder.btnMeGusta);

        holder.btnMeGusta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.votado){
                    conjunto.get(position).votos++;
                    holder.votado=true;
                    Picasso.with(context).load(R.drawable.me_gusta).resize(300, 300).transform(new TransformationVotos(context, conjunto.get(position).getVotos())).into(holder.btnMeGusta);

                }else {
                    Toast.makeText(context,"Ya ha votado",Toast.LENGTH_LONG).show();
                }
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
        public ImageButton btnMeGusta;

        public Boolean votado;



        public ViewHolder(View itemView) {
            super(itemView);

            imagen= (ImageView) itemView.findViewById(R.id.imgConjunto);
            titulo= (TextView) itemView.findViewById(R.id.txtTituloConjunto);
            descripcion= (TextView) itemView.findViewById(R.id.txtDescripcion);
            btnMeGusta= (ImageButton) itemView.findViewById(R.id.imgMeGusta);

            votado=false;

        }


    }
}
