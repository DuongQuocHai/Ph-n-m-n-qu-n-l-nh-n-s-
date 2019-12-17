package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.example.phanmemquanlynhansu.Function.Function;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth firebaseAuth;

    ProgressDialog pDialog;

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
                } else if (edtPass.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                } else if (edtUser.getText().toString().trim().equals("admin") && edtPass.getText().toString().trim().equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    checkLogin();
                }

            }
        });
    }

    private void checkLogin() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging in ...");
        showDialog();
        firebaseAuth = FirebaseAuth.getInstance();
        String email = edtUser.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    hideDialog();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        });

    }

    public void showDialog(){
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }
    public void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
