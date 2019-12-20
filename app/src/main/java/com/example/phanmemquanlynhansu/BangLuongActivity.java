package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLamViec;
import com.example.phanmemquanlynhansu.Model.ModelChucVu;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BangLuongActivity extends AppCompatActivity {
    View view;
    ImageButton btnBack;
    TextView txtTen, txtCuaHang, txtChucVu, txtThang, txtTongGioLam, txtLuong;
    ImageView btnBefore, btnAfter;
    ListView lvBangLuong;
    LinearLayout layoutMain;
    ModelNhanVien modelNhanVien;

    ArrayList<String> list;
    ArrayAdapter adapter;
    DatabaseReference mData;
    ModelCaLam modelCaLam;

    ProgressBar progressBar;

    int month, year;
    double luong;
    String thang, nam, cuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_luong);
        addControls();
        readData();
        addEvents();
    }

    public void addControls() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_bangluong);
        view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.action_bar_back_bangluong);
        txtTen = findViewById(R.id.txt_tennv_bangluong);
        txtCuaHang = findViewById(R.id.txt_cuahang_bangluong);
        txtChucVu = findViewById(R.id.txt_chucvu_bangluong);
        txtThang = findViewById(R.id.txt_thang_bangluong);
        txtTongGioLam = findViewById(R.id.txt_tonggio_bangluong);
        txtLuong = findViewById(R.id.txt_luong_bangluong);
        btnAfter = findViewById(R.id.btn_sau_bangluong);
        btnBefore = findViewById(R.id.btn_truoc_bangluong);
        layoutMain = findViewById(R.id.ly_main_bangluong);
        lvBangLuong = findViewById(R.id.lv_bangluong);
        progressBar = findViewById(R.id.pr_bangluong);
    }

    public void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressBefore();
            }
        });
        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressAfter();
            }
        });
    }

    public void readData() {
        Intent intent = getIntent();
        modelNhanVien = (ModelNhanVien) intent.getSerializableExtra("ModelNhanVien");
        if (modelNhanVien != null) {
            txtTen.setText(modelNhanVien.getTenNv());
            txtCuaHang.setText(modelNhanVien.getMaCuaHang());
            txtChucVu.setText(modelNhanVien.getMaChucVu());
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            thang = String.valueOf(month);
            nam = String.valueOf(year);
            if (month < 10) {
                thang = "0" + month;
            }
            txtThang.setText(nam + "-" + thang);
            getListLichLam(modelNhanVien.getIdNv());
        }
    }

    public void getListLichLam(final String idUser) {
        layoutMain.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        cuaHang = txtCuaHang.getText().toString();
        luong = 0;
        txtLuong.setText(luong + "");
        adapter = new ArrayAdapter(BangLuongActivity.this, android.R.layout.simple_list_item_1, list);
        lvBangLuong.setAdapter(adapter);
        mData = FirebaseDatabase.getInstance().getReference("CaLamViec");
        mData.orderByKey().startAt(cuaHang + "_" + nam + "-" + thang).endAt(cuaHang + "_" + nam + "-" + thang + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if (dataSnapshot.getValue() != null) {
                    for (final DataSnapshot data : dataSnapshot.getChildren()) {
                        mData.child(data.getKey()).child("listNhanVien")
                                .orderByChild("idNv")
                                .equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    list.add(data.getKey());
                                    ModelCaLamViec modelCaLamViec = data.getValue(ModelCaLamViec.class);
                                    modelCaLam = modelCaLamViec.getModelCaLam();
                                    luong += modelCaLam.getLuongCaLam();
                                }
                                txtLuong.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(luong) + " vnd");
                                adapter.notifyDataSetChanged();
                                layoutMain.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                } else {
                    layoutMain.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void pressAfter() {
        month++;
        if (month > 12) {
            month = 1;
            year++;
            thang = String.valueOf(month);
            nam = String.valueOf(year);
        }
        if (month < 10) {
            thang = "0" + month;
            txtThang.setText(nam + "-" + thang);
            getListLichLam(modelNhanVien.getIdNv());
            return;

        }
        thang = String.valueOf(month);
        nam = String.valueOf(year);
        txtThang.setText(nam + "-" + thang);
        getListLichLam(modelNhanVien.getIdNv());
    }

    public void pressBefore() {
        month--;
        if (month < 10) {
            thang = "0" + month;
            if (month < 1) {
                month = 12;
                year--;
                thang = String.valueOf(month);
                nam = String.valueOf(year);
            }
        } else {
            thang = String.valueOf(month);
        }
        txtThang.setText(nam + "-" + thang);
        getListLichLam(modelNhanVien.getIdNv());
    }

}
