package com.snksport.Entity;

public class LoaiMatHang {

    int MaLMH;
    String TenLMH;
    String MoTa;

    public int getMaLMH() {
        return MaLMH;
    }

    public void setMaLMH(int MaLMH) {
        this.MaLMH = MaLMH;
    }

    public String getTenLMH() {
        return TenLMH;
    }

    public void setTenLMH(String TenLMH) {
        this.TenLMH = TenLMH;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    
//    @Override
//    public boolean equals(Object obj) {
//        LoaiMatHang other = (LoaiMatHang) obj;
//        return other.getTenLMH().equals(this.getTenLMH());
//    }
    
    @Override
    public String toString() {
        return TenLMH;
    }
}
