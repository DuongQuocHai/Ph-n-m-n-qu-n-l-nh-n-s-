package com.example.phanmemquanlynhansu.Function;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Function {

    public String convert(String str) {
        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replaceAll("đ", "d");

        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll(" ", "_");
        return str;
    }

    public static Date converttoTime(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("HH:mm");

        return date;
    }

    public String soGioLam(String strGioBd, String strGioKt ){
        java.util.Date bd = null ;
        java.util.Date kt = null;
        bd = converttoTime(strGioBd);
        kt = converttoTime(strGioKt);

        int gioBatDau = bd.getHours() * 60 + bd.getMinutes();
        int gioKetThuc = kt.getHours() * 60 + kt.getMinutes();
        int soPhut = (gioKetThuc - gioBatDau) % 60;
        int soGio = (gioKetThuc - gioBatDau) / 60;
        String gioLam ;
        if (soGio < 10) {
            gioLam = "0" + soGio + ":"+ String.valueOf(soPhut);
        } if (soPhut < 10) {
            gioLam = String.valueOf(soGio) + ":" + "0" + soPhut;
        } if (soPhut < 10 && soPhut < 10) {
            gioLam = "0" + soGio + ":"+ "0" + soPhut;
        } else gioLam = String.valueOf(soGio) + ":"+ String.valueOf(soPhut);
        return gioLam;
    }

    public double luong1CL(String strGioBd, String strGioKt, double luong1Gio) {
//        double luong1Gio = Double.parseDouble(edtLuong1Gio.getText().toString());
//        double gioBatDau = Integer.parseInt(giobd) * 60 + Integer.parseInt(phutbd);
//        double gioKetThuc = Integer.parseInt(giokt) * 60 + Integer.parseInt(phutkt);
//        double soGio = (gioKetThuc - gioBatDau) / 60;
//        double tongLuong1Cl = soGio * luong1Gio;
        java.util.Date bd = null ;
        java.util.Date kt = null;
        bd = converttoTime(strGioBd);
        kt = converttoTime(strGioKt);

        double gioBatDau = bd.getHours() * 60 + bd.getMinutes();
        double gioKetThuc = kt.getHours() * 60 + kt.getMinutes();
        double soGio = (gioKetThuc - gioBatDau) / 60;
        double luongCaLam = soGio * luong1Gio;
        return luongCaLam;
    }
}
