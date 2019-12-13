package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Adapter.AdapterCaLam;
import com.example.phanmemquanlynhansu.Adapter.AdapterNhanVien;
import com.example.phanmemquanlynhansu.Fragment.FragmentThem;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {
    ListView lvNhanVien;
    ImageView btnBack, btnThem;
    ArrayList<ModelNhanVien> list;
    AdapterNhanVien adapterNhanVien;
    DatabaseReference mData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        addControls();
        readData();
        addEvents();


    }

    public void addControls() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_nhan_vien);
        View view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.btn_back_nhanvien);
        btnThem = view.findViewById(R.id.btn_them_nhanvien);

        lvNhanVien = (ListView) findViewById(R.id.lv_nhanvien);
        list = new ArrayList<>();
        adapterNhanVien = new AdapterNhanVien(NhanVienActivity.this, list);
        Log.e("fffffff", list.size() + "");
        lvNhanVien.setAdapter(adapterNhanVien);

    }

    public void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhanVienActivity.this, ThemNhanVienActivity.class);
                startActivity(intent);
            }
        });
        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NhanVienActivity.this,SuaNhanVienActivity.class);
                intent.putExtra("ModelNhanVien",list.get(position));
                startActivity(intent);
            }
        });

    }

    public void readData() {
//        mData = FirebaseDatabase.getInstance().getReference("NhanVien");
//        mData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    ModelNhanVien modelNhanVien = data.getValue(ModelNhanVien.class);
//                    modelNhanVien.setIdNv(data.getKey());
//                    list.add(modelNhanVien);
//                    Log.e("vvvvv",list.size()+"");
//                }
//                adapterNhanVien.notifyDataSetChanged();
//                Toast.makeText(getApplicationContext(), "Load Data Success", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        mData = FirebaseDatabase.getInstance().getReference();
        list.clear();
        mData.child("NhanVien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ModelNhanVien modelNhanVien = dataSnapshot.getValue(ModelNhanVien.class);
                modelNhanVien.setIdNv(dataSnapshot.getKey());
                list.add(modelNhanVien);
                Log.e("ttttt", modelNhanVien.getMaChucVu());
                adapterNhanVien.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
