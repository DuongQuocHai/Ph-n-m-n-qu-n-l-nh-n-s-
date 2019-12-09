package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Adapter.AdapterCaLam;
import com.example.phanmemquanlynhansu.Function.Function;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
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
    TextView txtTongGioCl, txtLuong1Ca, txtMaCaLam, txtGioBd, txtGioKt;
    Button btnGioBd, btnGioKt;
    String maCl, tenCl, tgBdCl, tgKtCl, tongGioCl, luong1Gio, luong1Ca;
    DatabaseReference mData;
    Function function = new Function();
    ArrayList<ModelCaLam> list;
    AdapterCaLam adapterCaLam;

    public void clickCaLam(View view) throws ParseException {
        switch (view.getId()) {
            case R.id.btn_them_calam:
                showDialogThemCaLam();
                break;
            case R.id.btn_huy_dlthemcl:
//                Dialog dialog = new Dialog(CaLamActivity.this);
//                dialog.dismiss();
//                showTimePicker();
                getString();
                txtTongGioCl.setText(function.soGioLam(tgBdCl, tgKtCl));
                break;
            case R.id.btn_them_dlthemcl:
                addCaLam();
                break;
        }
    }

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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CaLamActivity.this, SuaCaLamActivity.class);
                intent.putExtra("ModelCalam", list.get(position));
                startActivity(intent);
            }
        });
        lvCaLam.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mData = FirebaseDatabase.getInstance().getReference("CaLam");
                mData.child(list.get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(CaLamActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
    }

    public void addControl() {
        lvCaLam = findViewById(R.id.lvcalam);
        list = new ArrayList<>();
        adapterCaLam = new AdapterCaLam(CaLamActivity.this, list);
        lvCaLam.setAdapter(adapterCaLam);
    }

    public void readData() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("CaLam").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ModelCaLam modelCaLam = dataSnapshot.getValue(ModelCaLam.class);
                list.add(modelCaLam);
                modelCaLam.setId(dataSnapshot.getKey());
                adapterCaLam.notifyDataSetChanged();
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

//    public void readData(){
//        mData = FirebaseDatabase.getInstance().getReference("CaLam");
//        mData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    ModelCaLam modelCaLam = data.getValue(ModelCaLam.class);
//                    modelCaLam.setId(data.getKey());
//                    list.add(modelCaLam);
//                }
//                Toast.makeText(getApplicationContext(), "Load Data Success", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public void showDialogThemCaLam() throws ParseException {
        Dialog dialog = new Dialog(CaLamActivity.this);

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
        mData.child(uid).setValue(modelCaLam);
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
