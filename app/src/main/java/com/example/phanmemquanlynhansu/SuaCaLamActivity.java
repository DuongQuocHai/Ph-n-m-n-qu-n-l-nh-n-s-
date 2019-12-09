package com.example.phanmemquanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Function.Function;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SuaCaLamActivity extends AppCompatActivity {
    EditText edttenCaLam, edtLuong1Gio;
    TextView txtTongGioCl, txtLuong1Ca, txtMaCaLam, txtGioBdCl, txtGioKtCl;
    Button btnGioBD, btnGioKt;
    String maCl, tenCl, gioBdCl, gioKtCl, tongGioCl, luong1Ca, luong1Gio;

    ModelCaLam modelCaLam;
    Function function = new Function();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ca_lam);
        addControl();
        getData();


    }

    public void addControl() {
        edttenCaLam = findViewById(R.id.edt_ten_suacl);
        edtLuong1Gio = findViewById(R.id.edt_luong1h_suacl);
        txtMaCaLam = findViewById(R.id.txt_ma_suacl);
        txtTongGioCl = findViewById(R.id.txt_tongtglam_suacl);
        txtLuong1Ca = findViewById(R.id.txt_luong1ca_suacl);
        txtGioBdCl = findViewById(R.id.txt_giobd_suacl);
        txtGioKtCl = findViewById(R.id.txt_giokt_suacl);
    }

    public void getString() {
        maCl = txtMaCaLam.getText().toString();
        tenCl = edttenCaLam.getText().toString();
        gioBdCl = txtGioBdCl.getText().toString();
        gioKtCl = txtGioKtCl.getText().toString();
        tongGioCl = txtTongGioCl.getText().toString();
        luong1Gio = edtLuong1Gio.getText().toString();
        luong1Ca = txtLuong1Ca.getText().toString();
    }


    public void getData() {
        Intent intent = getIntent();
        modelCaLam = (ModelCaLam) intent.getSerializableExtra("ModelCalam");
        if (modelCaLam != null) {
            txtMaCaLam.setText(modelCaLam.getMaCaLam());
            edttenCaLam.setText(modelCaLam.getTenCaLam());
            txtGioBdCl.setText(modelCaLam.getTgBatDauCaLam());
            txtGioKtCl.setText(modelCaLam.getTgKetThucCaLam());
            edtLuong1Gio.setText(modelCaLam.getLuong1GioLam() + "");
            txtTongGioCl.setText(modelCaLam.getTongGioLam());
            txtLuong1Ca.setText(modelCaLam.getLuongCaLam() + "");
        } else Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        edttenCaLam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtMaCaLam.setText(function.convert(edttenCaLam.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtGioKtCl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtTongGioCl.setText(String.valueOf(function.soGioLam(txtGioBdCl.getText().toString(), txtGioKtCl.getText().toString())));

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

    }

    public void clickSuaCl(View view) {
        switch (view.getId()) {
            case R.id.btn_giobd_suacl:
                showTimePicker(txtGioBdCl);
                break;
            case R.id.btn_giokt_suacl:
                showTimePicker(txtGioKtCl);
                break;
            case R.id.btn_huy_suacl:
                finish();
                break;
            case R.id.btn_luu_suacl:
                getString();
                String uid = modelCaLam.getId();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("CaLam");
                myRef.child(uid).child("luong1GioLam").setValue(luong1Gio);
                myRef.child(uid).child("luongCaLam").setValue(luong1Ca);
                myRef.child(uid).child("maCaLam").setValue(maCl);
                myRef.child(uid).child("tenCaLam").setValue(tenCl);
                myRef.child(uid).child("tgBatDauCaLam").setValue(gioBdCl);
                myRef.child(uid).child("tgKetThucCaLam").setValue(gioKtCl);
                myRef.child(uid).child("tongGioLam").setValue(tongGioCl);
                break;
        }
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
