package taes.project.dressyourself.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.UsuariosAdapter;

public class SearchAmigosActivity extends AppCompatActivity {

    private EditText searchAmigos;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_amigos);

        searchAmigos = (EditText) findViewById(R.id.search_amigos);
        recyclerView = (RecyclerView) findViewById(R.id.lstUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchAmigos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ParseQuery<ParseUser> query1=ParseUser.getQuery();
                query1.whereContains("username", s.toString());
                ParseQuery<ParseUser> query2=ParseUser.getQuery();
                query2.whereContains("email", s.toString());
                ArrayList<ParseQuery<ParseUser>> queries=new ArrayList<>();
                queries.add(query1);
                queries.add(query2);
                ParseQuery<ParseUser> query=ParseQuery.or(queries);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {
                        recyclerView.setAdapter(new UsuariosAdapter(list));
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_amigos, menu);
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
