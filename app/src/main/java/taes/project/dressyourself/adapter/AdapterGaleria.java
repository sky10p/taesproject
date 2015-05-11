package taes.project.dressyourself.adapter;

import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import taes.project.dressyourself.R;
import taes.project.dressyourself.classes.Foto;

/**
 * Created by Salomon on 08/05/2015.
 */
public class AdapterGaleria extends RecyclerView.Adapter<AdapterGaleria.FotoViewHolder>{

    ArrayList<Foto> fotos;

    public AdapterGaleria(ArrayList<Foto> fotos){
        this.fotos = fotos;
    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    @Override
    public FotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_card_view, viewGroup, false);
        FotoViewHolder pvh = new FotoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FotoViewHolder personViewHolder, int i) {

        personViewHolder.fotoPhoto.setImageBitmap(fotos.get(i).getImage());

    }

  /*  @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }*/

    public void clear() {
        fotos.clear();
    }

    public void add(Foto foto) {
        fotos.add(foto);
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {


        ImageView fotoPhoto;

        FotoViewHolder(View itemView) {
            super(itemView);


            fotoPhoto = (ImageView)itemView.findViewById(R.id.imagePhotoView);
        }
    }
}
