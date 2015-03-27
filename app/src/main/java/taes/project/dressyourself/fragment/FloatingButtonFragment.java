package taes.project.dressyourself.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import taes.project.dressyourself.R;

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

        btnFloatingMain.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                relativeLayout.setBackgroundResource(R.color.black_semi_transparent);
            }

            @Override
            public void onMenuCollapsed() {
                relativeLayout.setBackgroundColor(Color.TRANSPARENT);
            }
        });



        return v;
    }

    public boolean isExpanded(){
        return btnFloatingMain.isExpanded();
    }

    public void collapse(){
        btnFloatingMain.collapse();
    }
}
