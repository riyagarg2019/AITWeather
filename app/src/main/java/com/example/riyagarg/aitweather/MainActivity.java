package com.example.riyagarg.aitweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.CityCreateDialog;
import com.adapter.CityRecyclerAdapter;
import com.data.AppDatabase;
import com.data.City;
import com.touch.CityTouchHelperCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CityCreateDialog.CityHandler {

    private CityRecyclerAdapter cityRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewCityDialog();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_add){

                    showNewCityDialog();


                } else if (id == R.id.nav_about){

                    Toast.makeText(MainActivity.this, "Author: Riya Garg", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerTodo);

        recyclerView.setHasFixedSize(true); //set view to fit whole screen
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //reason why it looks like a list (linear layout), could change this to Grid



        initCities(recyclerView);


    }

    public void initCities (final RecyclerView recyclerView){
        new Thread(){
            @Override
            public void run() {
                final List<City> cities = AppDatabase.getAppDatabase(MainActivity.this).cityDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cityRecyclerAdapter = new CityRecyclerAdapter(cities, MainActivity.this);//create recycler object
                        recyclerView.setAdapter(cityRecyclerAdapter);  //where the adapter and recycler view connect together

                        ItemTouchHelper.Callback callback = new CityTouchHelperCallback(cityRecyclerAdapter);
                        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                        touchHelper.attachToRecyclerView(recyclerView);



                    }
                });

            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_add){

            showNewCityDialog();


        } else if (id == R.id.nav_about){

            Toast.makeText(MainActivity.this, "Author: Riya Garg", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNewCityDialog() {
        new CityCreateDialog().show(getSupportFragmentManager(), "CityCreateDialog");
    }

    @Override
    public void onNewCityCreated(final String city) { //add info into the list
        //which class is responsible for the data of the recycler view: adapter
        new Thread(){
            @Override
            public void run() {

                final City newCity = new City(city);

                long id = AppDatabase.getAppDatabase(MainActivity.this).cityDao().insertCity(newCity);

                newCity.setCityId(id);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cityRecyclerAdapter.addCity(newCity);

                    }
                });


            }
        }.start();

    }

    @Override
    public void onCityUpdated(final City city){
        new Thread(){
            @Override
            public void run() {
                AppDatabase.getAppDatabase(MainActivity.this).cityDao().update(city);

                runOnUiThread(new Runnable() { //in this thread because now changing the UI
                    @Override
                    public void run() {
                        cityRecyclerAdapter.updateCity(city);
                    }
                });
            }
        }.start();
    }
}
