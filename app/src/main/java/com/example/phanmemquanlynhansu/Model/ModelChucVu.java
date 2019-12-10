package com.example.phanmemquanlynhansu.Model;

import java.io.Serializable;

public class ModelChucVu implements Serializable {
    String maChucVu, tenChucVu, ghiChu, id;

    public String getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelChucVu(String maChucVu, String tenChucVu, String ghiChu) {
        this.maChucVu = maChucVu;
        this.tenChucVu = tenChucVu;
        this.ghiChu = ghiChu;
    }

    public ModelChucVu() {

    }

}
