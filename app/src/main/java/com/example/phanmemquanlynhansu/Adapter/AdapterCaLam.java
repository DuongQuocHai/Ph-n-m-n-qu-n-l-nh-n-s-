package com.example.phanmemquanlynhansu.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.R;

import java.util.ArrayList;

public class AdapterCaLam extends BaseAdapter {
    Activity context;
    ArrayList<ModelCaLam> list;

    public AdapterCaLam(Activity context, ArrayList<ModelCaLam> list) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_calam, null);

        TextView txtTenCL = row.findViewById(R.id.txt_tenca_itcalam);
        TextView txtGioLam = row.findViewById(R.id.txt_tgca_itcalam);

        ModelCaLam modelCaLam =list.get(position);
        txtTenCL.setText(modelCaLam.getTenCaLam());
        txtGioLam.setText("từ "+ modelCaLam.getTgBatDauCaLam()+" đến "+modelCaLam.getTgKetThucCaLam());


        return row;
    }
}
