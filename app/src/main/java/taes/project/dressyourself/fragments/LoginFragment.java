package taes.project.dressyourself.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import taes.project.dressyourself.DressYourSelfActivity;
import taes.project.dressyourself.R;


/**
 * Created by pablo on 9/03/15.
 */
public class LoginFragment extends Fragment {
    private String username;
    private String password;
    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private TextView error;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v=inflater.inflate(R.layout.fragment_login,container,false);

        loginButton = (Button) v.findViewById(R.id.loginbtn);

        // obtener campos desde el layout para el login
        usernameText = (EditText) v.findViewById(R.id.textUsername);
        passwordText = (EditText) v.findViewById(R.id.textPassword);
        error = (TextView) v.findViewById(R.id.textError);

        // login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                // obtener los datos de los campos
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                error.setVisibility(View.INVISIBLE);
                // enviar los datos a parse para comprobar si son correctos
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            // autenticación correcto
                            if(parseUser.getBoolean("emailVerified")){
                                // email verificado
                                Intent intent = new Intent(getActivity(), DressYourSelfActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                // falta verificar email
                                error.setText(R.string.email_not_verified);
                                error.setVisibility(View.VISIBLE);
                                loginButton.setEnabled(true);
                            }
                        }else{
                            error.setText(R.string.wrong_user_password);
                            error.setVisibility(View.VISIBLE);
                            loginButton.setEnabled(true);
                        }
                    }
                });
            }
        });
        return v;
    }
}
