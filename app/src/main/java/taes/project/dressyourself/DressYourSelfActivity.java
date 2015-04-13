package taes.project.dressyourself;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseUser;

import taes.project.dressyourself.activities.FloatingButtonActivity;
import taes.project.dressyourself.adapter.AdapterDrawerNavigation;
import taes.project.dressyourself.fragments.CategoriasFragment;
import taes.project.dressyourself.fragments.ConjuntosFragment;
import taes.project.dressyourself.fragments.FloatingButtonFragment;
import taes.project.dressyourself.interfaces.OnDrawerLayoutMenuListener;


public class DressYourSelfActivity extends FloatingButtonActivity {

    public Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawer;
    private ConjuntosFragment conjuntosFragment;
    private RecyclerView listaDrawer;

    /*@Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()!=0){
            getSupportFragmentManager().popBackStackImmediate();
    */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dress_yourself_activity);        



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_dress_your_self_circle);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listaDrawer= (RecyclerView) findViewById(R.id.left_drawer);
        listaDrawer.setLayoutManager(new LinearLayoutManager(this));
        floatingButton= (FloatingButtonFragment) getSupportFragmentManager().findFragmentById(R.id.floatingButtonFragment);
        AdapterDrawerNavigation adapter=new AdapterDrawerNavigation(this);



        adapter.setOnDrawerLayoutMenuListener(new OnDrawerLayoutMenuListener() {
            @Override
            public void onClicArmario() {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new CategoriasFragment()).addToBackStack(null).commit();

                drawerLayout.closeDrawers();
            }

            @Override
            public void onClicAmigos() {

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
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        onClickPhotoAndVoteButton();
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
    
    public void onClickPhotoAndVoteButton() {
        onClickPhotoButton();
        onClickVoteButton();
    }

   
    // Al hacer click en icono de foto
    public void onClickPhotoButton() {        
            
        findViewById(R.id.action_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DressYourSelfActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });  
       
    }

    // Al hacer click en icono de voto
    public void onClickVoteButton() {

        findViewById(R.id.action_vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DressYourSelfActivity.this, "Clicked Vote Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
