package com.snksport.DAO;

import com.snksport.Entity.NguoiDung;
import com.snksport.Utils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO extends SNKSportDAO<NguoiDung, String>{
    String INSERT_SQL = "INSERT INTO NguoiDung(MaND, MatKhau, HoTen, GioiTinh, NgaySinh, DienThoai, QueQuan, Luong, VaiTro) VALUES (?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NguoiDung SET HoTen = ?, GioiTinh = ?, MatKhau = ?, NgaySinh = ?, DienThoai = ?, QueQuan = ?, Luong = ?, VaiTro = ? WHERE MaND = ?";
    String DELETE_SQL = "DELETE FROM NguoiDung WHERE MaND = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NguoiDung";
    String SELECT_BY_ID_SQL = "SELECT * FROM NguoiDung WHERE MaND = ?";

    @Override
    public void insert(NguoiDung entity) {
        Xjdbc.update(INSERT_SQL, 
                entity.getMaND(), entity.getMatKhau(), entity.getHoTen(), entity.isGioiTinh(), 
                new java.sql.Date(entity.getNgaySinh().getTime()), entity.getDienThoai(), entity.getQueQuan(), entity.getLuong(), 
                entity.isVaiTro());
    }

    @Override
    public void update(NguoiDung entity) {
        Xjdbc.update(UPDATE_SQL, entity.getHoTen(), entity.isGioiTinh(),entity.getMatKhau(), 
                new java.sql.Date(entity.getNgaySinh().getTime()), entity.getDienThoai(), entity.getQueQuan(), entity.getLuong(), entity.isVaiTro(), entity.getMaND());
    }

    @Override
    public void delete(String id) {
        Xjdbc.update(DELETE_SQL, id);
    }

    @Override
    public List<NguoiDung> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NguoiDung selectByID(String id) {
        List<NguoiDung> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NguoiDung> selectBySQL(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<NguoiDung>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while(rs.next()){
                NguoiDung nd = new NguoiDung();
                nd.setMaND(rs.getString("MaND"));
                nd.setMatKhau(rs.getString("MatKhau"));
                nd.setHoTen(rs.getString("HoTen"));
                nd.setGioiTinh(rs.getBoolean("GioiTinh"));
                nd.setNgaySinh(rs.getDate("NgaySinh"));
                nd.setDienThoai(rs.getString("DienThoai"));
                nd.setQueQuan(rs.getString("QueQuan"));
                nd.setLuong(rs.getDouble("Luong"));
                nd.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(nd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
