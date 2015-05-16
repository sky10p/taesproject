package taes.project.dressyourself.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.R;
import taes.project.dressyourself.activities.DressYourSelfActivity;
import taes.project.dressyourself.activities.SearchAmigosActivity;
import taes.project.dressyourself.adapter.AmigosAdapter;
import taes.project.dressyourself.listeners.RecyclerItemClickListener;

/**
 * Created by pablo on 7/05/15.
 */
public class AmigosFragment extends Fragment {


    private RecyclerView lstAmigos;
    private FloatingButtonAddFragment buttonAdd;
    private AmigosAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        loadFriends();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((DressYourSelfActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_activity_main);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_amigos, container, false);
        lstAmigos = (RecyclerView) v.findViewById(R.id.lstAmigos);
        lstAmigos.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AmigosAdapter(getActivity());
        ((DressYourSelfActivity)getActivity()).getSupportActionBar().setTitle(getActivity().getString(R.string.friends));
        lstAmigos.setAdapter(adapter);
        lstAmigos.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), lstAmigos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));


        buttonAdd=new FloatingButtonAddFragment();
        getFragmentManager().beginTransaction().replace(R.id.floatingButtonFragment,buttonAdd).commit();
        ((DressYourSelfActivity)getActivity()).setFloatingButton(buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFriends();
            }
        });

        loadFriends();






        return v;


    }

    private void loadFriends() {
        ParseUser user=ParseUser.getCurrentUser();

        user.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject.has("amigos")) {
                    adapter.setAmigos((ArrayList<ParseUser>) parseObject.get("amigos"));
                }

            }
        });
    }

    private void searchFriends() {
        startActivity(new Intent(getActivity(),SearchAmigosActivity.class));
    }
}
