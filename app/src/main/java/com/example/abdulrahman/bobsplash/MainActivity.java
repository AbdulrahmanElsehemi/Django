package com.example.abdulrahman.bobsplash;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.abdulrahman.bobsplash.Adapter.PhotosAdapter;
import com.example.abdulrahman.bobsplash.Util.ListItemClickListener;
import com.example.abdulrahman.bobsplash.model.PhotosResponse;
import com.example.abdulrahman.bobsplash.model.Result;
import com.example.abdulrahman.bobsplash.repository.NetworkState;
import com.example.abdulrahman.bobsplash.repository.UserViewmodel;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private static final String TAG=MainActivity.class.getSimpleName();

    private UserViewmodel viewModel;

    FloatingActionButton fab;

    RecyclerView recyclerView;
    Toolbar mToolbar;

    PhotosAdapter photosAdapter;

    private ArrayList<Result> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mToolbar =findViewById(R.id.home_toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("BoB Splash");

        photosAdapter=new PhotosAdapter(MainActivity.this,itemList);


        fab=findViewById(R.id.fab);

        initRecycler();

        loadData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.scrollToPosition(0);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }






    private  void  loadData()
    {

        viewModel = ViewModelProviders.of(this).get(UserViewmodel.class);

        final PhotosAdapter userUserAdapter = new PhotosAdapter(this,MainActivity.this);



        viewModel.photoList.observe(this, new Observer<PagedList<PhotosResponse>>() {
            @Override
            public void onChanged(@Nullable PagedList<PhotosResponse> photoObjects) {

                userUserAdapter.setList(photoObjects);
            }
        });


        viewModel.networkState.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                userUserAdapter.setNetworkState(networkState);

            }


        });

        recyclerView.setAdapter(userUserAdapter);

    }

    private void  initRecycler()
    {
        recyclerView = findViewById(R.id.userList);
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(llm);
    }




    @Override
    public void onItemClick(View view, int position) {

    }
}
