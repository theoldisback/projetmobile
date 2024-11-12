package com.example.projetmobile.View.Userview;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFrontActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdashboard_layout);

        // Set up bottom navigation view
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the default fragment
        loadFragment(new HomeFragment());

        // Set up item selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                // Handle bottom navigation item clicks

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new DashboarduserFragment();
                }
                if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfilerFragment();
                }
                // Load the selected fragment into the frame
                loadFragment(selectedFragment);
                return true;
            }
        });
    }

    // Helper method to load fragments into the FrameLayout
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}