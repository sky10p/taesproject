package taes.project.dressyourself.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.viewpagerindicator.LinePageIndicator;

import taes.project.dressyourself.R;
import taes.project.dressyourself.fragments.LoginFragment;
import taes.project.dressyourself.fragments.LoginSignupFragment;
import taes.project.dressyourself.fragments.SlidePageFragment;
import taes.project.dressyourself.transformers.DepthPageTransformer;

/**
 * Created by isma on 1/04/15.
 */
public class ScreenSlideActivity extends AppCompatActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    public static final int LOGIN=4;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.


        /*postponeEnterTransition();
        mPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            public boolean onPreDraw() {
                mPager.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });*/
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setPageTransformer(true, new DepthPageTransformer());

        LinePageIndicator indicator = (LinePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
    }



    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem()==mPagerAdapter.getCount()-1){
            mPager.setCurrentItem(0,true);
            return;
        }
        if(mPager.getCurrentItem()==0){
            super.onBackPressed();
            return;
        }
            // Otherwise, select the previous step.
        mPager.setCurrentItem(mPager.getCurrentItem() - 1,true);

    }

    public void cambiarPagina(int pagina){
        mPager.setCurrentItem(pagina,true);

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }




        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            switch(position)
            {

                case 0:
                    return new LoginSignupFragment();
                case LOGIN:
                    return new LoginFragment();
                case 1:
                    bundle.putInt("imagen_id",R.drawable.organiza);
                    bundle.putString("descripcion","Organiza tu ropa");
                    break;
                case 2:
                    bundle.putInt("imagen_id", R.drawable.aprovecha);
                    bundle.putString("descripcion","Aprovecha tu ropa");
                    break;
                case 3:
                    bundle.putInt("imagen_id",R.drawable.comparte);
                    bundle.putString("descripcion","Comparte tu ropa");
                    break;


            }

            SlidePageFragment fragment=new SlidePageFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
