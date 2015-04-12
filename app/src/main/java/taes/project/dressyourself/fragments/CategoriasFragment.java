package taes.project.dressyourself.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.DeleteCallback;
import com.parse.ParseException;

import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.AdapterCategoria;
import taes.project.dressyourself.classes.Categoria;
import taes.project.dressyourself.listeners.RecyclerItemClickListener;
import taes.project.dressyourself.utils.DividerItemDecoration;

public class CategoriasFragment extends Fragment implements InsertarCategoriaDialogFragment.InsertarCategoriaDialogListener {

    private RecyclerView listaCategorias;
    private AdapterCategoria adapter;
    private RecyclerView.LayoutManager manager;
    private FragmentActivity context;
    private Button insertarBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_categorias,container,false);
        context = getActivity();
        listaCategorias= (RecyclerView) v.findViewById(R.id.listaCategorias);
        listaCategorias.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        listaCategorias.setHasFixedSize(true);
        manager = new LinearLayoutManager(context);
        listaCategorias.setLayoutManager(manager);
        adapter=new AdapterCategoria();
        listaCategorias.setAdapter(adapter);
        listaCategorias.setItemAnimator(new DefaultItemAnimator());
        insertarBtn = (Button) v.findViewById(R.id.insertarCategoriaBtn);
        insertarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarCategoriaDialog();
            }
        });
        listaCategorias.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), listaCategorias, new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position)
            {
                if(view.isSelected()){
                    view.setBackgroundColor(getResources().getColor(R.color.background_material_light));
                    view.setSelected(false);
                }
                Log.e("onClick", "Pos: "+position);
            }
            @Override
            public void onItemLongClick(View view, final int position)
            {
                /*view.setSelected(true);
                view.setBackgroundColor(getResources().getColor(R.color.primaryLight));*/
                adapter.categorias.get(position).deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        adapter.categorias.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }));
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void insertarCategoriaDialog()
    {
        InsertarCategoriaDialogFragment dialog = new InsertarCategoriaDialogFragment();
        dialog.show(getFragmentManager(), "insertar_categoria_dialog");
        dialog.setDialogListener(this);
    }

    @Override
    public void onDialogAccept(DialogFragment dialog, Categoria c) {
        adapter.categorias.add(c);
        adapter.notifyDataSetChanged();
    }

}
