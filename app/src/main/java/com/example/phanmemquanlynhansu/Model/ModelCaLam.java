package com.example.phanmemquanlynhansu.Model;

import java.io.Serializable;

public class ModelCaLam implements Serializable {
    String id;
    String maCaLam, tenCaLam, tgBatDauCaLam, tgKetThucCaLam, tongGioLam;
    double luongCaLam, luong1GioLam;

    @Override
    public String toString() {
        return "ModelCaLam{" +
                "id='" + id + '\'' +
                ", maCaLam='" + maCaLam + '\'' +
                ", tenCaLam='" + tenCaLam + '\'' +
                ", tgBatDauCaLam='" + tgBatDauCaLam + '\'' +
                ", tgKetThucCaLam='" + tgKetThucCaLam + '\'' +
                ", tongGioLam='" + tongGioLam + '\'' +
                ", luongCaLam=" + luongCaLam +
                ", luong1GioLam=" + luong1GioLam +
                '}';
    }

    public ModelCaLam() {

    }

    public ModelCaLam(String tenCaLam) {
        this.tenCaLam = tenCaLam;
    }

    public ModelCaLam(String maCaLam, String tenCaLam, String tgBatDauCaLam, String tgKetThucCaLam, String tongGioLam, double luongCaLam, double luong1GioLam) {
        this.maCaLam = maCaLam;
        this.tenCaLam = tenCaLam;
        this.tgBatDauCaLam = tgBatDauCaLam;
        this.tgKetThucCaLam = tgKetThucCaLam;
        this.tongGioLam = tongGioLam;
        this.luongCaLam = luongCaLam;
        this.luong1GioLam = luong1GioLam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getLuongCaLam() {
        return luongCaLam;
    }

    public void setLuongCaLam(double luongCaLam) {
        this.luongCaLam = luongCaLam;
    }

    public double getLuong1GioLam() {
        return luong1GioLam;
    }

    public void setLuong1GioLam(double luong1GioLam) {
        this.luong1GioLam = luong1GioLam;
    }
}
