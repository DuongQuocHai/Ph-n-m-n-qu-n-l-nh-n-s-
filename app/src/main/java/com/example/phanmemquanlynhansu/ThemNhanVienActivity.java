package com.example.phanmemquanlynhansu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phanmemquanlynhansu.Function.Function;
import com.example.phanmemquanlynhansu.Function.NhanVienDAO;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemNhanVienActivity extends AppCompatActivity {
    Spinner spnChucVu, spnChiNhanh;
    EditText edtTen, edtSdt, edtDiaChi, edtUser, edtPass, edtRePass;
    RadioButton rdNam, rdNu;
    CircleImageView imgNhanVien, imgEditNhanVien;
    Button btnLamMoi;
    ImageView btnBack;
    TextView btnThem;
    Function function;
    View view;

    String ten, user, pass, rePass, chucVu, cuaHang, gioiTinh = "Nam", sdt, diaChi, urlHinh;

    final int REQUEST_CHOOSE_PHOTO = 321;
    ProgressBar progressBar;

    DatabaseReference mData;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    StorageReference mountainsRef;
    StorageReference storageRef;
    Uri filePath;

    ModelNhanVien modelNhanVien;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        addControl();
        addEnvent();
    }

    public void addControl() {
        nhanVienDAO = new NhanVienDAO();

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_them_nhan_vien);
        view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.btn_back_themnv);
        btnThem = view.findViewById(R.id.btn_them_themnv);

        progressBar = findViewById(R.id.progressbar);

        edtTen = findViewById(R.id.edt_ten_themnv);
        edtUser = findViewById(R.id.edt_tendn_themnv);
        edtPass = findViewById(R.id.edt_mathau_themnv);
        edtRePass = findViewById(R.id.edt_nhaplaimk_them);
        edtSdt = findViewById(R.id.edt_sdt_themnv);
        edtDiaChi = findViewById(R.id.edt_diachi_themnv);
        spnChucVu = findViewById(R.id.spn_chucvu_themnv);
        spnChiNhanh = findViewById(R.id.spn_chinhanh_thmenv);
        rdNam = findViewById(R.id.rd_nam_themnv);
        rdNu = findViewById(R.id.rd_nu_themnv);
        imgNhanVien = findViewById(R.id.img_themnv);
        imgEditNhanVien = findViewById(R.id.img_edit_nhanvien);
        btnLamMoi = findViewById(R.id.btn_lammoi_themnv);
        nhanVienDAO.ganDsCuaHangVaoSpiner(ThemNhanVienActivity.this, spnChiNhanh, "");
        nhanVienDAO.ganDsChucVuVaoSpiner(ThemNhanVienActivity.this, spnChucVu, "");
    }

    public void addEnvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (batLoi()) {
                    upLoad();
                }
            }
        });
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lamMoi();
            }
        });
        imgEditNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });
        rdNam.setOnCheckedChangeListener(listenerRadio);
        rdNu.setOnCheckedChangeListener(listenerRadio);
    }
    public void lamMoi() {
        edtTen.setText("");
        edtUser.setText("");
        edtPass.setText("");
        edtRePass.setText("");
        edtSdt.setText("");
        edtDiaChi.setText("");
        rdNam.setChecked(true);
    }
    private static boolean checkEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) {
            return false;
        }
        return true;
    }
    public boolean batLoi() {
        String email = edtUser.getText().toString();
        if (edtTen.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtUser.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkEmail(email)) {
            Toast.makeText(this, "Tên đăng nhập phải là Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtPass.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtPass.getText().length() < 6) {
            Toast.makeText(this, "Mật khẩu phải trên 6 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtRePass.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edtPass.getText().toString().equals(edtRePass.getText().toString())) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtSdt.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    CompoundButton.OnCheckedChangeListener listenerRadio = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                gioiTinh = (String) buttonView.getText();
            }
        }
    };

    public void getString() {
        ten = edtTen.getText().toString();
        user = edtUser.getText().toString();
        pass = edtPass.getText().toString();
        rePass = edtRePass.getText().toString();
        chucVu = spnChucVu.getSelectedItem().toString();
        cuaHang = spnChiNhanh.getSelectedItem().toString();
        sdt = edtSdt.getText().toString();
        diaChi = edtDiaChi.getText().toString();
    }
    public void upLoad() {
        if (filePath != null) {
            getString();
            firebaseAuth = FirebaseAuth.getInstance();
            progressBar.setVisibility(View.VISIBLE);
            storage = FirebaseStorage.getInstance("gs://phanmemquanlynhansu-eda6c.appspot.com");
            storageRef = storage.getReference();
            StorageReference reference = storageRef.child("image/" + UUID.randomUUID().toString());
            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    urlHinh = urlTask.getResult().toString();
                    firebaseAuth.fetchSignInMethodsForEmail(edtUser.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.getResult().getSignInMethods().size() == 0) {
                                firebaseAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(ThemNhanVienActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            modelNhanVien = new ModelNhanVien(ten, user, pass, chucVu, cuaHang, gioiTinh, sdt, diaChi, urlHinh);
                                            FirebaseDatabase.getInstance()
                                                    .getReference("NhanVien")
                                                    .child(FirebaseAuth.getInstance()
                                                            .getCurrentUser().getUid())
                                                    .setValue(modelNhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(ThemNhanVienActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(ThemNhanVienActivity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            });
                                        }else
                                            Toast.makeText(ThemNhanVienActivity.this, "--------", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(ThemNhanVienActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThemNhanVienActivity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        } else Toast.makeText(this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
    }

    void checkEmail() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.fetchSignInMethodsForEmail(edtUser.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                Log.d("hahahaahah", "" + task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0) {
                    // email not existed
                } else {
                    Toast.makeText(ThemNhanVienActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void chooseImg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
//                InputStream inputStream = getContentResolver().openInputStream(filePath);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgNhanVien.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
