package taes.project.dressyourself.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.adapter.UsuariosAdapter;

public class SearchAmigosFragment extends Fragment {

    private EditText searchAmigos;
    private RecyclerView recyclerView;
    private UsuariosAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search_amigos,container,false);
        searchAmigos = (EditText) v.findViewById(R.id.search_amigos);
        recyclerView = (RecyclerView) v.findViewById(R.id.lstUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UsuariosAdapter(new ArrayList<ParseUser>());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        searchAmigos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ParseQuery<ParseUser> query1 = ParseUser.getQuery();
                query1.whereContains("username", s.toString());
                ParseQuery<ParseUser> query2 = ParseUser.getQuery();
                query2.whereContains("email", s.toString());
                ArrayList<ParseQuery<ParseUser>> queries = new ArrayList<>();
                queries.add(query1);
                queries.add(query2);
                ParseQuery<ParseUser> query = ParseQuery.or(queries);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {
                        adapter.setUsuarios(list);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }


}
