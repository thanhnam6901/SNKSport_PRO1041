/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.MatHang;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNam
 */
public class MatHangDAO extends SNKSportDAO<MatHang, String> {

    String INSERT_SQL_MatHang = "INSERT INTO MatHang(MaMH, TenMH, MAU, SoLuong, Size, HinhAnh, GiaTien, MaNCC, MaLMH, MaND) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL_MatHang = "UPDATE MatHang SET TenMH = ?, MAU = ?, SoLuong = ?, Size = ?, HinhAnh = ?, GiaTien = ?, MaNCC = ?, MaLMH = ? WHERE MaMH = ?";
    String DELETE_SQL_MatHang = "DELETE from MatHang WHERE MaMH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM MatHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM MatHang WHERE MaMH = ? ";
   

    @Override
    public void insert(MatHang entity) {
        Xjdbc.update(INSERT_SQL_MatHang,
                entity.getMaMH(), entity.getTenMH(), entity.getMAU(), entity.getSoLuong(), entity.getSize(),
                entity.getHinhAnh(), entity.getGiaTien(), entity.getMaNCC(), entity.getMaLMH(), entity.getMaND());
    }

    @Override
    public void update(MatHang entity) {
        Xjdbc.update(UPDATE_SQL_MatHang,
                entity.getTenMH(), entity.getMAU(), entity.getSoLuong(), entity.getSize(), entity.getHinhAnh(),
                entity.getGiaTien(), entity.getMaNCC(), entity.getMaLMH(), entity.getMaMH());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(DELETE_SQL_MatHang, id);
    }

    public void deleteChiTietNull() {
        String sql = "DELETE from ChiTietMatHang WHERE Size = 'SZ'";
        Xjdbc.update(sql);
    }

    @Override
    public List<MatHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public MatHang selectByID(String id) {
        List<MatHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

//    public MatHang Select_By_SL(String id) {
//    String Select_By_SL = "select * from ChiTietMatHang where MaMH = ?";
//        List<MatHang> list = this.selectBySQL(Select_By_SL, id);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.size() > 0 ? list.get(0) : null;
//    }
    @Override
    protected List<MatHang> selectBySQL(String sql, Object... args) {
        List<MatHang> list = new ArrayList<MatHang>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                MatHang mh = new MatHang();
                mh.setMaMH(rs.getString("MaMH"));
                mh.setTenMH(rs.getString("TenMH"));
                mh.setMAU(rs.getString("MAU"));
                mh.setSoLuong(rs.getInt("SoLuong"));
                mh.setSize(rs.getString("Size"));
                mh.setHinhAnh(rs.getString("HinhAnh"));
                mh.setGiaTien(rs.getDouble("GiaTien"));
                mh.setMaNCC(rs.getInt("MaNCC"));
                mh.setMaLMH(rs.getInt("MaLMH"));
                mh.setMaND(rs.getString("MaND"));
                mh.setNgayNhap(rs.getDate("NgayNhap"));
                list.add(mh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MatHang> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM MatHang where TenMH like ? ORDER BY SoLuong ASC";
        return this.selectBySQL(sql, "%" + keyword + "%");
    }

    public List<MatHang> selectSizeByIDandName(String id, String tenmh) {
        String sql = "SELECT * FROM MatHang where TenMH like ?";
        return this.selectBySQL(sql, "%" + id + "%", "%" + tenmh + "%");
    }

    public List<MatHang> selectTenMatHangByLMH(String tenlmh, String keyword) {
        String sql = "select * from MatHang where TenMH like ? AND MaLMH in (select MaLMH from LoaiMH where TenLMH LIKE ?) ";
        return this.selectBySQL(sql, "%" + keyword + "%", tenlmh);
    }

}
