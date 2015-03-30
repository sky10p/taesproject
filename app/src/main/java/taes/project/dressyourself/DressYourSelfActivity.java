package taes.project.dressyourself;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.parse.ParseUser;

import taes.project.dressyourself.adapter.AdapterDrawerNavigation;
import taes.project.dressyourself.fragment.CategoriasFragment;
import taes.project.dressyourself.fragment.ConjuntosFragment;

public class DressYourSelfActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawer;
    private ConjuntosFragment conjuntosFragment;
    private RecyclerView listaDrawer;

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()!=0){
            getSupportFragmentManager().popBackStackImmediate();

        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dressyourself);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_dress_your_self_circle);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listaDrawer= (RecyclerView) findViewById(R.id.left_drawer);
        listaDrawer.setLayoutManager(new LinearLayoutManager(this));
        AdapterDrawerNavigation adapter=new AdapterDrawerNavigation(this);


        adapter.setListenerCargarCategoria(cargarCategoriasListener);
        adapter.setListenerLogout(logoutListener);
        listaDrawer.setAdapter(adapter);
        conjuntosFragment=new ConjuntosFragment();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawer=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_open_drawer,R.string.menu_close_drawer);

        drawerLayout.setDrawerListener(actionBarDrawer);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawer.syncState();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame,conjuntosFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchview, menu);
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

    final View.OnTouchListener cargarCategoriasListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.LTGRAY);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new CategoriasFragment()).addToBackStack(null).commit();
                    drawerLayout.closeDrawers();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setBackgroundColor(Color.LTGRAY);
                    break;
                default:
                    v.setBackgroundColor(Color.TRANSPARENT);
            }
            return true;
        }
    };

   final View.OnTouchListener logoutListener = new View.OnTouchListener()
   {
       @Override
       public boolean onTouch(View v, MotionEvent event)
       {
           switch(event.getAction())
           {
               case MotionEvent.ACTION_DOWN:
                   v.setBackgroundColor(Color.LTGRAY);
                   break;
               case MotionEvent.ACTION_UP:
                   v.setBackgroundColor(Color.TRANSPARENT);
                   ParseUser.logOut();
                   Intent intent = new Intent(DressYourSelfActivity.this,LoginActivity.class);
                   startActivity(intent);
                   finish();
                   break;
               case MotionEvent.ACTION_MOVE:
                   v.setBackgroundColor(Color.LTGRAY);
                   break;
               default:
                   v.setBackgroundColor(Color.TRANSPARENT);
           }
           return true;
       }
   };
}
