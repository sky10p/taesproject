package taes.project.dressyourself.fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import taes.project.dressyourself.R;


public class SignupFragment extends Fragment {
    private EditText usernameText;
    private EditText passwordText;
    private EditText emailText;
    private Button signupBtn;
    private TextView error;
    private TextView success;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_signup,container,false);
        usernameText = (EditText) v.findViewById(R.id.textUsername);
        passwordText = (EditText) v.findViewById(R.id.textPassword);
        emailText = (EditText) v.findViewById(R.id.textEmail);
        signupBtn = (Button) v.findViewById(R.id.signupbtn);
        error = (TextView) v.findViewById(R.id.textError);
        success = (TextView) v.findViewById(R.id.textSignupSuccess);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupBtn.setEnabled(false);
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String email = emailText.getText().toString();
                error.setText("");
                success.setVisibility(View.INVISIBLE);
                if (username.equals("") || password.equals("") || email.equals("")) {
                    error.setText("Debe rellenar todos los campos");
                } else {
                    if (isEmailValid(email)) {
                        ParseUser user = new ParseUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    success.setVisibility(View.VISIBLE);
                                } else {
                                    switch (e.getCode()) {
                                        case ParseException.EMAIL_TAKEN:
                                            error.setText("El email introducido ya existe");
                                            break;
                                        case ParseException.USERNAME_TAKEN:
                                            error.setText("El usuario introducido ya existe");
                                            break;
                                        default:
                                            error.setText("No se ha podido completar el registro");
                                            break;
                                    }
                                }
                            }
                        });
                    } else {
                        error.setText("El email introducido no es válido");
                    }
                }
                signupBtn.setEnabled(true);
            }
        });

        return v;
    }





    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
