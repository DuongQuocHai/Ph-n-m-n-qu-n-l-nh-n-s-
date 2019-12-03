package com.example.phanmemquanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CaLamActivity extends AppCompatActivity {
    ListView lvCaLam;
    EditText edtMaCaLam, edttenCaLam, edtGioBd, edtPhutBd, edtGioKt, edtPhutKt, edtLuong1Gio;
    TextView txtTongGioCl, txtLuong1Ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_lam);
    }

    public void addControl() {
        lvCaLam = findViewById(R.id.lvcalam);
    }

    public void clickCaLam(View view) {
        switch (view.getId()) {
            case R.id.btn_them_calam:
                showDialogThemCaLam();
                break;
        }
    }

    public void showDialogThemCaLam() {
        Dialog dialog = new Dialog(CaLamActivity.this);
        dialog.setContentView(R.layout.dialog_themcalam);
        edtMaCaLam      = dialog.findViewById(R.id.edt_ma_dlthemcl);
        edttenCaLam     = dialog.findViewById(R.id.edt_ten_dlthemcl);
        edtGioBd        = dialog.findViewById(R.id.edt_giobd_dlthemcl);
        edtPhutBd       = dialog.findViewById(R.id.edt_phutbd_dlthemcl);
        edtGioKt        = dialog.findViewById(R.id.edt_giokt_dlthemcl);
        edtPhutKt       = dialog.findViewById(R.id.edt_phutkt_dlthemcl);
        edtLuong1Gio    = dialog.findViewById(R.id.edt_luong1h_dlthemcl);
        txtTongGioCl    = dialog.findViewById(R.id.txt_tongtglam_dlthemcl);
        txtLuong1Ca     = dialog.findViewById(R.id.txt_luong1ca_dlthemcl);


        dialog.show();
    }
}
