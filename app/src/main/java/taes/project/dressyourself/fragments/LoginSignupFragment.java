package taes.project.dressyourself.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.ScreenSlideActivity;

/**
 * Created by isma on 2/04/15.
 */
public class LoginSignupFragment extends Fragment {

    private Button ingresarBtn;
    private Button unirseBtn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ScreenSlideActivity activity= (ScreenSlideActivity) getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_login, container, false);

        ingresarBtn = (Button) rootView.findViewById(R.id.ingresarBtn);
        ingresarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.cambiarPagina(activity.LOGIN);
            }
        });
        unirseBtn = (Button) rootView.findViewById(R.id.unirseBtn);
        unirseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getFragmentManager().beginTransaction().replace(R.id.slide_login_content,new SignupFragment()).addToBackStack(null).commit();
            }
        });
        return rootView;
    }
}
