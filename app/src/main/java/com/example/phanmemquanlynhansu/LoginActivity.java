package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin;
    RelativeLayout relly, relly2;
    TextInputEditText edtUser, edtPass;
    ImageView ivLoading;
    AnimationDrawable animation;

    DatabaseReference mData;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                relly.setVisibility(View.VISIBLE);
                relly2.setVisibility(View.VISIBLE);
            }
        };

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        relly = (RelativeLayout) findViewById(R.id.relly);
        relly2 = (RelativeLayout) findViewById(R.id.relly2);
        handler.postDelayed(runnable, 2000);
        addControls();
        addEvents();


    }

    private boolean batLoi() {
        if (edtUser.equals("")) {
            Toast.makeText(this, "Vui lòng nhập Username!", Toast.LENGTH_SHORT).show();
        }
        if (edtPass.equals("")) {
            Toast.makeText(this, "Vui lòng nhập Password!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void addControls() {
        edtUser = findViewById(R.id.edt_user_login);
        edtPass = findViewById(R.id.edt_pass_login);
        btnlogin = findViewById(R.id.btn_dangnhap_login);
        ivLoading = findViewById(R.id.iv_loading);
    }

    public void addEvents() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUser.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT).show();
                } else if ( edtPass.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                } else {
                    ivLoading.setBackgroundResource(R.drawable.loading);
                    animation = (AnimationDrawable) ivLoading.getBackground();
                    animation.start();
                    checkLogin();
                }
            }
        });
    }

    private void checkLogin() {
        mData = FirebaseDatabase.getInstance().getReference("NhanVien");
        final String user = edtUser.getText().toString();
        final String pass = edtPass.getText().toString();
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot data : dataSnapshot.getChildren()) {
                    if (user.equals(data.child("userNv").getValue().toString()) && pass.equals(data.child("passNv").getValue().toString())) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        DatabaseReference dataLog = FirebaseDatabase.getInstance().getReference("logLogin");
                        dataLog.push().setValue(user);
                        startActivity(intent);
                        return;
                    } else
                        Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
                }
                ivLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
