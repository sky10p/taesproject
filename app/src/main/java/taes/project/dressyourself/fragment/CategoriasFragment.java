package taes.project.dressyourself.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.AdapterCategoria;


public class CategoriasFragment extends Fragment {

    private RecyclerView listaCategorias;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private Context contexto;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_categorias,container,false);
        listaCategorias= (RecyclerView) v.findViewById(R.id.lstConjuntos);
        manager=new LinearLayoutManager(getActivity());
        listaCategorias.setLayoutManager(manager);
        adapter=new AdapterCategoria();
        listaCategorias.setAdapter(adapter);
        return v;
    }
}