package taes.project.dressyourself.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;

/**
 * Created by pablo on 8/05/15.
 */
public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    ArrayList<ParseUser> usuarios;
    ArrayList<ParseUser> amigos;

    public UsuariosAdapter(List<ParseUser> list) {
        this.usuarios= (ArrayList<ParseUser>) list;
        ParseUser user=ParseUser.getCurrentUser();
        if(user.has("amigos")){
            this.amigos= (ArrayList<ParseUser>) user.get("amigos");
        }else {
            this.amigos=new ArrayList<>();
        }

    }

    public void setUsuarios(List<ParseUser> list){
        usuarios= (ArrayList<ParseUser>) list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_user_found,parent,false);

        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.userName.setText(usuarios.get(position).getUsername());

        if(containsUser(amigos, usuarios.get(position))){
            holder.btnSeguir.setText("Siguiendo");
            holder.btnSeguir.setEnabled(false);
        }else {
            holder.btnSeguir.setText("Seguir");
            holder.btnSeguir.setEnabled(true);
        }



        holder.btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user=ParseUser.getCurrentUser();
                ArrayList<ParseUser> amigos=new ArrayList<ParseUser>();
                if(user.has("amigos")){
                   amigos = (ArrayList<ParseUser>) user.get("amigos");

                }
                amigos.add(usuarios.get(position));


                user.put("amigos", amigos);
                user.saveInBackground();
                holder.btnSeguir.setText("Siguiendo");
                holder.btnSeguir.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public Button btnSeguir;

        public ViewHolder(View itemView) {
            super(itemView);
            userName= (TextView) itemView.findViewById(R.id.txtUserName);
            btnSeguir= (Button) itemView.findViewById(R.id.btnSeguir);
        }
    }
    private boolean containsUser(List<ParseUser> list, ParseUser user) {
        for (ParseUser parseUser : list) {
            if (parseUser.hasSameId(user)) return true;
        }
        return false;

    }
}
