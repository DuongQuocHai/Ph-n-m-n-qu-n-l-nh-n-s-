package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Model.NhanVien;
import com.example.phanmemquanlynhansu.R;

import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private int resource;
    private List<NhanVien> object;

    public NhanVienAdapter(Context context, int resource, List<NhanVien> objects, List<NhanVien> object) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.object = object;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}