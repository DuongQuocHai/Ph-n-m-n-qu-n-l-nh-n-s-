package com.example.phanmemquanlynhansu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phanmemquanlynhansu.Function.NhanVienDAO;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_SHORT;

public class SuaNhanVienActivity extends AppCompatActivity {
    ModelNhanVien modelNhanVien;
    CircleImageView imgAvatar, btnEditAvatar;
    EditText edtTen, edtUser, edtSdt, edtDiaChi;
    Spinner spnChucVu, spnCuaHang;
    RadioButton rdNam, rdNu;
    Button btnDoiMk, btnLuu1;
    TextView btnXoa, btnLuu;
    ImageView btnBack;
    //dialog
    EditText edtOldPass, edtNewPass, edtRePass;
    Button btnHuy, btnLuudl;

    NhanVienDAO nhanVienDAO;

    DatabaseReference mData;
    FirebaseStorage storage;
    StorageReference mountainsRef;
    StorageReference storageRef;

    int REQUEST_CHOOSE_PHOTO = 321;

    String ten, user, chucVu, cuaHang, gioiTinh, sdt, diaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nhan_vien);
        addControls();
        addEvents();
        getData();
    }

    public void addControls() {
        btnEditAvatar = findViewById(R.id.img_editimg_suanv);
        edtTen = findViewById(R.id.edt_ten_suanv);
        edtUser = findViewById(R.id.edt_tendn_suanv);
        edtSdt = findViewById(R.id.edt_sdt_suanv);
        edtDiaChi = findViewById(R.id.edt_diachi_suanv);

        btnXoa = findViewById(R.id.btn_xoa_suanv);

        imgAvatar = findViewById(R.id.img_suanv);
        spnChucVu = findViewById(R.id.spn_chucvu_suanv);
        spnCuaHang = findViewById(R.id.spn_chinhanh_suanv);
        rdNam = findViewById(R.id.rd_nam_suanv);
        rdNu = findViewById(R.id.rd_nu_suanv);
        btnDoiMk = findViewById(R.id.btn_doimk_suanv);


    }

    public void addEvents() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_sua_nhanvien);
        View view = getSupportActionBar().getCustomView();
        btnLuu = view.findViewById(R.id.action_bar_sua_nhanvien);
        btnBack = view.findViewById(R.id.action_bar_back_sua_nhanvien);

        rdNam.setOnCheckedChangeListener(listenerRadio);
        rdNu.setOnCheckedChangeListener(listenerRadio);
        btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (batLoi()) {
                    suaNhanVien();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaNhanVien();
            }
        });
        btnDoiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDoiMatKhau();
            }
        });

    }

    CompoundButton.OnCheckedChangeListener listenerRadio = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                gioiTinh = (String) buttonView.getText();
            }
        }
    };

    public boolean batLoi() {
        if (edtTen.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
        } else if (edtUser.getText().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void getString() {
        ten = edtTen.getText().toString();
        user = edtUser.getText().toString();
        chucVu = spnChucVu.getSelectedItem().toString();
        cuaHang = spnCuaHang.getSelectedItem().toString();
        sdt = edtSdt.getText().toString();
        diaChi = edtDiaChi.getText().toString();
    }

    public void getData() {
        nhanVienDAO = new NhanVienDAO();
        Intent intent = getIntent();
        modelNhanVien = (ModelNhanVien) intent.getSerializableExtra("ModelNhanVien");
        Picasso.get().load(modelNhanVien.getUrlHinhNv()).into(imgAvatar);
        nhanVienDAO.ganDsChucVuVaoSpiner(SuaNhanVienActivity.this, spnChucVu, modelNhanVien.getMaChucVu());
        nhanVienDAO.ganDsCuaHangVaoSpiner(SuaNhanVienActivity.this, spnCuaHang, modelNhanVien.getMaCuaHang());
        gioiTinh = modelNhanVien.getGioiTinhNv();
        if (gioiTinh.equals("Nam")) {
            rdNam.setChecked(true);
        } else {
            rdNu.setChecked(true);
        }
        edtTen.setText(modelNhanVien.getTenNv());
        edtUser.setText(modelNhanVien.getUserNv());
        edtSdt.setText(modelNhanVien.getSdtNv());
        edtDiaChi.setText(modelNhanVien.getDiaChiNv());
    }

    private void suaNhanVien() {
        getString();
        mData = FirebaseDatabase.getInstance().getReference("NhanVien");
        final String uid = modelNhanVien.getIdNv();
        storage = FirebaseStorage.getInstance("gs://phanmemquanlynhansu-eda6c.appspot.com");
        storageRef = storage.getReference();
        Calendar calendar = Calendar.getInstance();
        mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        imgAvatar.setDrawingCacheEnabled(true);
        imgAvatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(SuaNhanVienActivity.this, "Lỗi", LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
                mData.child(uid).child("diaChiNv").setValue(diaChi);
                mData.child(uid).child("gioiTinhNv").setValue(gioiTinh);
                mData.child(uid).child("maChucVu").setValue(chucVu);
                mData.child(uid).child("maCuaHang").setValue(cuaHang);
                mData.child(uid).child("sdtNv").setValue(sdt);
                mData.child(uid).child("tenNv").setValue(ten);
                mData.child(uid).child("urlHinhNv").setValue(String.valueOf(downloadUrl));
                mData.child(uid).child("userNv").setValue(user);
                Toast.makeText(SuaNhanVienActivity.this, "Lưu thành công", LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void xoaNhanVien() {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("NhanVien").child(modelNhanVien.getIdNv()).removeValue();
        finish();
    }

    private void dialogDoiMatKhau() {
        final Dialog dialog = new Dialog(SuaNhanVienActivity.this);
        dialog.setContentView(R.layout.dialog_doimatkhau);
        edtOldPass = dialog.findViewById(R.id.edt_mkcu_dldoimk);
        edtNewPass = dialog.findViewById(R.id.edt_mkmoi_dldoimk);
        edtRePass = dialog.findViewById(R.id.edt_nhaplaimk_dldoimk);
        btnHuy = dialog.findViewById(R.id.btn_huy_dldoimk);
        btnLuu1 = dialog.findViewById(R.id.btn_luu_dldoimk);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnLuu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doiMatKhau()){
                    dialog.dismiss();
                }
            }
        });


        dialog.show();
    }

    private boolean doiMatKhau() {
        String pass = edtOldPass.getText().toString();
        String newpass = edtNewPass.getText().toString();
        String repass = edtRePass.getText().toString();

        if (pass.trim().length() != 0 || newpass.trim().length() != 0 || repass.trim().length() != 0){
           if (pass.equals(modelNhanVien.getPassNv())){
               if (newpass.equals(repass)){
                   mData = FirebaseDatabase.getInstance().getReference("NhanVien");
                   String uid = modelNhanVien.getIdNv();
                   mData.child(uid).child("passNv").setValue(newpass, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                           if (databaseError == null) {
                               Toast.makeText(SuaNhanVienActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                           }else Toast.makeText(SuaNhanVienActivity.this, "Lỗi "+databaseError, Toast.LENGTH_SHORT).show();
                       }
                   });
                   return true;
               }else Toast.makeText(this, "Mật khẩu không trùng nhau", LENGTH_SHORT).show();
           }else Toast.makeText(this, "Sai mật khẩu", LENGTH_SHORT).show();
        }else Toast.makeText(this, "Hãy nhập đầy đủ các trường!", LENGTH_SHORT).show();
        return false;
    }

    private void chooseImg() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
