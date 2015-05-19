package taes.project.dressyourself.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseUser;

import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.AdapterDrawerNavigation;
import taes.project.dressyourself.fragments.AmigosFragment;
import taes.project.dressyourself.fragments.CategoriasFragment;
import taes.project.dressyourself.fragments.FloatingButtonCameraFragment;
import taes.project.dressyourself.fragments.PublicacionesFragment;
import taes.project.dressyourself.interfaces.OnBackPressedListener;
import taes.project.dressyourself.interfaces.OnDrawerLayoutMenuListener;


public class DressYourSelfActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawer;
    private PublicacionesFragment publicacionesFragment;
    private RecyclerView listaDrawer;
    public FloatingButtonCameraFragment floatingButton;

    private OnBackPressedListener onBackPressedListener;

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        this.onBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
       if(!floatingButton.isExpanded()){
            super.onBackPressed();
            return;
        }else {
            floatingButton.collapse();
            return;
        }
    }

    public void setFloatingButton(FloatingButtonCameraFragment fragment){
        this.floatingButton=fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dress_yourself_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_dress_your_self_circle);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listaDrawer= (RecyclerView) findViewById(R.id.left_drawer);
        listaDrawer.setLayoutManager(new LinearLayoutManager(this));


        AdapterDrawerNavigation adapter=new AdapterDrawerNavigation(this);

        adapter.setOnDrawerLayoutMenuListener(new OnDrawerLayoutMenuListener() {
            @Override
            public void onClicArmario() {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CategoriasFragment()).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
            }

            @Override
            public void onClicAmigos() {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AmigosFragment()).addToBackStack(null).commit();
                drawerLayout.closeDrawers();
            }

            @Override
            public void onClicAjustes() {

            }

            @Override
            public void onClicAyuda() {

            }

            @Override
            public void onClicCerrarSesion() {
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listaDrawer.setAdapter(adapter);
        publicacionesFragment =new PublicacionesFragment();

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
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, publicacionesFragment).commit();
        }

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
    


}
