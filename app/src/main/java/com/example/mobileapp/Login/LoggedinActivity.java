package com.example.mobileapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobileapp.MainPages.AccountDashboard;
import com.example.mobileapp.MainPages.InstitutionDashboard;
import com.example.mobileapp.MainPages.Newsfeed;
import com.example.mobileapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoggedinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        BottomNavigationView bottomNav = findViewById(R.id.navigationBar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Newsfeed()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_newsfeed:
                            selectedFragment = new Newsfeed();
                            break;
                        case R.id.nav_insDashboard:
                            selectedFragment = new InstitutionDashboard();
                            break;
                        case R.id.nav_accDashboard:
                            selectedFragment = new AccountDashboard();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
