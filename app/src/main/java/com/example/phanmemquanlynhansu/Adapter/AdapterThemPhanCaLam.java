package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.example.phanmemquanlynhansu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterThemPhanCaLam extends BaseAdapter {
    Activity context;
    ArrayList<ModelNhanVien> list;

    public AdapterThemPhanCaLam(Activity context, ArrayList<ModelNhanVien> list) {
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
    private class viewHolder{
        CircleImageView imgHinh;
        TextView txtTen,txtChucvu;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_themphancl,null);
        viewHolder holder = new viewHolder();
        holder.imgHinh = row.findViewById(R.id.img_nhanvien_itemphancl);
        holder.txtTen = row.findViewById(R.id.txt_ten_itemphancl);
        holder.txtChucvu = row.findViewById(R.id.txt_chucvu_itemphancl);
        ModelNhanVien modelNhanVien = list.get(position);

        holder.txtTen.setText(modelNhanVien.getTenNv());
        holder.txtChucvu.setText(modelNhanVien.getMaChucVu());
        Picasso.get().load(modelNhanVien.getUrlHinhNv()).into(holder.imgHinh);

        return row;
    }
}
