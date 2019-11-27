package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.phanmemquanlynhansu.Fragment.FragmentChamCong;
import com.example.phanmemquanlynhansu.Fragment.FragmentLichCong;
import com.example.phanmemquanlynhansu.Fragment.FragmentThem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_homepage,
                new FragmentLichCong()).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_lichcong:
                        selectedFragment = new FragmentLichCong();
                        break;

                    case R.id.nav_chamcong:
                        selectedFragment = new FragmentChamCong();
                        break;

                    case R.id.nav_them:
                        selectedFragment = new FragmentThem();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_homepage, selectedFragment).commit();
                return false;
            }

        });


    }
}
