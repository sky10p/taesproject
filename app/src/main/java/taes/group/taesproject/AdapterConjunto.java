package taes.group.taesproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pablo on 24/02/15.
 */
public class AdapterConjunto extends RecyclerView.Adapter<AdapterConjunto.ViewHolder> {


    private ArrayList<ConjuntoRopa> conjunto;
    private class ConjuntoRopa{
        public String titulo;
        public int imagen;


        public ConjuntoRopa(String titulo, int imagen){
            this.titulo=titulo;
            this.imagen=imagen;
        }
    }

    public AdapterConjunto(){
        conjunto = new ArrayList<>();
        conjunto.add(new ConjuntoRopa("conjunto1",R.drawable.conjunto1));
        conjunto.add(new ConjuntoRopa("conjunto2",R.drawable.conjunto2));
        conjunto.add(new ConjuntoRopa("conjunto3",R.drawable.conjunto3));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_conjunto_ropa,parent,false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imagen.setImageDrawable(holder.imagen.getResources().getDrawable(conjunto.get(position).imagen));
        holder.titulo.setText(conjunto.get(position).titulo);
    }

    @Override
    public int getItemCount() {
        return conjunto.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView titulo;
        public ViewHolder(View itemView) {
            super(itemView);

            imagen= (ImageView) itemView.findViewById(R.id.imgConjunto);
            titulo= (TextView) itemView.findViewById(R.id.txtTituloConjunto);
        }
    }
}
