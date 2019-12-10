package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Model.ModelChucVu;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.R;

import java.util.ArrayList;

public class AdapterChucVu extends BaseAdapter {
    Activity context;
    ArrayList<ModelChucVu> list;

    public AdapterChucVu(Activity context, ArrayList<ModelChucVu> list) {
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
        View row = inflater.inflate(R.layout.item_chucvu, null);
        TextView txtMaChucVu = row.findViewById(R.id.txt_ma_itchucvu);
        TextView txtTenChucVu = row.findViewById(R.id.txt_ten_itchucvu);
        TextView txtGhiChu = row.findViewById(R.id.txt_ghichu);


        ModelChucVu modelChucVu = list.get(position);
        txtMaChucVu.setText(modelChucVu.getMaChucVu());
        txtTenChucVu.setText(modelChucVu.getTenChucVu());
        txtGhiChu.setText(modelChucVu.getGhiChu());

        return row;
    }
}
