package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.phanmemquanlynhansu.Fragment.FragmentLichCong;
import com.example.phanmemquanlynhansu.Fragment.FragmentThem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNav;
    ImageView imgMore, imgReload, imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_homepage,
//                new FragmentLichCong()).commit();

        defaultFragment(new FragmentLichCong());
        bottomNav.setOnNavigationItemSelectedListener(this);

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.lichcong:
                selectedFragment = new FragmentLichCong();
                break;

            case R.id.them:
                selectedFragment = new FragmentThem();
                break;
        }
        return defaultFragment(selectedFragment);
    }

    private boolean defaultFragment(Fragment fragment) {
        if (fragment !=null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_homepage, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
