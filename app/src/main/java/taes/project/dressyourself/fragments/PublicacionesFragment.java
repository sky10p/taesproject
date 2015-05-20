package taes.project.dressyourself.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.adapter.AdapterConjunto;

/**
 * Created by pablo on 24/02/15.
 */
public class PublicacionesFragment extends Fragment {

    private RecyclerView lstConjuntos;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private Context contexto;
    //FloatingButtonCameraFragment fragmentCamera;
    FloatingButtonAddFragment floatingBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_publicaciones,container,false);
        floatingBtn = new FloatingButtonAddFragment();
        //fragmentCamera=new FloatingButtonCameraFragment();
        getFragmentManager().beginTransaction().replace(R.id.floatingButtonFragment,floatingBtn).commit();
        ((DressYourSelfActivity)getActivity()).setFloatingButton(floatingBtn);
        lstConjuntos= (RecyclerView) v.findViewById(R.id.lstConjuntos);
        manager=new LinearLayoutManager(getActivity());
        lstConjuntos.setLayoutManager(manager);
        adapter=new AdapterConjunto();
        lstConjuntos.setAdapter(adapter);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new NuevaPublicacionFragment()).addToBackStack(null).commit();
                ((DressYourSelfActivity) getActivity()).drawerLayout.closeDrawers();
            }
        });
        return v;
    }

}
