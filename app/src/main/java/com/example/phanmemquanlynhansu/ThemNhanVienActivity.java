package com.example.phanmemquanlynhansu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemNhanVienActivity extends AppCompatActivity {
    Spinner spnChucVu, spnChiNhanh;
    EditText edtTen, edtSdt, edtDiaChi, edtUser, edtPass, edtRePass;
    RadioButton rdNam, rdNu;
    CircleImageView imgNhanVien, imgEditNhanVien;
    Button btnLamMoi, btnChonHinh;
    View view;

    String ten, user, pass, rePass, chucVu, cuaHang, gioiTinh = "Nam", sdt, diaChi, urlHinh;

    DatabaseReference mData;

    final int REQUEST_CHOOSE_PHOTO = 321;
    FirebaseStorage storage;
    StorageReference mountainsRef;
    StorageReference storageRef;
    ModelNhanVien modelNhanVien;


    ImageView btnBack;
    TextView btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);


        addControl();
        addEnvent();


    }

    public void addControl() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_them_nhan_vien);
        view = getSupportActionBar().getCustomView();
        btnBack = view.findViewById(R.id.btn_back_themnv);
        btnThem = view.findViewById(R.id.btn_them_themnv);

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
        ganDsCuaHangVaoSpiner();
        ganDsChucVuVaoSpiner();
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
                addNhanVien();
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
        user = edtTen.getText().toString();
        pass = edtTen.getText().toString();
        rePass = edtTen.getText().toString();
        chucVu = spnChucVu.getSelectedItem().toString();
        cuaHang = spnChiNhanh.getSelectedItem().toString();
        sdt = edtSdt.getText().toString();
        sdt = edtSdt.getText().toString();
        diaChi = edtDiaChi.getText().toString();
    }

    public void addNhanVien() {
        getString();
        mData = FirebaseDatabase.getInstance().getReference("NhanVien");
        storage = FirebaseStorage.getInstance("gs://phanmemquanlynhansu-eda6c.appspot.com");
        storageRef = storage.getReference();
        Calendar calendar = Calendar.getInstance();
        mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        imgNhanVien.setDrawingCacheEnabled(true);
        imgNhanVien.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgNhanVien.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ThemNhanVienActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
                Toast.makeText(ThemNhanVienActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                Log.e("urlImage : ", downloadUrl + "");
                modelNhanVien = new ModelNhanVien(ten, user, pass, chucVu, cuaHang, gioiTinh, sdt, diaChi, String.valueOf(downloadUrl));
                mData.push().setValue(modelNhanVien, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(ThemNhanVienActivity.this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void ganDsCuaHangVaoSpiner() {
        final ArrayList<String> arr = new ArrayList<>();
        arr.add("Chọn cửa hàng");
        mData = FirebaseDatabase.getInstance().getReference("CuaHang");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ModelCuaHang modelCuaHang = data.getValue(ModelCuaHang.class);
                    arr.add(modelCuaHang.getTenCuaHang());
                }
                Toast.makeText(getApplicationContext(), "Load Data Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        ArrayAdapter<String> adapterCuaHang = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arr);
        adapterCuaHang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChiNhanh.setAdapter(adapterCuaHang);
    }

    private void ganDsChucVuVaoSpiner() {
        spnChucVu = (Spinner) findViewById(R.id.spn_chucvu_themnv);
        List<String> listChucVu = new ArrayList<>();
        listChucVu.add("Quản lý");
        listChucVu.add("Nhân Viên");
        ArrayAdapter<String> adapterChucVu = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listChucVu);
        adapterChucVu.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChucVu.setAdapter(adapterChucVu);
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
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgNhanVien.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
