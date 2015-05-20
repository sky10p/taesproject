package taes.project.dressyourself.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.adapter.AdapterCategoria;
import taes.project.dressyourself.classes.Categoria;
import taes.project.dressyourself.interfaces.OnBackPressedListener;
import taes.project.dressyourself.listeners.RecyclerItemClickListener;
import taes.project.dressyourself.utils.DividerItemDecoration;

public class CategoriasFragment extends Fragment implements InsertarCategoriaDialogFragment.InsertarCategoriaDialogListener {

    private RecyclerView listaCategorias;
    private AdapterCategoria adapter;
    private RecyclerView.LayoutManager manager;
    private FragmentActivity context;
    private ActionMode actionMode;
    FloatingButtonAddFragment buttonAdd;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((DressYourSelfActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_activity_main);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_categorias,container,false);
        context = getActivity();
        ((DressYourSelfActivity)context).getSupportActionBar().setTitle(context.getString(R.string.categorias));
        ((DressYourSelfActivity) context).setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void onBack() {
                if (actionMode == null) {
                    ((DressYourSelfActivity) context).setOnBackPressedListener(null);
                    context.onBackPressed();
                } else {
                    actionMode.finish();
                }
            }
        });


        buttonAdd=new FloatingButtonAddFragment();
        getFragmentManager().beginTransaction().replace(R.id.floatingButtonFragment,buttonAdd).commit();
        ((DressYourSelfActivity)getActivity()).setFloatingButton(buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarCategoriaDialog();
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
        listaCategorias.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), listaCategorias, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nombre = null;

                if (actionMode == null) {
                    nombre = adapter.categorias.get(position).getNombre();
                    GalleryPhotosCategory gallery = new GalleryPhotosCategory();
                    Bundle bundle = new Bundle();
                    bundle.putString("categoria", nombre);
                    gallery.setArguments(bundle);
                    context.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, gallery).addToBackStack(null).commit();
                    GalleryPhotosCategory.setItemClickListener(null);
                    Log.v("onClick", "Pos: " + position + " nombre: " + nombre);
                }

            }

            @Override
            public void onItemLongClick(View view, final int position) {
                adapter.selected(position);
                if (actionMode == null) {
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
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
            actionMode=null;
            mode = null;
        }
    };
}
