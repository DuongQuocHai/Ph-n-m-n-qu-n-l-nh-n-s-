package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.CuaHangActivity;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCuaHang extends BaseAdapter {
    Activity context;
    ArrayList<ModelCuaHang> list;

    public AdapterCuaHang(Activity context, ArrayList<ModelCuaHang> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_cuahang, null);
        TextView txtMaCuaHang = row.findViewById(R.id.txt_ma_itcuahang);
        TextView txtTenCuaHang = row.findViewById(R.id.txt_ten_itcuahang);
        TextView txtDiaChiCuaHang = row.findViewById(R.id.txt_diachi_itcuahang);
        ImageView ivDeleteCH = row.findViewById(R.id.iv_xoacuahang);


        ModelCuaHang modelCuaHang = list.get(position);

        txtMaCuaHang.setText(modelCuaHang.getMaCuaHang());
        txtTenCuaHang.setText(modelCuaHang.getTenCuaHang());
        txtDiaChiCuaHang.setText(modelCuaHang.getDiaChi());
        final String keyId = modelCuaHang.getId();


        ivDeleteCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                mData.child("CuaHang").child(keyId).removeValue();
            }
        });
        return row;
    }
}
