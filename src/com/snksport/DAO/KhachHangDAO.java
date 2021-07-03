/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.KhachHang;
import com.snksport.UI.BanHangJframe;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNam
 */
public class KhachHangDAO extends SNKSportDAO<KhachHang, Integer> {

    String INSERT_SQL = "INSERT INTO KhachHang(TenKH,DienThoai, DiaChi) VALUES (?,?,?)";
    String UPDATE_SQL = "UPDATE KhachHang SET TenKH = ?, DienThoai = ?, DiaChi = ? WHERE MaKH = ?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKH = ?";
  
    @Override
    public void insert(KhachHang entity) {
        Xjdbc.update(INSERT_SQL, entity.getTenKH(), entity.getDienThoai(), entity.getDiaChi());
    }

    @Override
    public void update(KhachHang entity) {
        Xjdbc.update(UPDATE_SQL, entity.getTenKH(), entity.getDienThoai(), entity.getDiaChi(), entity.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        Xjdbc.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectByID(Integer id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<KhachHang>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setDienThoai(rs.getString("DienThoai"));
                kh.setDiaChi(rs.getString("DiaChi"));
                list.add(kh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public KhachHang selectByName(String name) {
        String sql = "SELECT * FROM KhachHang WHERE TenKH = ?";
        List<KhachHang> list = this.selectBySQL(sql, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public KhachHang selectSDTKH(String sdt) {
        String sql = "select * from KhachHang where DienThoai = ?";
        List<KhachHang> list = this.selectBySQL(sql, sdt);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<KhachHang> selectByKeyword(String keyword) {
        String sql = "select * from KhachHang where TenKH LIKE ?";
        return this.selectBySQL(sql, "%" + keyword + "%");
    }
}
