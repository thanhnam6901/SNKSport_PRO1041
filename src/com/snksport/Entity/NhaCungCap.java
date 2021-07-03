package com.snksport.Entity;

public class NhaCungCap {

    int MaNCC;
    String TenNCC;
    String DienThoai;
    String DiaChi;

    public int getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(int MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String TenNCC) {
        this.TenNCC = TenNCC;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

//    @Override
//    public boolean equals(Object obj) {
//        NhaCungCap other = (NhaCungCap) obj;
//        return other.getTenNCC().equals(this.getTenNCC());
//    }

    @Override
    public String toString() {
        return TenNCC;
    }
}
