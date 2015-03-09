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

import taes.project.dressyourself.DressYourSelfActivity;
import taes.project.dressyourself.R;


/**
 * Created by pablo on 9/03/15.
 */
public class LoginFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v=inflater.inflate(R.layout.fragment_login,container,false);

        Button btnComplete= (Button) v.findViewById(R.id.btnComplete);
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
        }
        return v;
    }
}
