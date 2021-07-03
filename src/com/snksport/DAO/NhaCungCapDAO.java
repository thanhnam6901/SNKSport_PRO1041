/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.DAO;

import com.snksport.Entity.NhaCungCap;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThanhNam
 */
public class NhaCungCapDAO extends SNKSportDAO<NhaCungCap, Integer>{
    String INSERT_SQL = "INSERT INTO NhaCC(TenNCC,DienThoai, DiaChi) VALUES (?,?,?)";
    String UPDATE_SQL = "UPDATE NhaCC SET TenNCC = ?, DienThoai = ?, DiaChi = ? WHERE MaNCC = ?";
    String DELETE_SQL = "DELETE FROM NhaCC WHERE MaNCC = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhaCC";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhaCC WHERE MaNCC = ?";

    @Override
    public void insert(NhaCungCap entity) {
        Xjdbc.update(INSERT_SQL, entity.getTenNCC(), entity.getDienThoai(), entity.getDiaChi());
    }

    @Override
    public void update(NhaCungCap entity) {
        Xjdbc.update(UPDATE_SQL, entity.getTenNCC(), entity.getDienThoai(), entity.getDiaChi(), entity.getMaNCC());
    }

    @Override
    public void delete(Integer id) {
        Xjdbc.update(DELETE_SQL, id);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NhaCungCap selectByID(Integer id) {
        List<NhaCungCap> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NhaCungCap> selectBySQL(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<NhaCungCap>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(rs.getInt("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDienThoai(rs.getString("DienThoai"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                list.add(ncc);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public NhaCungCap selectByName(String name) {
        String sql = "SELECT * FROM NhaCC WHERE TenNCC = ?";
        List<NhaCungCap> list = this.selectBySQL(sql, name);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }
}
