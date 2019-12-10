package com.example.phanmemquanlynhansu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phanmemquanlynhansu.CaLamActivity;
import com.example.phanmemquanlynhansu.ChucVuActivity;
import com.example.phanmemquanlynhansu.CuaHangActivity;
import com.example.phanmemquanlynhansu.NhanVienActivity;
import com.example.phanmemquanlynhansu.R;

public class FragmentThem extends Fragment {
    View view;
    LinearLayout btnNhanVien,btnCaLam, btnCuaHang, btnChucVu;
    LinearLayout nhanVien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_them,container,false);
        addControl();
        addEvent();





        return view;
    }
    public void addControl(){
        btnNhanVien = view.findViewById(R.id.btn_nhanvien_fgthem);
        btnCaLam = view.findViewById(R.id.btn_calam_fgthem);
        btnCuaHang = view.findViewById(R.id.btn_cuahang_fgthem);
        btnChucVu = view.findViewById(R.id.btn_chuc_vu_fgthem);

    }
    public void addEvent(){
        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NhanVienActivity.class);
                startActivity(intent);
            }
        });
        btnCaLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CaLamActivity.class);
                startActivity(intent);
            }
        });
        btnCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CuaHangActivity.class);
                startActivity(intent);
            }
        });
        btnChucVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChucVuActivity.class);
                startActivity(intent);
            }
        });
    }



}
