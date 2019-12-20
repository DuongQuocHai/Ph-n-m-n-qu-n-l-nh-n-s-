package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
    ImageView btnBack, btnBefore, btnAfter;
    int year, month;
    String thang, nam;
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
        txtThang = findViewById(R.id.txt_thang_xemcl);
        btnBefore = findViewById(R.id.btn_truoc_xemcl);
        btnAfter = findViewById(R.id.btn_sau_xemcl);
    }

    public void addEvents() {
        lvCaLamViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(XemCaLamViecActivity.this, ChiTietLichLamActivity.class);
                intent.putExtra("keylichlam", list.get(position));
                startActivity(intent);
            }
        });
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_xemcalam);
        View view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.action_bar_back_xemcalam);
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
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        thang = String.valueOf(month);
        nam = String.valueOf(year);
        if (month < 10) {
            thang = "0" + month;
        }
        txtThang.setText(nam + "-" + thang);
        modelCuaHang = (ModelCuaHang) intent.getSerializableExtra("macuahang");
        cuahang = modelCuaHang.getTenCuaHang();
        txtCuaHang.setText(cuahang);
        getListLichLam();
    }

    public void pressAfter() {
        month++;
        if (month > 12) {
            month = 1;
            year++;
            thang = String.valueOf(month);
            nam = String.valueOf(year);
        }
        if (month<10){
            thang = "0" + month;
            txtThang.setText(nam + "-" + thang);
            getListLichLam();
            return;

        }
        thang = String.valueOf(month);
        nam = String.valueOf(year);
        txtThang.setText(nam + "-" + thang);
        getListLichLam();
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
        getListLichLam();
    }

    public void getListLichLam() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter(XemCaLamViecActivity.this, android.R.layout.simple_list_item_1, list);
        lvCaLamViec.setAdapter(adapter);
        mData = FirebaseDatabase.getInstance().getReference("CaLamViec");
        mData.orderByKey().startAt(cuahang + "_" + nam + "-" + thang).endAt(cuahang + "_" + nam + "-" + thang + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    list.add(data.getKey());
                }
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    Toast.makeText(XemCaLamViecActivity.this, "Chưa có ca làm việc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
