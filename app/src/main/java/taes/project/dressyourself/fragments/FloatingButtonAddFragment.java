package taes.project.dressyourself.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import taes.project.dressyourself.R;

/**
 * Created by pablo on 10/05/15.
 */
public class FloatingButtonAddFragment extends FloatingButtonCameraFragment{



    FloatingActionButton button;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_floating_button_add,container,false);
        button= (FloatingActionButton) v.findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
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
