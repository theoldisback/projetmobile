package com.example.projetmobile.View.Userview;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projetmobile.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // Set up Toolbar

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Ensure the listener is set correctly
        navigationView.setNavigationItemSelectedListener(this);


        // Set up Drawer Toggle
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Make sure the DrawerToggle works properly
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Optional: Show home page as the default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }
        drawerLayout.openDrawer(GravityCompat.START);

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            Log.d("FragmentTransaction", "Replacing with DashboarduserFragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new DashboarduserFragment())
                    .commit();
        }
        if (item.getItemId() == R.id.nav_profile) {
            Log.d("FragmentTransaction", "Replacing with DashboarduserFragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new ProfilerFragment())
                    .commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}