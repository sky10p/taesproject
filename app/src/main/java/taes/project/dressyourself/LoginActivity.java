package taes.project.dressyourself;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;


import taes.project.dressyourself.animation.LayoutAnimation;
import taes.project.dressyourself.animation.VisibilityAnimationListener;
import taes.project.dressyourself.fragment.LoginFragment;
import taes.project.dressyourself.fragment.SignupFragment;


public class LoginActivity extends ActionBarActivity {
    private ImageView imgSplash;
    private TextView txtSplash;
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        imgSplash= (ImageView) findViewById(R.id.imgSplash);
        txtSplash= (TextView) findViewById(R.id.txtDescription);


        configureTabHost();

        configureAnimation();

    }

    private void configureAnimation() {
        LayoutAnimation animation=new LayoutAnimation(this,189,192,imgSplash);
        animation.setStartOffset(2000);
        animation.setDuration(2000);

        AlphaAnimation alphaInvisibility=new AlphaAnimation(1,0);
        alphaInvisibility.setFillAfter(true);
        alphaInvisibility.setStartOffset(2000);
        alphaInvisibility.setDuration(2000);
        alphaInvisibility.setAnimationListener(new VisibilityAnimationListener(txtSplash, View.VISIBLE, View.GONE){
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                AlphaAnimation alphaVisibility=new AlphaAnimation(0,1);
                alphaVisibility.setFillAfter(true);
                alphaVisibility.setDuration(1000);
                alphaVisibility.setAnimationListener(new VisibilityAnimationListener(tabHost,View.VISIBLE,View.VISIBLE));

                tabHost.startAnimation(alphaVisibility);

            }
        });




        txtSplash.startAnimation(alphaInvisibility);
        imgSplash.startAnimation(animation);

    }



    private void configureTabHost() {
        tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        Bundle registrar=new Bundle();

        registrar.putString("type", getString(R.string.signup));

        Bundle login=new Bundle();
        login.putString("type",getString(R.string.login));

        tabHost.addTab(tabHost.newTabSpec(getString(R.string.login))
                .setIndicator(getString(R.string.login)),LoginFragment.class,login);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.signup))
                .setIndicator(getString(R.string.signup)), SignupFragment.class,registrar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
