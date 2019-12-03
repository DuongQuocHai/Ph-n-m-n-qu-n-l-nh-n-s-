package com.example.phanmemquanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phanmemquanlynhansu.Model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class ThemNhanVienActivity extends AppCompatActivity {
    Spinner spnChucVu, spnChiNhanh;

    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_them_nhan_vien);
        View view = getSupportActionBar().getCustomView();
        ivBack = view.findViewById(R.id.action_bar_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemNhanVienActivity.this, NhanVienActivity.class);
                startActivity(intent);
            }
        });
        spnChucVu = (Spinner) findViewById(R.id.spnChucVu);
        spnChiNhanh = (Spinner) findViewById(R.id.spnChiNhanh);
        List<String> listChucVu = new ArrayList<>();
        listChucVu.add("Quản lý");
        listChucVu.add("Nhân Viên");
        List<String> listChiNhanh = new ArrayList<>();
        listChiNhanh.add("Chi nhánh 1: CV PMQT");
        listChiNhanh.add("Chi nhánh 2: Nam Kì Khởi Nghĩa");
        listChiNhanh.add("Chi Nhánh 3: Nguyễn Kiệm");
        ArrayAdapter<String> adapterChucVu = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listChucVu);
        adapterChucVu.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChucVu.setAdapter(adapterChucVu);

        ArrayAdapter<String> adapterChiNhanh = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listChiNhanh);
        adapterChiNhanh.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChiNhanh.setAdapter(adapterChiNhanh);

    }
}
