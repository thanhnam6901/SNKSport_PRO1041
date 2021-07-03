/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.LoaiMatHang;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNam
 */
public class LoaiMatHangDAO extends SNKSportDAO<LoaiMatHang, Integer>{
    String INSERT_SQL = "INSERT INTO LoaiMH(TenLMH,MoTa) VALUES (?,?)";
    String UPDATE_SQL = "UPDATE LoaiMH SET TenLMH = ?, MoTa = ? WHERE MaLMH = ?";
    String DELETE_SQL = "DELETE FROM LoaiMH WHERE MaLMH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiMH";
    String SELECT_BY_ID_SQL = "SELECT * FROM LoaiMH WHERE MaLMH = ?";

    @Override
    public void insert(LoaiMatHang entity) {
        Xjdbc.update(INSERT_SQL, entity.getTenLMH(), entity.getMoTa());
    }

    @Override
    public void update(LoaiMatHang entity) {
        Xjdbc.update(UPDATE_SQL, entity.getTenLMH(), entity.getMoTa(), entity.getMaLMH());
    }

    @Override
    public void delete(Integer id) {
        Xjdbc.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiMatHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public LoaiMatHang selectByID(Integer id) {
        List<LoaiMatHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<LoaiMatHang> selectBySQL(String sql, Object... args) {
        List<LoaiMatHang> list = new ArrayList<LoaiMatHang>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                LoaiMatHang lmh = new LoaiMatHang();
                lmh.setMaLMH(rs.getInt("MaLMH"));
                lmh.setTenLMH(rs.getString("TenLMH"));
                lmh.setMoTa(rs.getString("MoTa"));
                list.add(lmh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public LoaiMatHang selectByName(String name) {
        String sql = "SELECT * FROM LoaiMH WHERE TenLMH = ?";
        List<LoaiMatHang> list = this.selectBySQL(sql, name);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }
}
