package taes.project.dressyourself.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.CameraActivity;

/**
 * Created by pablo on 27/03/15.
 */
public class FloatingButtonFragment extends Fragment {

    private FloatingActionsMenu btnFloatingMain;
    private RelativeLayout relativeLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_floating_button,container,false);

        btnFloatingMain= (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
        relativeLayout= (RelativeLayout) v.findViewById(R.id.fondoFloatingButton);

        onClickPhotoAndVoteButton(v);

        btnFloatingMain.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                relativeLayout.setBackgroundResource(R.color.black_semi_transparent);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnFloatingMain.collapse();
                    }
                });
                relativeLayout.setClickable(true);
            }

            @Override
            public void onMenuCollapsed() {
                relativeLayout.setBackgroundColor(Color.TRANSPARENT);
                relativeLayout.setClickable(false);
            }
        });






        return v;
    }

    public void onClickPhotoAndVoteButton(View v) {
        onClickPhotoButton(v);
        onClickVoteButton(v);
    }


    // Al hacer click en icono de foto
    public void onClickPhotoButton(View v) {

        v.findViewById(R.id.action_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });

    }

    // Al hacer click en icono de voto
    public void onClickVoteButton(View v) {

        v.findViewById(R.id.action_vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked Vote Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isExpanded(){
        return btnFloatingMain.isExpanded();
    }

    public void collapse(){
        btnFloatingMain.collapse();
    }
}
