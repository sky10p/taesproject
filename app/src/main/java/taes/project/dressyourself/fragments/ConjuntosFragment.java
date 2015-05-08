package taes.project.dressyourself.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.CameraActivity;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.adapter.AdapterConjunto;
import taes.project.dressyourself.interfaces.OnBackPressedListener;

/**
 * Created by pablo on 24/02/15.
 */
public class ConjuntosFragment extends Fragment {

    private RecyclerView lstConjuntos;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private Context contexto;

    public FloatingButtonFragment floatingButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_conjuntos,container,false);

        lstConjuntos= (RecyclerView) v.findViewById(R.id.lstConjuntos);
        manager=new LinearLayoutManager(getActivity());
        lstConjuntos.setLayoutManager(manager);
        adapter=new AdapterConjunto();
        lstConjuntos.setAdapter(adapter);
        floatingButton= (FloatingButtonFragment) getChildFragmentManager().findFragmentById(R.id.floatingButtonFragment);

        onClickPhotoAndVoteButton(v);
        ((DressYourSelfActivity)getActivity()).setOnBackPressedListener(new OnBackPressedListener() {
            @Override
            public void onBack() {

                if (!floatingButton.isExpanded()) {
                    getActivity().onBackPressed();
                } else {
                    floatingButton.collapse();
                }
            }
        });
        return v;
    }

    public void onClickPhotoAndVoteButton(View v) {
        onClickPhotoButton(v);
        onClickVoteButton(v);
    }


    // Al hacer click en icono de foto
    public void onClickPhotoButton(View v) {

        v.findViewById(R.id.action_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });

    }

    // Al hacer click en icono de voto
    public void onClickVoteButton(View v) {

        v.findViewById(R.id.action_vote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked Vote Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
