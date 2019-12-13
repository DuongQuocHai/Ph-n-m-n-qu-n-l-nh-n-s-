package com.example.phanmemquanlynhansu.Function;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.phanmemquanlynhansu.Model.ModelChucVu;
import com.example.phanmemquanlynhansu.Model.ModelCuaHang;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NhanVienDAO {
    DatabaseReference mData;
    FirebaseStorage storage;
    StorageReference mountainsRef;
    StorageReference storageRef;

    public void ganDsChucVuVaoSpiner(final Context context, final Spinner spn, final String str) {
        final List<String> listChucVu = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference("ChucVu");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listChucVu.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ModelChucVu modelChucVu = data.getValue(ModelChucVu.class);
                    listChucVu.add(modelChucVu.getTenChucVu());
                }
                ArrayAdapter<String> adapterChucVu = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listChucVu);
                adapterChucVu.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spn.setAdapter(adapterChucVu);
                if (str != null) {
                    for (int i = 0; i < spn.getAdapter().getCount(); i++) {
                        if (adapterChucVu.getItem(i).equals(str)) {
                            spn.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void ganDsCuaHangVaoSpiner(final Context context, final Spinner spn, final String str) {
        final ArrayList<String> arr = new ArrayList<>();
        mData = FirebaseDatabase.getInstance().getReference("CuaHang");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ModelCuaHang modelCuaHang = data.getValue(ModelCuaHang.class);
                    arr.add(modelCuaHang.getTenCuaHang());
                }
                ArrayAdapter<String> adapterCuaHang = new ArrayAdapter(context, android.R.layout.simple_spinner_item, arr);
                adapterCuaHang.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spn.setAdapter(adapterCuaHang);
                if (str != null) {
                    for (int i = 0; i < spn.getAdapter().getCount(); i++) {
                        if (adapterCuaHang.getItem(i).equals(str)) {
                            spn.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}


