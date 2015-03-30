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
import android.widget.Toast;

import taes.project.dressyourself.DressYourSelfActivity;
import taes.project.dressyourself.R;


/**
 * Created by pablo on 9/03/15.
 */
public class LoginFragment extends Fragment {
    String username;
    String password;
    EditText usernameText;
    EditText passwordText;
    Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v=inflater.inflate(R.layout.fragment_login,container,false);

        loginButton = (Button) v.findViewById(R.id.loginbtn);

        // obtener campos desde el layout para el login
        usernameText = (EditText) v.findViewById(R.id.txtUserName);
        passwordText = (EditText) v.findViewById(R.id.txtPassword);

        // login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                // obtener los datos de los campos
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                // enviar los datos a parse para comprobar si son correctos
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            // la autenticación ha tenido éxito
                            Intent intent = new Intent(getActivity(), DressYourSelfActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(
                                    getActivity(),
                                    "Usuario y/o contraseña incorrectos",
                                    Toast.LENGTH_LONG).show();
                            loginButton.setEnabled(true);
                        }
                    }
                });
            }
        });

        /*
        Bundle bundle=getArguments();
        if(bundle.getString("type").equals(getString(R.string.login))){

            btnComplete.setText(getString(R.string.start_session));
            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), DressYourSelfActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }else{
            btnComplete.setText(getString(R.string.complete_register));
        }*/
        return v;
    }
}
