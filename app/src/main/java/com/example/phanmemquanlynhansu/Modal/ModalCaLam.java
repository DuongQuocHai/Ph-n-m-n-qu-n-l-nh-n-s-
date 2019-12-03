package com.example.phanmemquanlynhansu.Modal;

public class ModalCaLam {
    String maCaLam, tenCaLam, tgBatDauCaLam, tgKetThucCaLam, tongGioLam;
    Double luongCaLam, luong1GioLam;

    public ModalCaLam() {
    }

    public ModalCaLam(String maCaLam, String tenCaLam, String tgBatDauCaLam, String tgKetThucCaLam, String tongGioLam, Double luongCaLam, Double luong1GioLam) {
        this.maCaLam = maCaLam;
        this.tenCaLam = tenCaLam;
        this.tgBatDauCaLam = tgBatDauCaLam;
        this.tgKetThucCaLam = tgKetThucCaLam;
        this.tongGioLam = tongGioLam;
        this.luongCaLam = luongCaLam;
        this.luong1GioLam = luong1GioLam;
    }

    public String getMaCaLam() {
        return maCaLam;
    }

    public void setMaCaLam(String maCaLam) {
        this.maCaLam = maCaLam;
    }

    public String getTenCaLam() {
        return tenCaLam;
    }

    public void setTenCaLam(String tenCaLam) {
        this.tenCaLam = tenCaLam;
    }

    public String getTgBatDauCaLam() {
        return tgBatDauCaLam;
    }

    public void setTgBatDauCaLam(String tgBatDauCaLam) {
        this.tgBatDauCaLam = tgBatDauCaLam;
    }

    public String getTgKetThucCaLam() {
        return tgKetThucCaLam;
    }

    public void setTgKetThucCaLam(String tgKetThucCaLam) {
        this.tgKetThucCaLam = tgKetThucCaLam;
    }

    public String getTongGioLam() {
        return tongGioLam;
    }

    public void setTongGioLam(String tongGioLam) {
        this.tongGioLam = tongGioLam;
    }

    public Double getLuongCaLam() {
        return luongCaLam;
    }

    public void setLuongCaLam(Double luongCaLam) {
        this.luongCaLam = luongCaLam;
    }

    public Double getLuong1GioLam() {
        return luong1GioLam;
    }

    public void setLuong1GioLam(Double luong1GioLam) {
        this.luong1GioLam = luong1GioLam;
    }
}
