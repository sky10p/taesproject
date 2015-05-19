package taes.project.dressyourself.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by isma on 17/05/15.
 */
public class SelectPhotoDialogFragment extends DialogFragment {
    String categoria;




    public Dialog onCreaDialog(Bundle savedInstance)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.create();
    }
}
