package risabh_mishra.olaandroidplay;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Songs> songs;
    private ApiInterface apiInterface;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = (SearchView)view.findViewById(R.id.search_view);


        recycler = (RecyclerView) view.findViewById(R.id.rview);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<Songs>> call = apiInterface.getSongsInfo();
        call.enqueue(new Callback<ArrayList<Songs>>() {
            @Override
            public void onResponse(Call<ArrayList<Songs>> call, Response<ArrayList<Songs>> response) {
                songs = response.body();
                adapter = new RecyclerViewAdapter(songs, getContext());
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Songs>> call, Throwable t) {

            }
        });


searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(adapter!=null)
            adapter.getFilter().filter(newText);
        return true;
    }
});


        return view;
    }


}



