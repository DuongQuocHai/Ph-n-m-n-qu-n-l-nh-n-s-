package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Adapter.AdapterCaLam;
import com.example.phanmemquanlynhansu.Adapter.AdapterNhanVien;
import com.example.phanmemquanlynhansu.Adapter.AdapterThemPhanCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLamViec;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThemPhanCaLamActivity extends AppCompatActivity {
    TextView txtNgay, txtCuaHang, txtCaLam, txtThem;
    ListView lvPhanCl;
    DatabaseReference mData;
    ArrayList<ModelNhanVien> listNhanVien;
    AdapterThemPhanCaLam adapter;
    Button btnThem, btnHuy, btnDsNhanVien, btnLammoi;
    ImageView btnBack;
    ModelCaLam modelCaLam;
    ModelNhanVien modelNhanVien;
    Intent intent;
    Bundle bundle;
    String cuahang, ngay,calam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phan_ca_lam);
        addControls();
        addEvents();
        readData();
    }

    public void addControls() {
        txtNgay = findViewById(R.id.txt_ngay_themphancl);
        lvPhanCl = findViewById(R.id.lv_themphancl);
        txtCaLam = findViewById(R.id.txt_calam_themphancl);
        txtCuaHang = findViewById(R.id.txt_cuahang_themphancl);
        btnDsNhanVien = findViewById(R.id.btn_dsnhanvien_themphancl);
        btnLammoi = findViewById(R.id.btn_lammoi_themphancl);
    }

    public void addEvents() {
        btnDsNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemPhanCaLamActivity.this, NhanVienActivity.class);
                startActivity(intent);
            }
        });
        lvPhanCl.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listNhanVien.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        btnLammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_them_phanlich);
        View view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.action_bar_back_them_phanlich);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtThem = view.findViewById(R.id.action_bar_them_phanlich);
        txtThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCaLamViec();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    public void readData() {
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            cuahang = bundle.getString("cuahang");
            ngay = bundle.getString("ngay");
            modelCaLam = (ModelCaLam) bundle.getSerializable("modelcl");
            txtCuaHang.setText(cuahang);
            txtNgay.setText(ngay);
            calam = modelCaLam.getTenCaLam();
            txtCaLam.setText(calam);
        }
        listNhanVien = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference("NhanVien");
        mData.orderByChild("maCuaHang").equalTo(txtCuaHang.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNhanVien.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    modelNhanVien = data.getValue(ModelNhanVien.class);
                    modelNhanVien.setIdNv(data.getKey());
                    listNhanVien.add(modelNhanVien);
                }
                adapter = new AdapterThemPhanCaLam(ThemPhanCaLamActivity.this, listNhanVien);
                lvPhanCl.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void addCaLamViec() {
        mData = FirebaseDatabase.getInstance().getReference("CaLamViec");
        String keyNgay = ngay.substring(ngay.lastIndexOf(",") + 2);
        String uid = cuahang+"_"+keyNgay+"_"+calam;
        ModelCaLamViec modelCaLamViec = new ModelCaLamViec(ngay, cuahang, modelCaLam, listNhanVien);
        mData.child(uid).setValue(modelCaLamViec).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Thêm thành công!",Toast.LENGTH_LONG).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Thêm thất bại!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
