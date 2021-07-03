/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.ThongKe;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manh
 */
public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                Object[] vals = new Object[cols.length];
                for(int i = 0; i < cols.length; i++){
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

    public List<Object[]> getKhachHangTT(){
        String sql = "{CALL sp_KhachHangThanThiet}";
        String[] cols = {"TenKH", "DienThoai", "DiaChi", "soLuongMua"};
        return this.getListOfArray(sql, cols);
    }
    
    public List<Object[]> getSPBanChay(int thang ,int nam){
        String sql = "{CALL sp_ThongKeBanChay (?,?)}";
        String[] cols = {"MaMH", "TenMH", "TenLMH", "GiaTien", "SoLuongDaBan"};
        return this.getListOfArray(sql, cols,thang, nam);
    }
    
    public List<Object[]> getDoanhThu(int nam){
        String sql = "{CALL sp_ThongKeDoanhThu(?)}";
        String[] cols = {"Thang","TongSoHD", "ThapNhat", "CaoNhat", "TrungBinh", "DoanhThu"};
        return this.getListOfArray(sql, cols, nam);
    }
   
   
   
}
