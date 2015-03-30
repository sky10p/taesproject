package taes.project.dressyourself;


<<<<<<< HEAD
=======
import android.app.FragmentManager;
>>>>>>> develop
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
import android.widget.FrameLayout;
import android.widget.Toast;

<<<<<<< HEAD
import com.parse.ParseUser;

=======
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.ParseUser;

import taes.project.dressyourself.activities.FloatingButtonActivity;
>>>>>>> develop
import taes.project.dressyourself.adapter.AdapterDrawerNavigation;
import taes.project.dressyourself.fragment.CategoriasFragment;
import taes.project.dressyourself.fragment.ConjuntosFragment;
import taes.project.dressyourself.fragment.FloatingButtonFragment;
import taes.project.dressyourself.interfaces.OnDrawerLayoutMenuListener;

<<<<<<< HEAD
public class DressYourSelfActivity extends ActionBarActivity {

    private Toolbar toolbar;
=======

public class DressYourSelfActivity extends FloatingButtonActivity {

    private Toolbar toolbar;   
>>>>>>> develop
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawer;
    private ConjuntosFragment conjuntosFragment;
    private RecyclerView listaDrawer;

<<<<<<< HEAD
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()!=0){
            getSupportFragmentManager().popBackStackImmediate();
=======

>>>>>>> develop



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_dressyourself);
=======
        setContentView(R.layout.dress_yourself_activity);        
>>>>>>> develop


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_dress_your_self_circle);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listaDrawer= (RecyclerView) findViewById(R.id.left_drawer);
        listaDrawer.setLayoutManager(new LinearLayoutManager(this));
        floatingButton= (FloatingButtonFragment) getSupportFragmentManager().findFragmentById(R.id.floatingButtonFragment);
        AdapterDrawerNavigation adapter=new AdapterDrawerNavigation(this);


<<<<<<< HEAD
        adapter.setListenerCargarCategoria(cargarCategoriasListener);
        adapter.setListenerLogout(logoutListener);
=======
       
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

>>>>>>> develop
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



<<<<<<< HEAD
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
=======


    public void onClickVoteButton() {

        findViewById(R.id.action_vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DressYourSelfActivity.this, "Clicked Vote Button", Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    
>>>>>>> develop
}
