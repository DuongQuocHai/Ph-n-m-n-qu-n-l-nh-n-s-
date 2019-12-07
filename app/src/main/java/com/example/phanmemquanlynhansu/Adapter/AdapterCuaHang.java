package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.CuaHangActivity;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.R;

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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_cuahang, null);

        TextView txtMaCuaHang = row.findViewById(R.id.txt_ma_itcuahang);
        TextView txtTenCuaHang = row.findViewById(R.id.txt_ten_itcuahang);
        TextView txtDiaChiCuaHang = row.findViewById(R.id.txt_diachi_itcuahang);

        ModelCuaHang modelCuaHang = list.get(position);
        txtMaCuaHang.setText(modelCuaHang.getMaCuaHang());
        txtTenCuaHang.setText(modelCuaHang.getTenCuaHang());
        txtDiaChiCuaHang.setText(modelCuaHang.getDiaChi());
        return row;
    }
}
