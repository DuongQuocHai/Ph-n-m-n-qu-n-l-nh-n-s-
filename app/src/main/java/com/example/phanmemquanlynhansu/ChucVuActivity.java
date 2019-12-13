package com.example.phanmemquanlynhansu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phanmemquanlynhansu.Adapter.AdapterChucVu;
import com.example.phanmemquanlynhansu.Adapter.AdapterCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelChucVu;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

public class ChucVuActivity extends AppCompatActivity {
    ListView lvChucVu;
    EditText edtTenChucVu, edtMaChucVu, edtGhiChu;
    AdapterChucVu adapterChucVu;
    ModelChucVu modelChucVu;
    DatabaseReference mData;
    ArrayList<ModelChucVu> list;
    String maCV, tenCV, ghiChu, id;
    Dialog dialog;
    View view;
    AnimationDrawable animation;
    ImageView ivLoading;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_vu);
        addControl();
        addEnvents();
        reaData();
    }


    private void addControl() {
        lvChucVu = findViewById(R.id.lv_chucvu);
        list = new ArrayList<>();
        adapterChucVu = new AdapterChucVu(ChucVuActivity.this, list);
        lvChucVu.setAdapter(adapterChucVu);
    }

    private void addEnvents() {
        lvChucVu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChucVuActivity.this, SuaChucVuActivity.class);
                modelChucVu = list.get(position);
                intent.putExtra("ModelChucVu", modelChucVu);
                startActivity(intent);
            }
        });
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_chucvu);

        ivLoading = findViewById(R.id.iv_loading);
        ivLoading.setBackgroundResource(R.drawable.loading);
        animation = (AnimationDrawable) ivLoading.getBackground();
        animation.start();
    }

    public void clickChucVu(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.action_bar_add_chucvu:
                showDialogThemChucVu();
                break;
            case R.id.action_bar_back_chucvu:
                break;
            case R.id.btn_them_chucvu:
                if (batLoi()) {
                    addChucVu();
                }
                break;
            case R.id.btn_huy_chucvu:
                dialog.dismiss();
                break;
        }
    }
    public boolean batLoi() {
        if (edtMaChucVu.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập mã chức vụ!", Toast.LENGTH_SHORT).show();
        } else if (edtTenChucVu.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên chức vụ", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void showDialogThemChucVu() {
        dialog = new Dialog(ChucVuActivity.this);
        dialog.setContentView(R.layout.dialog_themchucvu);

        edtMaChucVu = dialog.findViewById(R.id.edt_machucvu);
        edtTenChucVu = dialog.findViewById(R.id.edt_tenchucvu);
        edtGhiChu = dialog.findViewById(R.id.edt_ghichu);

        dialog.show();
    }

    public void getString() {
        maCV = edtMaChucVu.getText().toString();
        tenCV = edtTenChucVu.getText().toString();
        ghiChu = edtGhiChu.getText().toString();

    }

    public void addChucVu() {
        mData = FirebaseDatabase.getInstance().getReference();
        getString();
        modelChucVu = new ModelChucVu(maCV, tenCV, ghiChu);
        mData.child("ChucVu").push().setValue(modelChucVu);
        dialog.dismiss();
    }

    private void reaData() {
        mData = FirebaseDatabase.getInstance().getReference();
        list.clear();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ModelChucVu modelChucVu = dataSnapshot.getValue(ModelChucVu.class);
                id = dataSnapshot.getKey();
                modelChucVu.setId(id);
                list.add(modelChucVu);
                adapterChucVu.notifyDataSetChanged();
                ivLoading.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.clear();
                mData.child("ChucVu").addChildEventListener(childEventListener);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                mData.child("ChucVu").addChildEventListener(childEventListener);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mData.child("ChucVu").addChildEventListener(childEventListener);
    }
}
