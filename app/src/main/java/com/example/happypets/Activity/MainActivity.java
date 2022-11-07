package com.example.happypets.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.happypets.Adapters.TabLayoutAdapter;
import com.example.happypets.Map.MapsActivity;
import com.example.happypets.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    // creating a Tag string
    private final String TAG = MainActivity.class.getSimpleName();

    //creating variables for adding functionality to the drawer layout
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting viewpager
        ViewPager viewPager = findViewById(R.id.viewpager);
        //setting adapter to viewpager
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabLayoutAdapter);
        //setting viewpager to tab layout
        TabLayout mainTab = findViewById(R.id.mainTab);
        mainTab.setupWithViewPager(viewPager);

        /* declaring objects needed for navigation view */
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        /* setting up drawer layout */
        navigationView.bringToFront(); // making drawer to come to the front of the screen
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // make burger button to become back button
        // setting listener when options are selected on the navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawer_layout_profile:
                        Log.e(TAG,"profile");
                        Intent profileIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(profileIntent);
                        break;
                    case R.id.drawer_layout_chat:
                        Log.e(TAG,"chat");
                        break;
                    case R.id.drawer_layout_fav:
                        Log.e(TAG,"fav");
                        break;
                    case R.id.drawer_layout_share:
                        Log.e(TAG,"share");
                        break;
                    case R.id.drawer_layout_logout:
                        Log.e(TAG,"logout");
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu for map button
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // this is to close the drawer if it is open
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        // set the functionality of the map button
        if(item.getItemId() == R.id.map_button){
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

}


