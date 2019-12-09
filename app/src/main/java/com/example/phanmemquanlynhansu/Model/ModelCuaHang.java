package com.example.phanmemquanlynhansu.Model;

import java.io.Serializable;

public class ModelCuaHang implements Serializable {
    String maCuaHang, tenCuaHang, diaChi, id;

    public ModelCuaHang() {

    }
    public ModelCuaHang(String maCuaHang, String tenCuaHang, String diaChi) {
        this.maCuaHang = maCuaHang;
        this.tenCuaHang = tenCuaHang;
        this.diaChi = diaChi;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(String maCuaHang) {
        this.maCuaHang = maCuaHang;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

}
