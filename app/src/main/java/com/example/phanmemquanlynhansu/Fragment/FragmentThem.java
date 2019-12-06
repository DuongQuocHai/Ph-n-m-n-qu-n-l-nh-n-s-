package com.example.phanmemquanlynhansu.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phanmemquanlynhansu.ImageConverter;
import com.example.phanmemquanlynhansu.NhanVienActivity;
import com.example.phanmemquanlynhansu.R;

public class FragmentThem extends Fragment {
    LinearLayout nhanVien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them,container,false);
        nhanVien = (LinearLayout) view.findViewById(R.id.nhanVien);
        nhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NhanVienActivity.class);
                startActivity(intent);
            }
        });

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = (ImageView) view.findViewById(R.id.civAvatar);
        circularImageView.setImageBitmap(circularBitmap);

        return view;
    }

}
