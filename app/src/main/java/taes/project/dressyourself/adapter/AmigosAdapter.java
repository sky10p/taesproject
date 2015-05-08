package taes.project.dressyourself.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

import taes.project.dressyourself.R;

/**
 * Created by pablo on 7/05/15.
 */
public class AmigosAdapter  extends RecyclerView.Adapter<AmigosAdapter.ViewHolder>{

    private final ArrayList<ParseUser> amigos;

    public AmigosAdapter(ArrayList<ParseUser> amigos){
        this.amigos=amigos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amigo,parent,false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            amigos.get(position).fetchIfNeeded();
            holder.amigo.setText(amigos.get(position).getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView amigo;
        public ViewHolder(View itemView) {
            super(itemView);
            amigo= (TextView) itemView.findViewById(R.id.txtNameAmigo);
        }
    }
}
