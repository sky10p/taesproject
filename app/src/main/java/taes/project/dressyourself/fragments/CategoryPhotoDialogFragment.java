package taes.project.dressyourself.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.CameraActivity;
import taes.project.dressyourself.R;
import taes.project.dressyourself.classes.Categoria;


public class CategoryPhotoDialogFragment extends DialogFragment {

    private List<Categoria> categorias;

    //categoria a crear
    private EditText cat;

    CategoriaDialogListener dialogListener;


    public interface CategoriaDialogListener{
        public void onDialogAccept(String categoria);
    }


    public CategoryPhotoDialogFragment() {
    }



    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        AlertDialog.Builder builder;
        if(categorias.size() != 0) {
            builder = builderList();
        }
        else
        {
            //Toast.makeText(getActivity(), "Debes tener al menos una categoría para hacer fotos", Toast.LENGTH_SHORT).show();
            builder = builderNew();
        }
        return builder.create();
    }

    public AlertDialog.Builder builderList (){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final ArrayList<String> itemss = new ArrayList<String>(categorias.size());
        for (int i = 0; i< categorias.size(); i++){
            itemss.add(((Categoria) categorias.get(i)).getNombre());
        }
        final CharSequence[] charSequenceItems = itemss.toArray(new CharSequence[itemss.size()]);
        builder.setTitle("Seleccione la Categoría");
        builder.setSingleChoiceItems(charSequenceItems, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                dialogListener.onDialogAccept(categorias.get(item).getNombre());
                dialog.cancel();
            }
        })
             .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                     dialog.cancel();
                     getActivity().finish();
                 }
             });

        return builder;
    }

    public AlertDialog.Builder builderNew (){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_insertar_categoria, null);

        cat = (EditText) dialog.findViewById(R.id.textCategoria);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Categoria categoria = new Categoria();
                        categoria.put("nombre", cat.getText().toString());
                        categoria.put("createdBy", ParseUser.getCurrentUser());
                        categoria.saveInBackground();
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), CameraActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
        return builder;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void setDialogListener(CameraActivity l)
    {
        dialogListener = l;
    }
}
