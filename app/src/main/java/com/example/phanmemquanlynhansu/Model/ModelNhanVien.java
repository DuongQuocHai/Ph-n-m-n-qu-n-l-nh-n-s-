package com.example.phanmemquanlynhansu.Model;

import java.io.Serializable;

public class ModelNhanVien implements Serializable {
    String idNv, tenNv, userNv, passNv, maChucVu, maCuaHang, gioiTinhNv, sdtNv,diaChiNv, urlHinhNv;

    public ModelNhanVien(String tenNv, String userNv, String passNv, String maChucVu, String maCuaHang, String gioiTinhNv, String sdtNv, String diaChiNv, String urlHinhNv) {
        this.tenNv = tenNv;
        this.userNv = userNv;
        this.passNv = passNv;
        this.maChucVu = maChucVu;
        this.maCuaHang = maCuaHang;
        this.gioiTinhNv = gioiTinhNv;
        this.sdtNv = sdtNv;
        this.diaChiNv = diaChiNv;
        this.urlHinhNv = urlHinhNv;
    }

    public ModelNhanVien() {
    }

    public String getIdNv() {
        return idNv;
    }

    public void setIdNv(String idNv) {
        this.idNv = idNv;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getUserNv() {
        return userNv;
    }

    public void setUserNv(String userNv) {
        this.userNv = userNv;
    }

    public String getPassNv() {
        return passNv;
    }

    public void setPassNv(String passNv) {
        this.passNv = passNv;
    }

    public String getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(String maCuaHang) {
        this.maCuaHang = maCuaHang;
    }

    public String getGioiTinhNv() {
        return gioiTinhNv;
    }

    public void setGioiTinhNv(String gioiTinhNv) {
        this.gioiTinhNv = gioiTinhNv;
    }

    public String getSdtNv() {
        return sdtNv;
    }

    public void setSdtNv(String sdtNv) {
        this.sdtNv = sdtNv;
    }

    public String getDiaChiNv() {
        return diaChiNv;
    }

    public void setDiaChiNv(String diaChiNv) {
        this.diaChiNv = diaChiNv;
    }

    public String getUrlHinhNv() {
        return urlHinhNv;
    }

    public void setUrlHinhNv(String urlHinhNv) {
        this.urlHinhNv = urlHinhNv;
    }

    @Override
    public String toString() {
        return "ModelNhanVien{" +
                "idNv='" + idNv + '\'' +
                ", tenNv='" + tenNv + '\'' +
                ", userNv='" + userNv + '\'' +
                ", passNv='" + passNv + '\'' +
                ", maChucVu='" + maChucVu + '\'' +
                ", maCuaHang='" + maCuaHang + '\'' +
                ", gioiTinhNv='" + gioiTinhNv + '\'' +
                ", sdtNv='" + sdtNv + '\'' +
                ", diaChiNv='" + diaChiNv + '\'' +
                ", urlHinhNv='" + urlHinhNv + '\'' +
                '}';
    }
}
