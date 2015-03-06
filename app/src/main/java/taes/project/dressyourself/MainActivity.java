package taes.project.dressyourself;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawer;
    private ConjuntosFragment conjuntosFragment;
    private RecyclerView listaDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "s90VyHtWPNx9cgndGgresmqQxjT1drJRcRNwBFzX", "WJRi26caKuA8QAmDiy9Xm1eB79fE950wMM2hHDCc");
        // Ejemplo de insercción con parse
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_dress_your_self_circle);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listaDrawer= (RecyclerView) findViewById(R.id.left_drawer);
        listaDrawer.setLayoutManager(new LinearLayoutManager(this));
        listaDrawer.setAdapter(new AdapterDrawerNavigation(this));
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
