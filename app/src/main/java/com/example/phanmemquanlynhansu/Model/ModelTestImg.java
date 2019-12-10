package com.example.phanmemquanlynhansu.Model;

public class ModelTestImg {
    String tenhinh,urlHinh;

    public ModelTestImg(String tenhinh, String urlHinh) {
        this.tenhinh = tenhinh;
        this.urlHinh = urlHinh;
    }

    public ModelTestImg() {
    }

    public String getTenhinh() {
        return tenhinh;
    }

    public void setTenhinh(String tenhinh) {
        this.tenhinh = tenhinh;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
    }
}
