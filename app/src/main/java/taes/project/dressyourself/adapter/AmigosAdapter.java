package taes.project.dressyourself.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

import taes.project.dressyourself.R;
import taes.project.dressyourself.utils.ColorUtils;
import taes.project.dressyourself.utils.TextDrawable;
import taes.project.dressyourself.utils.TextUtils;

/**
 * Created by pablo on 7/05/15.
 */
public class AmigosAdapter  extends RecyclerView.Adapter<AmigosAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ParseUser> amigos;

    public AmigosAdapter(Context context){
        amigos=new ArrayList<ParseUser>();
        this.context=context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amigo,parent,false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            amigos.get(position).fetchIfNeeded();
            holder.amigo.setText(amigos.get(position).getUsername());
            TextDrawable imgAmigo=TextDrawable.builder().buildRound(TextUtils.getLettersFromUser(amigos.get(position).getUsername()), ColorUtils.ColorGenerator.MATERIAL.getColor(amigos.get(position)));
            holder.imgAmigo.setImageDrawable(imgAmigo);
            holder.btnDejarDeSeguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.borrar_amigo))
                            .setMessage(context.getString(R.string.sure_delete_friend))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    String userName=amigos.get(position).getUsername();
                                    amigos.remove(amigos.get(position));
                                    notifyItemRemoved(position);
                                    ParseUser user=ParseUser.getCurrentUser();
                                    user.put("amigos",amigos);
                                    user.saveInBackground();
                                    Toast.makeText(context,"Dejando de seguir a "+userName,Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    public ArrayList<ParseUser> getAmigos() {
        return amigos;
    }

    public void setAmigos(ArrayList<ParseUser> amigos) {
        this.amigos = amigos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView amigo;
        public ImageView imgAmigo;
        public Button btnDejarDeSeguir;
        public ViewHolder(View itemView) {
            super(itemView);
            amigo= (TextView) itemView.findViewById(R.id.txtNameAmigo);
            imgAmigo= (ImageView) itemView.findViewById(R.id.imgAmigo);
            btnDejarDeSeguir= (Button) itemView.findViewById(R.id.btnSeguir);
        }
    }
}
