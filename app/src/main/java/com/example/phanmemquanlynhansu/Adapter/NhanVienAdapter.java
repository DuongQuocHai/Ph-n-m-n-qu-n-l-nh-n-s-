package com.example.phanmemquanlynhansu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.phanmemquanlynhansu.Model.ModelNhanVien;

import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<ModelNhanVien> {
    private Context context;
    private int resource;
    private List<ModelNhanVien> object;

    public NhanVienAdapter(Context context, int resource, List<ModelNhanVien> objects, List<ModelNhanVien> object) {
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