/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.CTHoaDon;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNam
 */
public class HoaDonChiTietDAO extends SNKSportDAO<CTHoaDon, String> {

    String INSERT_SQL = "INSERT INTO CTHoaDon(MaHD, TenMH, SoLuong, Size, MAU,GiaTien, ThanhTien, MaMH) VALUES (?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE CTHoaDon SET SoLuong = ?, giaTien = ?, ThanhTien = ? WHERE MaMH = ? and MaHD = ?";
    String DELETE_SQL = "DELETE FROM CTHoaDon WHERE MaHD = ?";
    String SELECT_ALL_SQL = "SELECT * FROM CTHoaDon";
    String SELECT_BY_ID_SQL = "SELECT * FROM CTHoaDon WHERE MaHD = ?";

    public void insert(CTHoaDon entity) {
        Xjdbc.update(INSERT_SQL, entity.getMaHD(), entity.getTenMH(), entity.getSoLuong(), entity.getSize(),
                entity.getMAU(), entity.getGiaTien(), entity.getThanhTien(), entity.getMaMH());
    }

    @Override
    public void update(CTHoaDon entity) {
        Xjdbc.update(UPDATE_SQL, entity.getSoLuong(), entity.getGiaTien(), entity.getThanhTien(), entity.getMaMH(), entity.getMaHD());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(DELETE_SQL, id);
    }

    public void deleteMatHang(String id) {
        String sql = "DELETE FROM CTHoaDon WHERE MaMH = ?";
        Xjdbc.update(sql, id);
    }

    @Override
    public List<CTHoaDon> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public CTHoaDon selectByID(String id) {
        List<CTHoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public CTHoaDon selectSoLuongOld(String mahd, String mamh) {
        String sql = "select * from CTHoaDon where MaHD = ? and MaMH = ?";
        List<CTHoaDon> list = this.selectBySQL(sql, mahd, mamh);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<CTHoaDon> selectProductsByID(String id) {
        return this.selectBySQL(SELECT_BY_ID_SQL, id);
    }

    @Override
    protected List<CTHoaDon> selectBySQL(String sql, Object... args) {
        List<CTHoaDon> list = new ArrayList<CTHoaDon>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                CTHoaDon cthd = new CTHoaDon();
                cthd.setMaHD(rs.getString("MaHD"));
                cthd.setTenMH(rs.getString("TenMH"));
                cthd.setSoLuong(rs.getInt("SoLuong"));
                cthd.setSize(rs.getString("Size"));
                cthd.setMAU(rs.getString("MAU"));
                cthd.setGiaTien(rs.getDouble("GiaTien"));
                cthd.setThanhTien(rs.getDouble("ThanhTien"));
                cthd.setMaMH(rs.getString("MaMH"));
                cthd.setNgayTao(rs.getDate("NgayTao"));
                list.add(cthd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CTHoaDon> selectTongSL() {
        List<CTHoaDon> list = new ArrayList<CTHoaDon>();
        try {
            String sql = "select lmh.TenLMH, COUNT(ct.TenMH) as TongSL from CTHoaDon ct \n"
                    + "JOIN MatHang mh ON ct.MaMH = mh.MaMH\n"
                    + "JOIN LoaiMH lmh ON mh.MaLMH = lmh.MaLMH\n"
                    + "GROUP BY lmh.TenLMH";
            ResultSet rs = Xjdbc.query(sql);
            while (rs.next()) {
                CTHoaDon cthd = new CTHoaDon();
                cthd.setTenLMH(rs.getString("TenLMH"));
                cthd.setTong(rs.getInt("TongSL"));
                list.add(cthd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
