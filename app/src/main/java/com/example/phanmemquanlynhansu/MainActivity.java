package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.phanmemquanlynhansu.Fragment.FragmentLichCong;
import com.example.phanmemquanlynhansu.Fragment.FragmentThem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNav;
    ImageView imgMore, imgReload, imgBack;
    CircleImageView btnVd;
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
        btnVd = view.findViewById(R.id.btn_video);
        btnVd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("https://www.youtube.com/playlist?list=PL-2cPclBp5g5wXDhCGtA0LHxkVnkIlHpg&fbclid=IwAR2EpvjZYmvk5eMVh58wB4G81AfxYZOwNos_OLUdlWdco39LY4AY7lkU9D4"));
                startActivity(openURL);
            }
        });

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
