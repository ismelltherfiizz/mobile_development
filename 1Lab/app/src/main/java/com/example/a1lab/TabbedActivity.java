package com.example.a1lab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TabbedActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        initFields();
    }

    private void initFields() {
        bottomNavigationView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_territories,
                R.id.navigation_blank,
                R.id.navigation_user_profile,
                R.id.navigation_territory_details
        ).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.e("Debug", "onDestinationChanged: " + destination.getLabel());
            FrameLayout frameLayout = findViewById(R.id.host_fragment_frame_layout);
            frameLayout.setVisibility(View.GONE);
            View navFragment = findViewById(R.id.nav_host_fragment);
            navFragment.setVisibility(View.VISIBLE);
        });
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

}