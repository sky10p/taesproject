package taes.project.dressyourself.activities;


import taes.project.dressyourself.fragment.FloatingButtonFragment;

/**
 * Created by pablo on 27/03/15.
 */
public class FloatingButtonActivity extends FragmentBackActivity {


    public FloatingButtonFragment floatingButton;

    @Override
    public void onBackPressed() {

        if(!floatingButton.isExpanded()){
            super.onBackPressed();
        }else {
            floatingButton.collapse();
        }
    }




}
