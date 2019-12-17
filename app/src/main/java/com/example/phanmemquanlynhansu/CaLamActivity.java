package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Adapter.AdapterCaLam;
import com.example.phanmemquanlynhansu.Function.Function;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class CaLamActivity extends AppCompatActivity {
    ListView lvCaLam;
    EditText edttenCaLam, edtLuong1Gio;
    TextView txtTongGioCl, txtLuong1Ca, txtMaCaLam, txtGioBd, txtGioKt, txtCuaHang, txtNgay;
    Button btnGioBd, btnGioKt, btnHuy;
    String maCl, tenCl, tgBdCl, tgKtCl, tongGioCl, luong1Gio, luong1Ca;
    DatabaseReference mData;
    Function function = new Function();
    ArrayList<ModelCaLam> list;
    AdapterCaLam adapterCaLam;
    View view;
    ImageView ivLoading;
    AnimationDrawable animation;

    Dialog dialog;

    Intent intent;
    Bundle bundle;
    int status = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_lam);
        addControl();
        addEnvents();
        readData();


    }

    public void addEnvents() {
        lvCaLam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (bundle != null) {
                    status++;
                    Intent intent = new Intent(CaLamActivity.this, ThemPhanCaLamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cuahang", txtCuaHang.getText().toString());
                    bundle.putString("ngay", txtNgay.getText().toString());
                    bundle.putSerializable("modelcl", list.get(position));
                    intent.putExtras(bundle);

                    startActivity(intent);
                    if (status == list.size()) {
                        finish();
                    }
                } else {
                    Intent intent = new Intent(CaLamActivity.this, SuaCaLamActivity.class);
                    intent.putExtra("ModelCalam", list.get(position));
                    startActivity(intent);
                }
            }
        });

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_calam);
        view = getSupportActionBar().getCustomView();

        ivLoading = findViewById(R.id.iv_loading);
        ivLoading.setBackgroundResource(R.drawable.loading);
        animation = (AnimationDrawable) ivLoading.getBackground();
        animation.start();
    }

    public void addControl() {
        lvCaLam = findViewById(R.id.lvcalam);
        txtCuaHang = findViewById(R.id.txt_cuahang_calam);
        txtNgay = findViewById(R.id.txt_ngay_calam);
        list = new ArrayList<>();
        adapterCaLam = new AdapterCaLam(CaLamActivity.this, list);
        lvCaLam.setAdapter(adapterCaLam);
    }

    public void clickCaLam(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.action_bar_add_calam:
                showDialogThemCaLam();
                break;
            case R.id.action_bar_back_calam:
                break;
            case R.id.btn_them_dlthemcl:
                if (batLoi()) {
                    addCaLam();
                }
                break;
        }
    }

    public boolean batLoi() {
        if (edttenCaLam.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên ca làm!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (txtGioBd.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn giờ bắt đầu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (txtGioKt.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn giờ kết thúc!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtLuong1Gio.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập lương 1 giờ làm!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void readData() {
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            txtCuaHang.setVisibility(View.VISIBLE);
            txtNgay.setVisibility(View.VISIBLE);
            String cuahang = bundle.getString("cuahang");
            String ngay = bundle.getString("ngay");
            txtCuaHang.setText(cuahang);
            txtNgay.setText(ngay);
        }
        mData = FirebaseDatabase.getInstance().getReference("CaLam");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ModelCaLam modelCaLam = data.getValue(ModelCaLam.class);
                    modelCaLam.setId(data.getKey());
                    list.add(modelCaLam);
                }
                if (list.size()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CaLamActivity.this);
                    builder.setIcon(R.drawable.iconnotification);
                    builder.setCancelable(false);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Hiện tại chưa có ca làm, hãy thêm ca làm");
                    builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                showDialogThemCaLam();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
                adapterCaLam.notifyDataSetChanged();
                ivLoading.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Tải dữ liệu thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Tải dữ liệu thất bại", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showDialogThemCaLam() throws ParseException {
        dialog = new Dialog(CaLamActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_themcalam);
        edttenCaLam = dialog.findViewById(R.id.edt_ten_dlthemcl);
        txtGioBd = dialog.findViewById(R.id.txt_giobd_dlthemcl);
        txtGioKt = dialog.findViewById(R.id.txt_giokt_dlthemcl);
        edtLuong1Gio = dialog.findViewById(R.id.edt_luong1h_dlthemcl);
        txtMaCaLam = dialog.findViewById(R.id.txt_ma_dlthemcl);
        txtTongGioCl = dialog.findViewById(R.id.txt_tongtglam_dlthemcl);
        txtLuong1Ca = dialog.findViewById(R.id.txt_luong1ca_dlthemcl);
        btnGioBd = dialog.findViewById(R.id.btn_giobd_dlthemcl);
        btnGioKt = dialog.findViewById(R.id.btn_giokt_dlthemcl);
        btnHuy = dialog.findViewById(R.id.btn_huy_dlthemcl);
        edttenCaLam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtMaCaLam.setText(function.convert(edttenCaLam.getText().toString()));
            }
        });

        txtGioKt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtTongGioCl.setText(String.valueOf(function.soGioLam(txtGioBd.getText().toString(), txtGioKt.getText().toString())));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtLuong1Gio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtLuong1Ca.setText(String.valueOf(function.luong1CL(txtTongGioCl.getText().toString(), edtLuong1Gio.getText().toString())));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnGioBd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(txtGioBd);
            }
        });
        btnGioKt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(txtGioKt);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void getString() {
        maCl = txtMaCaLam.getText().toString();
        tenCl = edttenCaLam.getText().toString();
        tgBdCl = txtGioBd.getText().toString();
        tgKtCl = txtGioKt.getText().toString();
        luong1Gio = edtLuong1Gio.getText().toString();
        tongGioCl = txtTongGioCl.getText().toString();
        luong1Ca = txtLuong1Ca.getText().toString();
    }


    public void addCaLam() {
        mData = FirebaseDatabase.getInstance().getReference("CaLam");
        getString();
        String uid = mData.push().getKey();
        ModelCaLam modelCaLam = new ModelCaLam(maCl, tenCl, tgBdCl, tgKtCl, tongGioCl, Double.parseDouble(luong1Ca), Double.parseDouble(luong1Gio));
        mData.child(uid).setValue(modelCaLam).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Thêm thất bại!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showTimePicker(final TextView txt) {
        final Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                txt.setText(formatter.format(calendar.getTime()));
            }
        }, gio, phut, true);
        timePickerDialog.show();
    }
}
