package taes.project.dressyourself.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import taes.project.dressyourself.R;

/**
 * Created by pablo on 7/05/15.
 */
public class AmigosFragment extends Fragment {


    private RecyclerView lstAmigos;
    private Button btnInsertarAmigo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_amigos,container,false);
        lstAmigos = (RecyclerView) v.findViewById(R.id.lstAmigos);
        lstAmigos.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnInsertarAmigo = (Button) v.findViewById(R.id.btnInsertarAmigo);



        return v;


    }
}
