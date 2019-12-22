package com.example.phanmemquanlynhansu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phanmemquanlynhansu.Model.ModelChucVu;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;

public class SuaChucVuActivity extends AppCompatActivity {
    Button btnEdit, btnHuy;
    EditText edtEditMaCV, edtEditTenCV, edtEditGhiChu;
    ModelChucVu modelChucVu;
    String maCV, tenCV, ghiChu, keyId;
    TextView txtXoa;
    ArrayList<ModelChucVu> list;
    DatabaseReference mData;
    boolean check = true;
    String tencv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_chuc_vu);
        addControls();
        getData();
        checkChucVu();

        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanXoa();
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

    public void checkChucVu() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("NhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelNhanVien modelNhanVien = data.getValue(ModelNhanVien.class);
                        tencv = modelNhanVien.getMaChucVu();
                        if (tencv.equals(modelChucVu.getTenChucVu())) {
                            check = false;
                            break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void xacNhanXoa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xoá?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteChucVu(keyId);
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private boolean batLoi() {
        if (edtEditMaCV.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập mã chức vụ!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtEditTenCV.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên chức vụ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void deleteChucVu(String keyId) {
        if (check) {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("ChucVu").child(keyId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SuaChucVuActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SuaChucVuActivity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
            }
        });
        } else
            Toast.makeText(this, "Hãy xóa nhân viên trước khi xóa chức vụ", Toast.LENGTH_LONG).show();
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
        mData = FirebaseDatabase.getInstance().getReference();
        getString();
        modelChucVu = new ModelChucVu(maCV, tenCV, ghiChu);
        mData.child("ChucVu").child(uid).setValue(modelChucVu).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SuaChucVuActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SuaChucVuActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
