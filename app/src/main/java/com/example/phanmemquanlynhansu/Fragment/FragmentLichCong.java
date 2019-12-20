package com.example.phanmemquanlynhansu.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phanmemquanlynhansu.CaLamActivity;
import com.example.phanmemquanlynhansu.ChiTietLichLamActivity;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCaLamViec;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.example.phanmemquanlynhansu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FragmentLichCong extends Fragment {
    View view;
    ArrayList<String> list;
    ArrayAdapter adapter;
    AnimationDrawable animation;
    ListView lvLichLam;
    ImageView btnBefore, btnAfter;
    TextView txtThang, txtLuong;
    FirebaseUser user;
    ProgressBar progressBar;
    LinearLayout ly_Main;

    int month, year;

    String thang, nam, cuaHang;
    ModelCaLam modelCaLam;
    double luong;


    DatabaseReference mData;
    Button btnRefresh;
    String uidNv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lichcong, container, false);
        addControls();
        readData();
        addEvents();

        return view;
    }

    public void addControls() {
        lvLichLam = view.findViewById(R.id.lv_fraglichlam);
        txtThang = view.findViewById(R.id.txt_thang_fraglichlam);
        btnBefore = view.findViewById(R.id.btn_truoc_fraglichlam);
        btnAfter = view.findViewById(R.id.btn_sau_fraglichlam);
        progressBar = view.findViewById(R.id.pr_fraglichlam);
        ly_Main = view.findViewById(R.id.ly_main_fraglichlam);
        txtLuong = view.findViewById(R.id.txt_luong_fraglichlam);

    }

    public void addEvents() {
        lvLichLam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChiTietLichLamActivity.class);
                intent.putExtra("keylichlam", list.get(position));
                startActivity(intent);
            }
        });
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


    }

    public void readData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            list = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            thang = String.valueOf(month);
            nam = String.valueOf(year);
            if (month < 10) {
                thang = "0" + month;
            }
            txtThang.setText(nam + "-" + thang);
            mData = FirebaseDatabase.getInstance().getReference("NhanVien");
            mData.orderByKey().equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelNhanVien modelNhanVien = data.getValue(ModelNhanVien.class);
                        cuaHang = modelNhanVien.getMaCuaHang();
                    }
                    getListLichLam(user.getUid());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            ly_Main.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }


    public void getListLichLam(final String idUser) {
        ly_Main.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        luong = 0;
        txtLuong.setText(luong + "");
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        lvLichLam.setAdapter(adapter);
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
                                ly_Main.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "Bạn chưa có lịch làm", Toast.LENGTH_SHORT).show();
                    ly_Main.setVisibility(View.VISIBLE);
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
            getListLichLam(user.getUid());
            return;

        }
        thang = String.valueOf(month);
        nam = String.valueOf(year);
        txtThang.setText(nam + "-" + thang);
        getListLichLam(user.getUid());
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
        getListLichLam(user.getUid());
    }
}
