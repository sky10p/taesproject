package taes.project.dressyourself.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import taes.project.dressyourself.R;

/**
 * Created by isma on 1/04/15.
 */
public class SlidePageFragment extends Fragment
{

    private ImageView imagen;
    private TextView texto;
    private int imagen_id;
    private String descripcion;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagen_id=getArguments().getInt("imagen_id");
        descripcion =getArguments().getString("descripcion");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_page, container, false);

        imagen = (ImageView) rootView.findViewById(R.id.slideImg);
        texto = (TextView) rootView.findViewById(R.id.txtSliceDescription);

        imagen.setImageResource(imagen_id);
        texto.setText(descripcion);
        return rootView;
    }

}
