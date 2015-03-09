package taes.project.dressyourself.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import taes.project.dressyourself.R;

/**
 * Created by pablo on 6/03/15.
 */
public class AdapterDrawerNavigation extends RecyclerView.Adapter<AdapterDrawerNavigation.ViewHolder> {

    String[] listMenu;
    TypedArray iconsMenu;

    Context context;

    public AdapterDrawerNavigation(Context context) {
        this.context=context;
        this.listMenu = context.getResources().getStringArray(R.array.lst_left_drawer_names);
        iconsMenu=context.getResources().obtainTypedArray(R.array.lst_left_drawer_img);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        Boolean divider;

        if(viewType==1){
           v=new LinearLayout(context);
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
            v.setBackgroundColor(Color.GRAY);
            divider=true;

        }else {
           v= LayoutInflater.from(context).inflate(R.layout.item_list_drawer,parent,false);
            divider=false;
        }

        ViewHolder vh=new ViewHolder(v,divider);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if(listMenu[position].equals("divider")){
            return 1;
        }

        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if(getItemViewType(position)==0){
            holder.texto.setText(listMenu[position]);

            holder.imagen.setImageResource(iconsMenu.getResourceId(position,android.R.drawable.ic_menu_preferences));
        }


    }

    @Override
    public int getItemCount() {
        return listMenu.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView texto;

        public ViewHolder(View itemView,boolean divider) {
            super(itemView);
            if(divider==false){
                imagen= (ImageView) itemView.findViewById(R.id.imgIcon);
                texto= (TextView) itemView.findViewById(R.id.txtTextMenu);
            }



        }
    }
}
