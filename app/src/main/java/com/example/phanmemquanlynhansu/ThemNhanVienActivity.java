package com.example.phanmemquanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemNhanVienActivity extends AppCompatActivity {
    Spinner spnChucVu, spnChiNhanh;
    EditText edtId,edtTen,edtSdt,edtDiaChi;
    CheckBox ckbNam,ckbNu;
    CircleImageView imgNhanVien;


    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        addControl();
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_them_nhan_vien);
        View view = getSupportActionBar().getCustomView();
        ivBack = view.findViewById(R.id.action_bar_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        spnChucVu = (Spinner) findViewById(R.id.spn_chucvu_themnv);
//        spnChiNhanh = (Spinner) findViewById(R.id.spn_chinhanh_thmenv);
//        List<String> listChucVu = new ArrayList<>();
//        listChucVu.add("Quản lý");
//        listChucVu.add("Nhân Viên");
//        List<String> listChiNhanh = new ArrayList<>();
//        listChiNhanh.add("Chi nhánh 1: CV PMQT");
//        listChiNhanh.add("Chi nhánh 2: Nam Kì Khởi Nghĩa");
//        listChiNhanh.add("Chi Nhánh 3: Nguyễn Kiệm");
//        ArrayAdapter<String> adapterChucVu = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listChucVu);
//        adapterChucVu.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//        spnChucVu.setAdapter(adapterChucVu);
//
//        ArrayAdapter<String> adapterChiNhanh = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listChiNhanh);
//        adapterChiNhanh.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//        spnChiNhanh.setAdapter(adapterChiNhanh);

    }
    public void addControl(){
        edtId = findViewById(R.id.edt_id_themnv);
        edtTen= findViewById(R.id.edt_ten_themnv);
        edtSdt = findViewById(R.id.edt_sdt_themnv);
        edtDiaChi= findViewById(R.id.edt_diachi_themnv);
        spnChucVu = findViewById(R.id.spn_chucvu_themnv);
        spnChiNhanh = findViewById(R.id.spn_chinhanh_thmenv);
        ckbNam = findViewById(R.id.ckb_nam_themnv);
        ckbNu = findViewById(R.id.ckb_nu_themnv);
    }
}
