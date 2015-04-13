package taes.project.dressyourself.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.DressYourSelfActivity;
import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.AdapterCategoria;
import taes.project.dressyourself.classes.Categoria;
import taes.project.dressyourself.interfaces.OnBackPressedListener;
import taes.project.dressyourself.utils.DividerItemDecoration;
import taes.project.dressyourself.listeners.RecyclerItemClickListener;

public class CategoriasFragment extends Fragment implements InsertarCategoriaDialogFragment.InsertarCategoriaDialogListener {

    private RecyclerView listaCategorias;
    private AdapterCategoria adapter;
    private RecyclerView.LayoutManager manager;
    private FragmentActivity context;
    private Button insertarBtn;
    private ActionMode actionMode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_categorias,container,false);
        context = getActivity();
        ((DressYourSelfActivity) context).setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void onBack() {
                if(actionMode==null){
                    ((DressYourSelfActivity) context).setOnBackPressedListener(null);
                }else{
                    actionMode.finish();
                }
            }
        });
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
                if(actionMode!=null){
                    adapter.selected(position);
                }
                Log.e("onClick", "Pos: "+position);
            }
            @Override
            public void onItemLongClick(View view, final int position)
            {
                adapter.selected(position);
                if(actionMode==null)
                {
                    ActionBarActivity activity = (ActionBarActivity) getActivity();
                    actionMode = activity.startSupportActionMode(mActionModeCallback);
                }
            }
        }));
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        if(actionMode!=null) actionMode.finish();
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

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_options, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_delete:
                    final List<Categoria> selectedItems = adapter.getSelectedItemsAsCategoria();
                    ParseObject.deleteAllInBackground(selectedItems, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            adapter.categorias.removeAll(selectedItems);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    actionMode.finish();
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelections();
            actionMode = null;
            mode = null;
        }
    };
}
