package taes.project.dressyourself.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.adapter.AdapterConjunto;

/**
 * Created by pablo on 24/02/15.
 */
public class PublicacionesFragment extends Fragment {

    private RecyclerView lstConjuntos;
    private AdapterConjunto adapter;
    private RecyclerView.LayoutManager manager;
    private Context contexto;
    //FloatingButtonFragment fragmentCamera;
    FloatingButtonAddFragment floatingBtn;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_publicaciones,container,false);
        floatingBtn = new FloatingButtonAddFragment();
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        //fragmentCamera=new FloatingButtonFragment();
        getFragmentManager().beginTransaction().replace(R.id.floatingButtonFragment,floatingBtn).commit();
        ((DressYourSelfActivity)getActivity()).setFloatingButton(floatingBtn);
        lstConjuntos= (RecyclerView) v.findViewById(R.id.lstConjuntos);
        manager=new LinearLayoutManager(getActivity());
        lstConjuntos.setLayoutManager(manager);

        adapter=new AdapterConjunto(getActivity());
        lstConjuntos.setAdapter(adapter);

        loadPublicaciones();
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new NuevaPublicacionFragment()).addToBackStack(null).commit();
                ((DressYourSelfActivity) getActivity()).drawerLayout.closeDrawers();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPublicaciones();
            }
        });
        return v;
    }

    private void loadPublicaciones() {
        ParseQuery<ParseObject> publicaciones= ParseQuery.getQuery("Publicacion");

        publicaciones.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                ArrayList<AdapterConjunto.ConjuntoRopa> conjuntos = new ArrayList<>();
                Random random=new Random();
                for (ParseObject parseObject : list) {

                    int votos;
                    votos=parseObject.getInt("votos");

                    AdapterConjunto.ConjuntoRopa conjunto = new AdapterConjunto.ConjuntoRopa(parseObject.getObjectId(),parseObject.getString("titulo"),
                            parseObject.getString("texto"), parseObject.getParseFile("foto").getUrl(),votos);
                    conjuntos.add(conjunto);


                }

                swipeRefreshLayout.setRefreshing(false);
                adapter.setConjuntos(conjuntos);
            }
        });
    }

}
