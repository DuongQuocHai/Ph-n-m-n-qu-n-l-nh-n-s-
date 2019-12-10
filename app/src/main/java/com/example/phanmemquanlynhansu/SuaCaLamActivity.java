package com.example.phanmemquanlynhansu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phanmemquanlynhansu.Model.ModelCaLam;

public class SuaCaLamActivity extends AppCompatActivity {
    EditText edttenCaLam, edtGioBd, edtPhutBd, edtGioKt, edtPhutKt, edtLuong1Gio;
    TextView txtTongGioCl, txtLuong1Ca, txtMaCaLam;
    String maCl, tenCl, gioBdCl, phutBdCl, gioKtCl, phutKtCl, tongGioCl;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ca_lam);
        addControl();
        addEvents();
        getData();


    }

    private void addEvents() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_sua_calam);
    }

    public void clickSuaCaLam() {
        switch (view.getId()) {
            case R.id.action_bar_sua_calam:

                break;
            case R.id.action_bar_back_sua_calam:
                break;
        }
    }

    public void addControl(){
        edttenCaLam = findViewById(R.id.edt_ten_suacl);
        edtGioBd = findViewById(R.id.edt_giobd_suacl);
        edtPhutBd = findViewById(R.id.edt_phutbd_suacl);
        edtGioKt = findViewById(R.id.edt_giokt_suacl);
        edtPhutKt = findViewById(R.id.edt_phutkt_suacl);
        edtLuong1Gio = findViewById(R.id.edt_luong1h_suacl);
        txtMaCaLam = findViewById(R.id.txt_ma_suacl);
        txtTongGioCl = findViewById(R.id.txt_tongtglam_suacl);
        txtLuong1Ca = findViewById(R.id.txt_luong1ca_suacl);
    }

    public void getData(){
        Intent intent = getIntent();
        ModelCaLam modelCaLam = (ModelCaLam) intent.getSerializableExtra("ModelCalam");

        txtMaCaLam.setText(modelCaLam.getMaCaLam());
        edttenCaLam.setText(modelCaLam.getTenCaLam());
//        edtGioBd.setText(modelCaLam.getMaCaLam());
//        edtPhutBd.setText(modelCaLam.getMaCaLam());
//        edtGioKt.setText(modelCaLam.getMaCaLam());
//        edtPhutKt.setText(modelCaLam.getMaCaLam());
        edtLuong1Gio.setText(modelCaLam.getLuong1GioLam()+"");
        txtTongGioCl.setText(modelCaLam.getTongGioLam());
        txtLuong1Ca.setText(modelCaLam.getLuongCaLam()+"");

//        maCl = txtMaCaLam.getText().toString();
//        tenCl = edttenCaLam.getText().toString();
//        gioBdCl = edtGioBd.getText().toString();
//        phutBdCl = edtPhutBd.getText().toString();
//        gioKtCl = edtGioKt.getText().toString();
//        phutKtCl = edtPhutKt.getText().toString();



    }

}
