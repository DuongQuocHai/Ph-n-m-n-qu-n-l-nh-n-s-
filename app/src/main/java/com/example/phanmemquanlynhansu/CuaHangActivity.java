package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.phanmemquanlynhansu.Adapter.AdapterCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

public class CuaHangActivity extends AppCompatActivity {
    ListView lvCuaHang;
    EditText  edtTenCuaHang, edtDiaChi, edtMaCuaHang;
    AdapterCuaHang adapterCuaHang;
    ModelCuaHang modelCuaHang;
    DatabaseReference mData;
    ArrayList<ModelCuaHang> list;
    String maCH, tenCH,diaChiCH, id;
    Dialog dialog;
    View view;
    ProgressBar progressBarCuaHang;
    AnimationDrawable animation;
    ImageView ivLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang);
        addControls();
        addEvents();
        reaData();
    }

    private void addControls() {
        lvCuaHang = findViewById(R.id.lv_cuahang);
        list = new ArrayList<>();
        adapterCuaHang = new AdapterCuaHang(CuaHangActivity.this, list);
        lvCuaHang.setAdapter(adapterCuaHang);
        ivLoading = findViewById(R.id.iv_loading);
        ivLoading.setBackgroundResource(R.drawable.loading);
        animation = (AnimationDrawable) ivLoading.getBackground();
        animation.start();
    }

    private void addEvents() {
        lvCuaHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CuaHangActivity.this, SuaCuaHangActivity.class);
                modelCuaHang = list.get(position);
                intent.putExtra("ModelCuaHang", modelCuaHang);
                startActivity(intent);
            }
        });
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_cuahang);

    }
    public void clickCuaHang(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.action_bar_add_cuahang:
                showDialogThemCaLam();
                break;
            case R.id.action_bar_back_cuahang:
                break;
            case R.id.btn_them_cuahang1:
                addCuaHang();
                break;
            case R.id.btn_huy_cuahang1:
                dialog.dismiss();
                break;
        }
    }

    private void showDialogThemCaLam() {
        dialog = new Dialog(CuaHangActivity.this);
        dialog.setContentView(R.layout.dialog_themcuahang);

        edtMaCuaHang = dialog.findViewById(R.id.edt_macuahang);
        edtTenCuaHang = dialog.findViewById(R.id.edt_tencuahang);
        edtDiaChi = dialog.findViewById(R.id.edt_diachicuahang);

        dialog.show();
    }
    public void getString() {
        maCH = edtMaCuaHang.getText().toString();
        tenCH = edtTenCuaHang.getText().toString();
        diaChiCH = edtDiaChi.getText().toString();

    }
    public void addCuaHang() {
        mData = FirebaseDatabase.getInstance().getReference();
        getString();
        modelCuaHang = new ModelCuaHang(maCH, tenCH, diaChiCH);
        mData.child("CuaHang").push().setValue(modelCuaHang);
        dialog.dismiss();
    }
    private void reaData() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("CuaHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ModelCuaHang modelCuaHang = dataSnapshot.getValue(ModelCuaHang.class);
                    id = dataSnapshot.getKey();
                modelCuaHang.setId(id);
                list.add(modelCuaHang);
                adapterCuaHang.notifyDataSetChanged();

                ivLoading.setVisibility(View.GONE);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
