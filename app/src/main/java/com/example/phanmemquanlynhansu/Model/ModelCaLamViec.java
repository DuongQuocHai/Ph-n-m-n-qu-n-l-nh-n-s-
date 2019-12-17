package com.example.phanmemquanlynhansu.Model;

import java.util.ArrayList;

public class ModelCaLamViec {
    String ngay, cuaHang;
    ModelCaLam modelCaLam;
    ArrayList<ModelNhanVien> listNhanVien;

    public ModelCaLamViec() {
    }

    public ModelCaLamViec(String ngay, String cuaHang, ModelCaLam modelCaLam, ArrayList<ModelNhanVien> listNhanVien) {
        this.ngay = ngay;
        this.cuaHang = cuaHang;
        this.modelCaLam = modelCaLam;
        this.listNhanVien = listNhanVien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(String cuaHang) {
        this.cuaHang = cuaHang;
    }

    public ModelCaLam getModelCaLam() {
        return modelCaLam;
    }

    public void setModelCaLam(ModelCaLam modelCaLam) {
        this.modelCaLam = modelCaLam;
    }

    public ArrayList<ModelNhanVien> getListNhanVien() {
        return listNhanVien;
    }

    public void setListNhanVien(ArrayList<ModelNhanVien> listNhanVien) {
        this.listNhanVien = listNhanVien;
    }
}
