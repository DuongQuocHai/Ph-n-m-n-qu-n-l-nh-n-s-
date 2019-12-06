package com.example.phanmemquanlynhansu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Fragment.FragmentThem;

public class NhanVienActivity extends AppCompatActivity {
    ListView lvNhanVien;
    ImageView ivBack, ivAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_nhan_vien);
        View view = getSupportActionBar().getCustomView();
        ivBack = view.findViewById(R.id.action_bar_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhanVienActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ivAdd = view.findViewById(R.id.action_bar_add);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhanVienActivity.this, ThemNhanVienActivity.class);
                startActivity(intent);
            }
        });

        lvNhanVien = (ListView) findViewById(R.id.lv_nhan_vien);
    }
}
