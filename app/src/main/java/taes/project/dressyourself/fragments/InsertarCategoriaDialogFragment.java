package taes.project.dressyourself.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;

import taes.project.dressyourself.R;
import taes.project.dressyourself.classes.Categoria;

/**
 * Created by isma on 6/04/15.
 */
public class InsertarCategoriaDialogFragment extends DialogFragment {

    private EditText cat;

    public interface InsertarCategoriaDialogListener{
        public void onDialogAccept(DialogFragment dialog, Categoria categoria);
    }

    InsertarCategoriaDialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
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
                        dialogListener.onDialogAccept(InsertarCategoriaDialogFragment.this, categoria);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        InsertarCategoriaDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setDialogListener(InsertarCategoriaDialogListener l)
    {
        dialogListener = l;
    }
}

