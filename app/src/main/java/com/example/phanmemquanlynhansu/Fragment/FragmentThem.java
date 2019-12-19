package com.example.phanmemquanlynhansu.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phanmemquanlynhansu.BangLuongActivity;
import com.example.phanmemquanlynhansu.CaLamActivity;
import com.example.phanmemquanlynhansu.ChucVuActivity;
import com.example.phanmemquanlynhansu.CuaHangActivity;
import com.example.phanmemquanlynhansu.LoginActivity;
import com.example.phanmemquanlynhansu.Model.ModelCaLam;
import com.example.phanmemquanlynhansu.Model.ModelNhanVien;
import com.example.phanmemquanlynhansu.NhanVienActivity;
import com.example.phanmemquanlynhansu.PhanCaLamActivity;
import com.example.phanmemquanlynhansu.R;
import com.example.phanmemquanlynhansu.SuaNhanVienActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentThem extends Fragment {
    View view;
    LinearLayout btnNhanVien, btnCaLam, btnCuaHang, btnChucVu, btnPhanCaLam, btnXemCaLamViec, btnDangXuat, layoutQuanLy, btnBangLuong;
    DatabaseReference mData;
    FirebaseUser currentFirebaseUser;
    CircleImageView imgHinh;
    TextView txtTen, txtInfo;
    ImageView ivLoading;
    AnimationDrawable animation;
    LinearLayout rellyFgThem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_them, container, false);
        addControl();
        addEvent();
        getData();
        return view;
    }

    public void addControl() {
        btnNhanVien = view.findViewById(R.id.btn_nhanvien_fgthem);
        btnCaLam = view.findViewById(R.id.btn_calam_fgthem);
        btnCuaHang = view.findViewById(R.id.btn_cuahang_fgthem);
        btnChucVu = view.findViewById(R.id.btn_chuc_vu_fgthem);
        btnPhanCaLam = view.findViewById(R.id.btn_phancalam_fgthem);
        btnXemCaLamViec = view.findViewById(R.id.btn_xemcalamviec_fgthem);
        btnDangXuat = view.findViewById(R.id.btn_dangxuat_fgthem);
        btnBangLuong = view.findViewById(R.id.btn_bangluong_fragthem);

        txtTen = view.findViewById(R.id.txt_ten_fragthem);
        txtInfo = view.findViewById(R.id.txt_info_fragthem);
        imgHinh = view.findViewById(R.id.img_fragthem);

        layoutQuanLy = view.findViewById(R.id.layout_quanly_fragthem);
        ivLoading = view.findViewById(R.id.iv_loading);
        rellyFgThem = view.findViewById(R.id.relly_fgthem);
    }

    public void addEvent() {
        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NhanVienActivity.class);
                startActivity(intent);
            }
        });
        btnCaLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaLamActivity.class);
                startActivity(intent);
            }
        });

        btnCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CuaHangActivity.class);
                startActivity(intent);
            }
        });

        btnChucVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChucVuActivity.class);
                startActivity(intent);
            }
        });
        btnPhanCaLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CuaHangActivity.class);
                intent.putExtra("phancalam", "1");
                startActivity(intent);
            }
        });
        btnXemCaLamViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CuaHangActivity.class);
                intent.putExtra("phancalam", "2");
                startActivity(intent);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanDangXuat();
            }
        });
        btnBangLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BangLuongActivity.class);
                startActivity(intent);
            }
        });
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SuaNhanVienActivity.class);
                intent.putExtra("userfrag", "1");
                startActivity(intent);
            }
        });
        ivLoading.setBackgroundResource(R.drawable.loading);
        animation = (AnimationDrawable) ivLoading.getBackground();
        animation.start();
    }

    private void xacNhanDangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn muốn đăng xuất ?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currentFirebaseUser != null) {
                    FirebaseAuth.getInstance().signOut();
                }
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();


    }

    public void getData() {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            mData = FirebaseDatabase.getInstance().getReference("NhanVien");
            mData.orderByKey().equalTo(currentFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelNhanVien modelNhanVien = data.getValue(ModelNhanVien.class);
                        Picasso.get().load(modelNhanVien.getUrlHinhNv()).into(imgHinh);
                        txtTen.setText(modelNhanVien.getTenNv());
                        txtInfo.setText(modelNhanVien.getMaChucVu() + " - " + modelNhanVien.getUserNv());
                        if (!modelNhanVien.getMaChucVu().equals("Quản lý")) {
                            layoutQuanLy.setVisibility(View.GONE);
                        }
                        ivLoading.setVisibility(View.GONE);
                        rellyFgThem.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    rellyFgThem.setVisibility(View.VISIBLE);
                    ivLoading.setVisibility(View.GONE);
                }
            });
        }else {
            rellyFgThem.setVisibility(View.VISIBLE);
            ivLoading.setVisibility(View.GONE);
        }
//        if (currentFirebaseUser.getUid().equals("5jUXYvVHR7ebByRKDL9cNtunY8i1")){
//
//        }
    }
}
