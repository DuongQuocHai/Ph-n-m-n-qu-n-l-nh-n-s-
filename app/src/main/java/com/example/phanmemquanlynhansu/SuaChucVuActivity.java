package com.example.phanmemquanlynhansu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phanmemquanlynhansu.Model.ModelChucVu;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

public class SuaChucVuActivity extends AppCompatActivity {
    Button btnEdit, btnHuy;
    EditText edtEditMaCV, edtEditTenCV, edtEditGhiChu;
    ModelChucVu modelChucVu;
    String maCV, tenCV, ghiChu, keyId;
    TextView txtXoa;
    ArrayList<ModelChucVu> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_chuc_vu);
        addControls();
        getData();

        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChucVu(keyId);
            }
        });

    }

    private void addControls() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_sua_chucvu);
        View view = getSupportActionBar().getCustomView();

        edtEditMaCV = findViewById(R.id.edt_machucvu_edit);
        edtEditTenCV = findViewById(R.id.edt_tenchucvu_edit);
        edtEditGhiChu = findViewById(R.id.edt_ghichu_edit);
        txtXoa = findViewById(R.id.txt_xoa_chucvu);


    }

    private void getData() {
        Intent intent = getIntent();
        modelChucVu = (ModelChucVu) intent.getSerializableExtra("ModelChucVu");
        keyId = modelChucVu.getId();
        edtEditMaCV.setText(modelChucVu.getMaChucVu());
        edtEditTenCV.setText(modelChucVu.getTenChucVu());
        edtEditGhiChu.setText(modelChucVu.getGhiChu());

    }

    public void clickSuaChucVu(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.action_bar_sua_chucvu:
                if (batLoi()) {
                    editChucVu(keyId);
                }
                break;
            case R.id.action_bar_back_sua_chucvu:
                finish();
                break;
        }
    }

    private boolean batLoi() {
        if (edtEditMaCV.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập mã chức vụ!", Toast.LENGTH_SHORT).show();
        } else if (edtEditTenCV.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên chức vụ", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void deleteChucVu(String keyId) {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("ChucVu").child(keyId).removeValue();
        finish();
    }

    private void getString() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        maCV = edtEditMaCV.getText().toString();
        tenCV = edtEditTenCV.getText().toString();
        ghiChu = edtEditGhiChu.getText().toString();
        keyId = (String) bundle.get("KeyID");
//        keyId = modelCuaHang.getId();
    }

    private void editChucVu(String uid) {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        getString();
        modelChucVu = new ModelChucVu(maCV, tenCV, ghiChu);
        mData.child("ChucVu").child(uid).setValue(modelChucVu);
        finish();
    }
}
