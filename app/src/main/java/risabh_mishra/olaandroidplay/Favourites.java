package risabh_mishra.olaandroidplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favourites extends Fragment {


    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    SharedPreference sharedPreference;
    ArrayList<Songs> favorites;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    public Favourites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_favourites, container, false);

        recycler = (RecyclerView) v.findViewById(R.id.recycler_fav);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.

                sharedPreference = new SharedPreference();
                favorites = sharedPreference.getFavorites(getActivity());
                if (favorites != null) {
                    //recyclerViewAdapter = new RecyclerViewAdapter(favorites,getActivity());
                    recyclerViewAdapter = new RecyclerViewAdapter(favorites,getActivity());
                    recycler.setAdapter(recyclerViewAdapter);
                }

                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        });

        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(getActivity());
        if (favorites != null) {
            //recyclerViewAdapter = new RecyclerViewAdapter(favorites,getActivity());
            recyclerViewAdapter = new RecyclerViewAdapter(favorites,getActivity());
            recycler.setAdapter(recyclerViewAdapter);
        }






        return v;
    }

}
