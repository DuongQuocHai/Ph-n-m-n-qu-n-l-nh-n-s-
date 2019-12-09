package com.example.phanmemquanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

public class SuaCuaHangActivity extends AppCompatActivity {
    Button btnEdit, btnHuy;
    EditText edtEditMaCH, edtEditTenCH, edtEditDiaChi;
    ModelCuaHang modelCuaHang;
    String maCH, tenCH, diaChi, keyId;
    ArrayList<ModelCuaHang> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_cua_hang);
        addControls();
        getData();

    }

    private void addControls() {
        btnEdit = findViewById(R.id.btn_editcuahang);
        btnHuy = findViewById(R.id.btn_huy);
        edtEditMaCH = findViewById(R.id.edt_macuahang_edit);
        edtEditTenCH = findViewById(R.id.edt_tencuahang_edit);
        edtEditDiaChi = findViewById(R.id.edt_diachi_edit);

    }
    private void getData() {
        Intent intent = getIntent();
        modelCuaHang = (ModelCuaHang) intent.getSerializableExtra("ModelCuaHang");
//        Log.e("------",modelCuaHang.getId()+"");
        keyId = modelCuaHang.getId();
        edtEditMaCH.setText(modelCuaHang.getMaCuaHang());
        edtEditTenCH.setText(modelCuaHang.getTenCuaHang());
        edtEditDiaChi.setText(modelCuaHang.getDiaChi());
    }
    public void clickSuaCuaHang(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.btn_editcuahang:
                editCuaHang(keyId);
                break;
            case R.id.btn_huy:
                finish();
                break;
        }
    }
    private void getString() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        maCH = edtEditMaCH.getText().toString();
        tenCH = edtEditTenCH.getText().toString();
        diaChi = edtEditDiaChi.getText().toString();
        keyId = (String) bundle.get("KeyID");
//        keyId = modelCuaHang.getId();
    }

    private void editCuaHang(String uid) {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        getString();
        modelCuaHang = new ModelCuaHang(maCH, tenCH, diaChi);
        mData.child("CuaHang").child(uid).setValue(modelCuaHang);
        finish();
    }
}
