package taes.project.dressyourself.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by pablo on 27/03/15.
 */
public class FragmentBackActivity extends ActionBarActivity {

    @Override
    public void onBackPressed() {
       if(getSupportFragmentManager().getBackStackEntryCount()!=0){
            getSupportFragmentManager().popBackStack();

        }else
        {
            super.onBackPressed();
        }
    }


}
