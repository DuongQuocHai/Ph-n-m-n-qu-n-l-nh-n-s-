package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Adapter.AdapterThemPhanCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLamViec;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietLichLamActivity extends AppCompatActivity {
    TextView txtCuaHang, txtNgay, txtCaLam, btnXoa, txtLuongCl, txtTgcl;
    ProgressBar progressBar;
    LinearLayout ly_main;

    ListView lvChiTiet;
    ImageView btnBack;
    String key;
    DatabaseReference mData;


    ArrayList<ModelNhanVien> listNhanVien;
    AdapterThemPhanCaLam adapter;
//    ArrayAdapter adapter;

    ModelCaLam modelCaLam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_lam);
        getData();
        addControls();
        addEvents();
    }

    public void addControls() {
        txtCaLam = findViewById(R.id.txt_calam_chitietcl);
        txtCuaHang = findViewById(R.id.txt_cuahang_chitietcl);
        txtNgay = findViewById(R.id.txt_ngay_chitietcl);
        lvChiTiet = findViewById(R.id.lv_chitietcl);
        btnXoa = findViewById(R.id.btn_xoa_chitietcl);
        txtLuongCl = findViewById(R.id.txt_luongcalam_chitietcl);
        txtTgcl = findViewById(R.id.txt_tgcl_chitietcl);
        progressBar = findViewById(R.id.pr_chitietcl);
        ly_main= findViewById(R.id.ly_main_chitietcl);
    }

    public void addEvents() {
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanXoa();
            }
        });
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_chitiet_lichlam);
        View view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.action_bar_back_chitiet_lichlam);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void xacNhanXoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xoá?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xoaLichLam(key);
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void getData() {
        Intent intent = getIntent();
        listNhanVien = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mData = FirebaseDatabase.getInstance().getReference("NhanVien");
            mData.orderByKey().equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelNhanVien modelNhanVien = data.getValue(ModelNhanVien.class);
                        if (!modelNhanVien.getMaChucVu().equals("Quản lý")) {
                            btnXoa.setVisibility(View.GONE);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        key = intent.getStringExtra("keylichlam");
        mData = FirebaseDatabase.getInstance().getReference("CaLamViec");
        mData.orderByKey().equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNhanVien.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ModelCaLamViec modelCaLamViec = data.getValue(ModelCaLamViec.class);
                    listNhanVien = modelCaLamViec.getListNhanVien();
                    modelCaLam = modelCaLamViec.getModelCaLam();
                    txtCaLam.setText(modelCaLam.getTenCaLam() + ": ");
                    String tgcl = modelCaLam.getTgBatDauCaLam() + " - " + modelCaLam.getTgKetThucCaLam();
                    txtTgcl.setText(tgcl);
                    double luong = modelCaLam.getLuongCaLam();
                    txtLuongCl.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(luong));
                    txtCuaHang.setText(modelCaLamViec.getCuaHang());
                    txtNgay.setText(modelCaLamViec.getNgay());
                }
                adapter = new AdapterThemPhanCaLam(ChiTietLichLamActivity.this, listNhanVien);
                lvChiTiet.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                ly_main.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void xoaLichLam(String key) {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("CaLamViec").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ChiTietLichLamActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChiTietLichLamActivity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
