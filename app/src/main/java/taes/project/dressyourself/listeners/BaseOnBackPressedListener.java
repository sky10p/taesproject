package taes.project.dressyourself.listeners;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import taes.project.dressyourself.interfaces.OnBackPressedListener;

/**
 * Created by isma on 13/04/15.
 */
public class BaseOnBackPressedListener implements OnBackPressedListener {
    private final FragmentActivity activity;
    public BaseOnBackPressedListener(FragmentActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public void onBack()
    {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
