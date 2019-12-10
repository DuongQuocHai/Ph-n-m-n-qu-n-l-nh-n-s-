package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.example.phanmemquanlynhansu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNhanVien extends BaseAdapter {
    Activity context;
    ArrayList<ModelNhanVien> list;

    public AdapterNhanVien(Activity context, ArrayList<ModelNhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class viewHolder{
        CircleImageView imgHinh;
        TextView txtTen,txtCuaHang,txtChucvu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_nhanvien,null);
        viewHolder holder = new viewHolder();
        holder.imgHinh = row.findViewById(R.id.img_nhanvien_itemnv);
        holder.txtTen = row.findViewById(R.id.txt_ten_itemnv);
        holder.txtChucvu = row.findViewById(R.id.txt_chucvu_itemnv);
        holder.txtCuaHang = row.findViewById(R.id.txt_cuahang_itemnv);
        ModelNhanVien modelNhanVien = list.get(position);

        holder.txtTen.setText(modelNhanVien.getTenNv());
        holder.txtChucvu.setText(modelNhanVien.getMaChucVu());
        holder.txtCuaHang.setText(modelNhanVien.getMaCuaHang());
        Picasso.get().load(modelNhanVien.getUrlHinhNv()).into(holder.imgHinh);

        return row;
    }
}