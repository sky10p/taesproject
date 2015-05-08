package taes.project.dressyourself.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.SearchAmigosActivity;
import taes.project.dressyourself.adapter.AmigosAdapter;

/**
 * Created by pablo on 7/05/15.
 */
public class AmigosFragment extends Fragment {


    private RecyclerView lstAmigos;
    private Button btnInsertarAmigo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_amigos, container, false);
        lstAmigos = (RecyclerView) v.findViewById(R.id.lstAmigos);
        lstAmigos.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnInsertarAmigo = (Button) v.findViewById(R.id.btnInsertarAmigo);

        ParseUser user=ParseUser.getCurrentUser();

        user.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(parseObject.has("amigos")){
                    lstAmigos.setAdapter(new AmigosAdapter((ArrayList<ParseUser>) parseObject.get("amigos")));
                }

            }
        });

        btnInsertarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchAmigosActivity.class));
            }
        });




        return v;


    }
}
