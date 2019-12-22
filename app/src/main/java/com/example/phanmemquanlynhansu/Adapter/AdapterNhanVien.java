package com.example.phanmemquanlynhansu.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

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
        TextView txtTen,txtCuaHang,txtChucvu,txtStt;
        ImageView btnGoi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_nhanvien,null);
        final viewHolder holder = new viewHolder();
        holder.imgHinh = row.findViewById(R.id.img_nhanvien_itemnv);
        holder.txtTen = row.findViewById(R.id.txt_ten_itemnv);
        holder.txtChucvu = row.findViewById(R.id.txt_chucvu_itemnv);
        holder.txtCuaHang = row.findViewById(R.id.txt_cuahang_itemnv);
        holder.txtStt = row.findViewById(R.id.txt_stt_itemnv);
        holder.btnGoi= row.findViewById(R.id.btn_call_itemnv);
        final ModelNhanVien modelNhanVien = list.get(position);

        holder.txtTen.setText(modelNhanVien.getTenNv());
        holder.txtChucvu.setText(modelNhanVien.getMaChucVu());
        holder.txtCuaHang.setText(modelNhanVien.getMaCuaHang());
        holder.txtStt.setText((position+1)+"");

        Picasso.get().load(modelNhanVien.getUrlHinhNv()).into(holder.imgHinh);

        holder.btnGoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogcall);
                TextView txtdgcalten = dialog.findViewById(R.id.txtdgcallten);
                final EditText edtdgcallsdt = dialog.findViewById(R.id.edtdgcallsdt);
                Button btndgcallgoi = dialog.findViewById(R.id.btndgcallgoi);
                Button btndgcallback = dialog.findViewById(R.id.btndgcallback);

                txtdgcalten.setText(holder.txtTen.getText().toString());
                edtdgcallsdt.setText(modelNhanVien.getSdtNv());

                btndgcallgoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkPermission(Manifest.permission.CALL_PHONE)) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + edtdgcallsdt.getText().toString()));
                            context.startActivity(intent);
                        }else {
                            Toast.makeText(context,"Permission call phone",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btndgcallback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return row;
    }
    private boolean checkPermission(String permission){
        return ContextCompat.checkSelfPermission(context, permission)== PackageManager.PERMISSION_GRANTED;
    }
}