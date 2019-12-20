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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLamViec;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
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

public class ThongKeActivity extends AppCompatActivity {
    View view;
    ImageButton btnBack;
    TextView txtCuaHang, txtTongNv, txtThang, txtTongLuong;
    ImageView btnBefore, btnAfter;

    ModelCuaHang modelCuaHang;

    int month, year, tongNhanVien;
    double luong, nv, tongLuong;

    String thang, nam, cuaHang;

    ArrayList<String> lisNhanVien;
    ArrayList<ModelNhanVien> listNvClViec;

    ProgressBar progressBar;


    DatabaseReference mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        addControls();
        readData();
        addEvents();
    }

    public void addControls() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_thongke);
        view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.action_bar_back_thongke);

        txtCuaHang = findViewById(R.id.txt_cuahang_thongke);
        txtTongNv = findViewById(R.id.txt_sonhanvien_thongke);
        txtThang = findViewById(R.id.txt_thang_thongke);
        txtTongLuong = findViewById(R.id.txt_tongluong_thongke);
        progressBar = findViewById(R.id.pr_thongke);


        btnAfter = findViewById(R.id.btn_sau_thongke);
        btnBefore = findViewById(R.id.btn_truoc_thongke);

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
        modelCuaHang = (ModelCuaHang) intent.getSerializableExtra("macuahang");
        cuaHang = modelCuaHang.getTenCuaHang();
        txtCuaHang.setText(cuaHang);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        thang = String.valueOf(month);
        nam = String.valueOf(year);
        if (month < 10) {
            thang = "0" + month;
        }
        txtThang.setText(nam + "-" + thang);
        getTongNhanVien();
        getTongLuong();
    }

    public void getTongLuong() {
        txtTongLuong.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        luong = 0;
        nv = 0;
        tongLuong = 0;
        txtTongLuong.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(tongLuong));
        listNvClViec = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference("CaLamViec");
        mData.orderByKey().startAt(cuaHang + "_" + nam + "-" + thang).endAt(cuaHang + "_" + nam + "-" + thang + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (final DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelCaLamViec modelCaLamViec = data.getValue(ModelCaLamViec.class);
                        ModelCaLam modelCaLam = modelCaLamViec.getModelCaLam();
                        listNvClViec = modelCaLamViec.getListNhanVien();
                        luong = modelCaLam.getLuongCaLam();
                        nv = listNvClViec.size();
                        tongLuong += nv * luong;
                    }
                    txtTongLuong.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(tongLuong));
                    txtTongLuong.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    txtTongLuong.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getTongNhanVien() {
        lisNhanVien = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference("NhanVien");
        mData.orderByChild("maCuaHang").equalTo(cuaHang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelNhanVien modelNhanVien = data.getValue(ModelNhanVien.class);
                        lisNhanVien.add(modelNhanVien.getMaCuaHang());
                    }
                    txtTongNv.setText(lisNhanVien.size() + " nhân viên");
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
            getTongLuong();
            return;

        }
        thang = String.valueOf(month);
        nam = String.valueOf(year);
        txtThang.setText(nam + "-" + thang);
        getTongLuong();
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
        getTongLuong();
    }
}
