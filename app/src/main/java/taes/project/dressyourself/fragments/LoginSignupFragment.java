package taes.project.dressyourself.fragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import taes.project.dressyourself.R;

/**
 * Created by isma on 2/04/15.
 */
public class LoginSignupFragment extends Fragment {

    private Button ingresarBtn;
    private Button unirseBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_login, container, false);

        ingresarBtn = (Button) rootView.findViewById(R.id.ingresarBtn);
        ingresarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.slide_login_content, frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        unirseBtn = (Button) rootView.findViewById(R.id.unirseBtn);
        unirseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new SignupFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.slide_login_content, frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }
}
