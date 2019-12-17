package com.example.phanmemquanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class PhanCaLamActivity extends AppCompatActivity {
    ImageView btnBefore, btnAfter;
    TextView txtNgaydt, txtNgayct, txtTuan, txtCuaHang;
    ListView lvNgay;
    Calendar c;

    int year, week, totalWeek, wee = 1;

    ArrayList<String> list;
    ArrayAdapter adapter;

    ModelCuaHang modelCuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_ca_lam);
        addControls();
        getData();
        getDataList();
        addEvents();

    }

    public void addControls() {
        btnBefore = findViewById(R.id.btn_truoc_phancl);
        btnAfter = findViewById(R.id.btn_sau_phancl);
        txtTuan = findViewById(R.id.txt_tuan_phancl);
        txtNgaydt = findViewById(R.id.txt_ngaydt_phancl);
        txtNgayct = findViewById(R.id.txt_ngayct_phancl);
        txtCuaHang = findViewById(R.id.txt_cuahang_phancl);
        lvNgay = findViewById(R.id.lv_phancl);

    }

    public void addEvents() {
        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressAfter();
            }
        });
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressBefore();
            }
        });
        lvNgay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhanCaLamActivity.this, CaLamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ngay",list.get(position));
                bundle.putString("cuahang",txtCuaHang.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void getData() {
        Intent intent = getIntent();
        modelCuaHang = (ModelCuaHang) intent.getSerializableExtra("macuahang");
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        week = c.get(Calendar.WEEK_OF_YEAR);
        totalWeek = getTotalWeeksInYear(year);
        txtTuan.setText("T" + week + "-" + year);
        txtCuaHang.setText(modelCuaHang.getTenCuaHang());
    }

    public void getDataList() {
        list = new ArrayList<>();
        list = getDaysOfWeek(wee);
        txtNgaydt.setText(list.get(0).substring(list.get(0).lastIndexOf(",") + 2));
        txtNgayct.setText(list.get(6).substring(list.get(0).lastIndexOf(",") + 2));
        adapter = new ArrayAdapter(PhanCaLamActivity.this, android.R.layout.simple_list_item_1, list);
        lvNgay.setAdapter(adapter);
    }

    public ArrayList<String> getDaysOfWeek(int week) {
        c = Calendar.getInstance();
        ArrayList<String> listt = new ArrayList<>();
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.add(Calendar.DATE, week * 7);
        // Print dates of the current week starting on Monday
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault());
        for (int i = 0; i < 7; i++) {
            listt.add(sdf.format(c.getTime()));
            c.add(Calendar.DAY_OF_WEEK, 1);
        }
        return listt;
    }

    public void pressAfter() {
        week++;
        wee++;
        if (week > totalWeek) {
            week = 1;
            year++;
        }
        txtTuan.setText("T" + week + "-" + year);
        getDataList();
    }

    public void pressBefore() {
        week--;
        wee--;
        if (week == 0) {
            year--;
            week = totalWeek;
        }
        txtTuan.setText("T" + week + "-" + year);
        getDataList();
    }

    private int getTotalWeeksInYear(int year) {
        Calendar mCalendar = new GregorianCalendar(TimeZone.getDefault());
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Workaround
        mCalendar.set(year,
                Calendar.DECEMBER,
                31);
        int totalDaysInYear = mCalendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(totalDaysInYear);
        int totalWeeks = totalDaysInYear / 7;
        return totalWeeks;
    }

}
