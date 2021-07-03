/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.Entity;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class ThongKe {
    public String MaND;
    public String HoTen;
    Date NgaySinh;
    public String QueQuan;
    public String VaiTro;
    public double Luong;
    public double Thuong;
    public double ThucLinh;

    public ThongKe(double Thuong, double ThucLinh) {
        this.Thuong = Thuong;
        this.ThucLinh = ThucLinh;
    }

    public ThongKe(){
        
    }
    public ThongKe(String MaND, String HoTen, Date NgaySinh, String QueQuan, String VaiTro, double Luong, double Thuong, double ThucLinh) {
        this.MaND = MaND;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.QueQuan = QueQuan;
        this.VaiTro = VaiTro;
        this.Luong = Luong;
        this.Thuong = Thuong;
        this.ThucLinh = ThucLinh;
    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String MaND) {
        this.MaND = MaND;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String QueQuan) {
        this.QueQuan = QueQuan;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(String VaiTro) {
        this.VaiTro = VaiTro;
    }

    public double getLuong() {
        return Luong;
    }

    public void setLuong(double Luong) {
        this.Luong = Luong;
    }

    public double getThuong() {
        return Thuong;
    }

    public void setThuong(double Thuong) {
        this.Thuong = Thuong;
    }

    public double getThucLinh() {
        return ThucLinh;
    }

    public void setThucLinh(double ThucLinh) {
        this.ThucLinh = ThucLinh;
    }
    
    
}
