package taes.project.dressyourself.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.CameraActivity;
import taes.project.dressyourself.activities.DressYourSelfActivity;

/**
 * Created by Pablo on 20/05/2015.
 */
public class FloatingButtonCameraFragment extends  FloatingButtonFragment {

    FloatingActionButton button;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_floating_camera_button,container,false);
        button= (FloatingActionButton) v.findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                Bundle bundle=new Bundle();
                String categoria= ((DressYourSelfActivity)getActivity()).getSupportActionBar().getTitle().toString();
                bundle.putString("categoria",categoria);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void collapse() {

    }
}
