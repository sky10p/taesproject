package taes.project.dressyourself.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import taes.project.dressyourself.DressYourSelfActivity;
import taes.project.dressyourself.LoginActivity;
import taes.project.dressyourself.R;


public class SignupFragment extends Fragment {
    String username;
    String password;
    EditText usernameText;
    EditText passwordText;
    Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v=inflater.inflate(R.layout.fragment_signup,container,false);

        return v;
    }
}
