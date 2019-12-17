package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class XemCaLamViecActivity extends AppCompatActivity {
    TextView txtThang, txtCuaHang;
    ListView lvCaLamViec;

    int year, month;
    String cuahang;
    ModelCuaHang modelCuaHang;

    ArrayList<String> list;
    ArrayAdapter adapter;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_ca_lam_viec);
        addControls();
        readData();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    public void addControls() {
        txtCuaHang = findViewById(R.id.txt_cuahang_xemclviec);
        lvCaLamViec = findViewById(R.id.lv_xemclviec);
    }

    public void addEvents() {
        lvCaLamViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(XemCaLamViecActivity.this,ChiTietLichLamActivity.class);
                intent.putExtra("keylichlam",list.get(position));
                startActivity(intent);
            }
        });

    }

    public void readData() {
        Intent intent = getIntent();
        modelCuaHang = (ModelCuaHang) intent.getSerializableExtra("macuahang");
        cuahang = modelCuaHang.getTenCuaHang();
        txtCuaHang.setText(cuahang);
        getListLichLam();
    }

    public void getListLichLam() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter(XemCaLamViecActivity.this, android.R.layout.simple_list_item_1, list);
        lvCaLamViec.setAdapter(adapter);
        mData = FirebaseDatabase.getInstance().getReference("CaLamViec");
        mData.orderByKey().startAt(cuahang).endAt(cuahang+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    list.add(data.getKey());
                }
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    Toast.makeText(XemCaLamViecActivity.this, "Chưa có ca làm việc", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(XemCaLamViecActivity.this, list.size() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
