package com.snksport.Entity;

import java.util.Date;

public class MatHang {

    String MaMH;
    String TenMH;
    int SoLuong;
    String Size;
    String MAU;
    String HinhAnh;
    double GiaTien;
    int MaNCC;
    int MaLMH;
    String MaND;
    Date NgayNhap;
    
    
    public String getMaMH() {
        return MaMH;
    }

    public void setMaMH(String MaMH) {
        this.MaMH = MaMH;
    }

    public String getTenMH() {
        return TenMH;
    }

    public void setTenMH(String TenMH) {
        this.TenMH = TenMH;
    }
    
    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getMAU() {
        return MAU;
    }

    public void setMAU(String MAU) {
        this.MAU = MAU;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(double GiaTien) {
        this.GiaTien = GiaTien;
    }

    public int getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(int MaNCC) {
        this.MaNCC = MaNCC;
    }

    public int getMaLMH() {
        return MaLMH;
    }

    public void setMaLMH(int MaLMH) {
        this.MaLMH = MaLMH;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }
    
    @Override
    public String toString() {
        return TenMH;
    }
}
