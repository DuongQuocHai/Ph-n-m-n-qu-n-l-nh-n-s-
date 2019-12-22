package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Adapter.AdapterCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;

public class CuaHangActivity extends AppCompatActivity {
    ListView lvCuaHang;
    EditText edtTenCuaHang, edtDiaChi, edtMaCuaHang;
    AdapterCuaHang adapterCuaHang;
    ModelCuaHang modelCuaHang;
    DatabaseReference mData;
    ArrayList<ModelCuaHang> list;
    String maCH, tenCH, diaChiCH, id;
    Dialog dialog;
    View view;
    ProgressBar progressBarCuaHang;
    AnimationDrawable animation;
    ImageView ivLoading, btnBack;


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
                Intent intent = getIntent();
                String status = intent.getStringExtra("phancalam");
                if (status != null) {
                    if (status.equals("1")) {
                        intent = new Intent(CuaHangActivity.this, PhanCaLamActivity.class);
                        intent.putExtra("macuahang", list.get(position));
                        startActivity(intent);
                    } else if (status.equals("2")) {
                        intent = new Intent(CuaHangActivity.this, XemCaLamViecActivity.class);
                        intent.putExtra("macuahang", list.get(position));
                        startActivity(intent);
                    } else if (status.equals("3")) {
                        intent = new Intent(CuaHangActivity.this, ThongKeActivity.class);
                        intent.putExtra("macuahang", list.get(position));
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(CuaHangActivity.this, SuaCuaHangActivity.class);
                    modelCuaHang = list.get(position);
                    intent.putExtra("ModelCuaHang", modelCuaHang);
                    startActivity(intent);
                }
            }
        });
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_cuahang);
        view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.action_bar_back_cuahang);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clickCuaHang(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.action_bar_add_cuahang:
                showDialogThemCaLam();
                break;
            case R.id.action_bar_back_cuahang:
                break;
            case R.id.btn_them_cuahang1:
                if (batLoi()) {
                    addCuaHang();
                }
                break;
            case R.id.btn_huy_cuahang1:
                dialog.dismiss();
                break;
        }
    }



    public boolean batLoi() {
        if (edtMaCuaHang.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập mã cửa hàng!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtTenCuaHang.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên cửa hàng", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtDiaChi.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showDialogThemCaLam() {
        dialog = new Dialog(CuaHangActivity.this);
        dialog.setCancelable(false);
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
        mData.child("CuaHang").push().setValue(modelCuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CuaHangActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                edtMaCuaHang.setText("");
                edtTenCuaHang.setText("");
                edtDiaChi.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CuaHangActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();
    }

    public void reaData() {
        mData = FirebaseDatabase.getInstance().getReference("CuaHang");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ModelCuaHang modelCuaHang = data.getValue(ModelCuaHang.class);
                    modelCuaHang.setId(data.getKey());
                    list.add(modelCuaHang);
                }
                if (list.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CuaHangActivity.this);
                    builder.setIcon(R.drawable.iconnotification);
                    builder.setCancelable(false);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Hiện tại chưa có của hàng, hãy thêm cửa hàng");
                    builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showDialogThemCaLam();
                        }
                    });
                    builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.show();
                }
                adapterCuaHang.notifyDataSetChanged();
                ivLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Tải dữ liệu thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }
}
