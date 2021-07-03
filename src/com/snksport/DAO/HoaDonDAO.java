/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.HoaDon;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNam
 */
public class HoaDonDAO extends SNKSportDAO<HoaDon, String>{
    String INSERT_SQL = "INSERT INTO HoaDon(MaHD, MaKH, TongTien, MaND) VALUES (?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDon SET MaKH = ?, TongTien = ? WHERE MaHD = ?";
    String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD = ?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD = ?";


    @Override
    public void insert(HoaDon entity) {
        Xjdbc.update(INSERT_SQL,entity.getMaHD(), entity.getMaKH(), entity.getTongTien(), entity.getMaND());
    }

    @Override
    public void update(HoaDon entity) {
        Xjdbc.update(UPDATE_SQL, entity.getMaKH(), entity.getTongTien(), entity.getMaHD());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(DELETE_SQL, id);
    }
    
    public void deleteHDnull() {
        String sql1 = "DELETE ct FROM CTHoaDon ct, HoaDon hd where hd.TongTien = 0  and hd.MaHD = ct.MaHD";
        String sql2 = "DELETE FROM HoaDon WHERE TongTien = 0";
        Xjdbc.update(sql1);
        Xjdbc.update(sql2);
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<HoaDon> selectThang(int nam) {
        String sql = "select Distinct MONTH(NgayTao) from HoaDon where YEAR(NgayTao) = ?";
        return this.selectBySQL(sql, nam);
    }
    @Override
    public HoaDon selectByID(String id) {
        List<HoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<HoaDon> selectBySQL(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<HoaDon>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaKH(rs.getInt("MaKH"));
                hd.setTongTien(rs.getInt("TongTien"));
                hd.setMaND(rs.getString("MaND"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                list.add(hd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<HoaDon> selectByKeyword(String keyword){
        String sql = "SELECT * FROM HoaDon WHERE MaHD LIKE ?";
        return this.selectBySQL(sql, "%" + keyword + "%");
    }
    
    public List<HoaDon> selectHoaDon(String keyword, String month, String year) {
        String sql = "select * from HoaDon where MaHD like ? AND MONTH(NgayTao) like ? AND YEAR(NgayTao) like ?";
        return this.selectBySQL(sql, "%" + keyword + "%",month, "%" + year + "%");
    }
  
}
