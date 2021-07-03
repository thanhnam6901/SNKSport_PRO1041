/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.UI;

import com.snksport.DAO.HoaDonChiTietDAO;
import com.snksport.DAO.HoaDonDAO;
import com.snksport.DAO.KhachHangDAO;
import com.snksport.DAO.LoaiMatHangDAO;
import com.snksport.DAO.MatHangDAO;
import com.snksport.DAO.NhaCungCapDAO;
import com.snksport.Entity.MatHang;
import com.snksport.Entity.CTHoaDon;
import com.snksport.Entity.HoaDon;
import com.snksport.Entity.KhachHang;
import com.snksport.Entity.LoaiMatHang;
import com.snksport.Utils.Auth;
import com.snksport.Utils.MsgBox;
import com.snksport.Utils.XDate;
import com.snksport.Utils.XImage;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ThanhNam
 */
public class BanHangJframe extends javax.swing.JInternalFrame {

    /**
     * Creates new form BanHangJframe
     */
    public static BanHangJframe it;

    public BanHangJframe() {
        it = this;
        initComponents();

        tabs.setSelectedIndex(1);
        tabs.setSelectedIndex(1);
        lblMaND.setText(Auth.user.getHoTen());
        btnThemSP.setEnabled(false);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnThemKH.setEnabled(false);
        btnXoaKH.setEnabled(false);
        btnSuaKH.setEnabled(false);
        lblMaHD.setText("...");
        fillComboBoxLoaiMH();
        fillComboboxSDT();
        fillTableKhachHang();
        fillTableMatHang();
        AutoCompleteDecorator.decorate(cboLoaiMatHang);
        AutoCompleteDecorator.decorate(cboSDT);

//        Status();
    }

    List<CTHoaDon> listCTHD = new ArrayList<>();
    MatHangDAO mhdao = new MatHangDAO();
    HoaDonDAO hddao = new HoaDonDAO();
    HoaDonChiTietDAO cthddao = new HoaDonChiTietDAO();
    int index = -1;
    DefaultTableModel model;
    NhaCungCapDAO nccdao = new NhaCungCapDAO();
    LoaiMatHangDAO lmhdao = new LoaiMatHangDAO();
    KhachHangDAO khdao = new KhachHangDAO();

    void fillComboBoxLoaiMH() {
        DefaultComboBoxModel CboModel = (DefaultComboBoxModel) cboLoaiMatHang.getModel();
        CboModel.removeAllElements();
        List<LoaiMatHang> list = lmhdao.selectAll();
        for (LoaiMatHang lmh : list) {
            CboModel.addElement(lmh);
        }
    }

    void fillComboboxSDT() {
//        DefaultComboBoxModel CboModel = (DefaultComboBoxModel) cboSDT.getModel();
        cboSDT.setModel(new DefaultComboBoxModel());
//        CboModel.removeAllElements();
        List<KhachHang> list = khdao.selectAll();
        for (KhachHang kh : list) {
//            CboModel.addElement(kh);
            cboSDT.addItem(kh.toString());
        }
    }

    void fillTableMatHang() {
        try {
            model = (DefaultTableModel) tblMatHang.getModel();
            model.setRowCount(0);
            List<MatHang> list = mhdao.selectTenMatHangByLMH(cboLoaiMatHang.getSelectedItem().toString(), txtTimKiemMH.getText());//doc du lieu tu CSDL
            for (int i = 0; i < list.size(); i++) {
                MatHang mh = list.get(i);
                String tenncc = nccdao.selectByID(mh.getMaNCC()).getTenNCC();
                String tenlmh = lmhdao.selectByID(mh.getMaLMH()).getTenLMH();
                if (mh.getSoLuong() > 0) {
                    model.addRow(new Object[]{
                        mh.getMaMH(),
                        mh.getTenMH(),
                        mh.getMAU(),
                        mh.getSize(),
                        mh.getSoLuong(),
                        mh.getGiaTien(),
                        tenlmh,
                        tenncc
                    }); //them 1 hang vao table
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillTableGioHang() {
        model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
        listCTHD = (List<CTHoaDon>) cthddao.selectProductsByID(lblMaHD.getText().trim());
        for (CTHoaDon cthd : listCTHD) {
            String mamh = cthd.getMaMH();
            String tenmh = cthd.getTenMH();
            String size = cthd.getSize();
            String MAU = cthd.getMAU();
            int soLuong = cthd.getSoLuong();
            double giaTien = cthd.getGiaTien();
            int thanhTien = (int) (giaTien * soLuong);
            model.addRow(new Object[]{
                mamh,
                tenmh,
                size,
                MAU,
                soLuong,
                giaTien,
                thanhTien
            });
        }
        tinhTong();
    }

    public void tinhTong() {
        int tong = 0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            int tt = (int) tblGioHang.getValueAt(i, 6);
            tong += tt;

        }

        lblTongTien.setText(String.valueOf(tong));
    }
    int soLuong, si;

    boolean checkForm(boolean chk) {
        if (chk) {
            List<HoaDon> listHD = hddao.selectAll();
            for (HoaDon hd : listHD) {
                if (hd.getMaHD().trim().equalsIgnoreCase(lblMaHD.getText().trim())) {
                    MsgBox.alert(this, "Da co hoan don!");
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkKH(boolean chk) {

        List<KhachHang> list = khdao.selectAll();
        if (chk) {
            for (KhachHang kh : list) {
                if (txtSDT1.getText().equalsIgnoreCase(kh.getDienThoai())) {
                    MsgBox.alert(this, "Đã có người sử dụng số điện thoại này");
                    txtSDT1.requestFocus();
                    return false;
                }
            }
        }

        if (txtTenKH1.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống họ tên!");
            txtTenKH1.requestFocus();
            return false;
        } else if (!txtTenKH1.getText().matches("[^0-9]+")) {
            MsgBox.alert(this, "Trong họ tên không được có số!");
            txtTenKH1.requestFocus();
            return false;
        } else if (txtSDT1.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống số điện thoại!");
            txtSDT1.requestFocus();
            return false;
        } else if (!txtSDT1.getText().matches("((84)|(0))\\d{9}")) {
            MsgBox.alert(this, "Không đúng định dạng số điện thoại!");
            txtSDT1.requestFocus();
            return false;
        } else if (txtDiaChi1.getText().length() == 0) {
            MsgBox.alert(this, "Không được phép để trống địa chỉ!");
            txtDiaChi1.requestFocus();
            return false;
        }
        return true;
    }

    void updateHoaDon() {
        String mahd = lblMaHD.getText();
        String tongTien = lblTongTien.getText();

        HoaDon hd = hddao.selectByID(mahd);
        hd.setMaKH(Integer.parseInt(lblMaKH.getText()));
        hd.setTongTien(Double.parseDouble(tongTien));
        hddao.update(hd);
    }

    void insert() {
        try {
            String mand = lblMaND.getText();
            String mahd = lblMaHD.getText();
            String mamh = txtMaMH.getText();
//            String tenmh = (String) cboTenMatHang.getSelectedItem().toString();//.split(" - ")[0].trim();
//            String size = (String) cboSize.getSelectedItem().toString();//.split(" - ")[1].trim();
            String MAU = txtMau.getText();
//            soLuong = (int) spnSoLuong.getValue();
            double giaTien = Double.parseDouble(txtGiaTien.getText());
            double thanhTien = soLuong * giaTien;
            Date NgayTao = XDate.toDate(lblNgayTao.getText());
            //insert vao table chi tiet hoa don
            CTHoaDon cthd = new CTHoaDon();
            try {
                cthd.setMaHD(mahd);
                cthd.setMaMH(mamh);
//                cthd.setTenMH(tenmh);
                cthd.setSoLuong(soLuong);
                cthd.setMAU(MAU);
//                cthd.setSize(size);
                cthd.setGiaTien(giaTien);
                cthd.setThanhTien(thanhTien);
                cthd.setNgayTao(NgayTao);
                cthddao.insert(cthd);

            } catch (Exception e) {
                MsgBox.alert(this, "Mặt hàng này đã có trong giỏ hàng!!!");
//                for (int i = 0; i < tblGioHang.getRowCount(); i++) {
//                int sLgOld = cthddao.selectSoLuongOld(mahd, mamh).getSoLuong();
//                int sLgNew = sLgOld + soLuong;
//                cthd.setSoLuong(sLgNew);
//
//                cthddao.update(cthd);
//                }
            }

            fillTableGioHang();
            tinhTong();
            tabs.setSelectedIndex(1);
//            cboTenMatHang.setSelectedIndex(0);
            btnThem.setEnabled(false);
            index = tblGioHang.getRowCount();
//            Status();

//            MsgBox.alert(this, "Thêm mặt hàng công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm sản phẩm vào giỏ hàng thất bại" + e);
        }
    }

    public void delete() {
        try {
            index = tblGioHang.getSelectedRow();
            if (index < 0) {
                MsgBox.alert(this, "Bạn chưa chọn mặt hàng muốn xóa!");
                return;
            } else {
                if (MsgBox.confirm(this, "Bạn có muốn xóa mặt hàng được chọn?")) {
                    String mamh = tblGioHang.getValueAt(index, 0).toString();
                    cthddao.deleteMatHang(mamh);
                    fillTableGioHang();
                    tinhTong();
                    MsgBox.alert(this, "Xóa mặt hàng công!");
                    btnSua.setEnabled(false);
                    btnXoa.setEnabled(false);
                    btnThanhToan.setEnabled(true);
                }
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi Xóa");
        }
    }

    public void update() {
        try {
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                String mahd = lblMaHD.getText();
                String mamh = (String) tblGioHang.getValueAt(i, 0);
                soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 4).toString());
                double giaTien = Double.parseDouble(tblGioHang.getValueAt(i, 5).toString());
                double thanhTien = soLuong * giaTien;

                si = Integer.parseInt(txtSLConLai.getText());
                if (soLuong > si) {
                    MsgBox.alert(this, "Số lượng mặt hàng trong kho không đủ để thêm vào giỏ hàng!");
                    double slnew = (thanhTien / giaTien);
                    fillTableGioHang();
                    btnSua.setEnabled(false);
                    btnXoa.setEnabled(false);
                    btnThanhToan.setEnabled(true);
                    return;
                } else {
                    CTHoaDon cthd = cthddao.selectByID(mahd);
                    cthd.setMaHD(mahd);
                    cthd.setMaMH(mamh);
                    cthd.setSoLuong(soLuong);
                    cthd.setGiaTien(giaTien);
                    cthd.setThanhTien(thanhTien);
                    cthddao.update(cthd);
                }

            }
            fillTableGioHang();
            tinhTong();
            MsgBox.alert(this, "Cập nhật số lượng thành công!");
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnThanhToan.setEnabled(true);
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật số lượng thất bại!");
        }
    }

    public void newForm() {
        hieuUng();
        model = (DefaultTableModel) tblGioHang.getModel();
        index = -1;
        model.setRowCount(0);
        btnThemSP.setEnabled(true);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        lblTongTien.setText("0");
//        fillComboboxSDT();
//        Status();
        //insert vao table hoa don

        if (checkForm(true)) {
            HoaDon hd = new HoaDon();
            hd.setMaHD(lblMaHD.getText().trim());
            hd.setMaKH(Integer.parseInt(lblMaKH.getText()));
            hd.setMaND(Auth.user.getMaND());
            hd.setNgayTao(XDate.toDate(lblNgayTao.getText()));
            hddao.insert(hd);
        }
    }

//    void Status() {
//        boolean edit = (this.index >= 0);
//        //Trang thai form
//        btnSua.setEnabled(edit);
//        btnXoa.setEnabled(edit);
//        btnThanhToan.setEnabled(edit);
//        btnThemKH.setEnabled(!edit);
//        btnSuaKH.setEnabled(edit);
//        btnXoakH.setEnabled(edit);
//    }
    void clearFromMH() {
        index = -1;
        txtMaMH.setText("");
        txtTenMH.setText("");
        txtMau.setText("");
        txtSize.setText("");
        txtSLConLai.setText("");
        txtGiaTien.setText("");
        txtSoLuongThem.setText("0");
//        Status();
    }

    void edit() {
        String mamh = (String) tblMatHang.getValueAt(this.index, 0);
        MatHang mh = mhdao.selectByID(mamh);
        this.setForm(mh);
//        this.Status();
    }

    void setForm(MatHang mh) {
        txtMaMH.setText(mh.getMaMH());
        txtTenMH.setText(mh.getTenMH());
        txtMau.setText(mh.getMAU());
        txtSize.setText(mh.getSize());
        txtSLConLai.setText(Integer.toString(mh.getSoLuong()));
        txtGiaTien.setText(Double.toString(mh.getGiaTien()));
    }

    public void thanhToanThanhCong() {
        model = (DefaultTableModel) tblGioHang.getModel();
        index = -1;
        cboGiamGia.setSelectedIndex(0);
        model.setRowCount(0);
        cboSDT.setSelectedIndex(0);
        btnThemSP.setEnabled(false);
        btnThem.setEnabled(false);
        lblTongTien.setText("0");
        lblMaHD.setText("...");
//        Status();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTieuDe = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGiaTien = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtMau = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtMaMH = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTenMH = new javax.swing.JTextField();
        txtSize = new javax.swing.JTextField();
        txtSLConLai = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSoLuongThem = new javax.swing.JTextField();
        cboLoaiMatHang = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        txtTimKiemMH = new javax.swing.JTextField();
        btnTimKiemMH = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMatHang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnTaoMoi = new javax.swing.JButton();
        btnThemSP = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        cboGiamGia = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        cboSDT = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblMaND = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtTenKH1 = new javax.swing.JTextField();
        txtSDT1 = new javax.swing.JTextField();
        txtDiaChi1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        lblMaKH1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnThemKH = new javax.swing.JButton();
        btnSuaKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnTaoMoiKH = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        txtTimKiemKH = new javax.swing.JTextField();
        btnTimKiemKH = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setTitle("SNKSport - Bán Hàng");

        lblTieuDe.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTieuDe.setText("QUẢN LÝ BÁN HÀNG");

        tabs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Mã Mặt Hàng");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Màu");

        txtGiaTien.setEditable(false);
        txtGiaTien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("SL Còn Lại");

        txtMau.setEditable(false);
        txtMau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMau.setToolTipText("");
        txtMau.setName(""); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Giá Tiền");

        txtMaMH.setEditable(false);
        txtMaMH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Tên Mặt Hàng");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Size");

        txtTenMH.setEditable(false);
        txtTenMH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtSize.setEditable(false);
        txtSize.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSize.setToolTipText("");
        txtSize.setName(""); // NOI18N

        txtSLConLai.setEditable(false);
        txtSLConLai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSLConLai.setToolTipText("");
        txtSLConLai.setName(""); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Số Lượng");

        txtSoLuongThem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSoLuongThem.setText("0");
        txtSoLuongThem.setToolTipText("");
        txtSoLuongThem.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuongThem)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel7)
                                .addComponent(txtMau)
                                .addComponent(txtTenMH)
                                .addComponent(txtMaMH)
                                .addComponent(txtSize, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                .addComponent(txtSLConLai))
                            .addComponent(jLabel13)
                            .addComponent(jLabel17))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaMH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenMH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSLConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuongThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtMau.getAccessibleContext().setAccessibleName("");

        cboLoaiMatHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboLoaiMatHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoaiMatHangItemStateChanged(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setText("Loại Mặt Hàng");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThem.setBackground(new java.awt.Color(204, 255, 255));
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThem.setText("Thêm vào giỏ hàng");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel16.setToolTipText("");
        jPanel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtTimKiemMH.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtTimKiemMH.setToolTipText("");
        txtTimKiemMH.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        btnTimKiemMH.setBackground(new java.awt.Color(153, 255, 255));
        btnTimKiemMH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTimKiemMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tim_kiem.png"))); // NOI18N
        btnTimKiemMH.setText("Tìm");
        btnTimKiemMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemMHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemMH, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiemMH, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiemMH, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtTimKiemMH, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblMatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã MH", "Tên MH", "Màu", "Size", "SL Còn Lại", "Giá Tiền", "Tên LMH", "Tên NCC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMatHangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblMatHang);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cboLoaiMatHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLoaiMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Sản Phẩm", jPanel1);

        tblGioHang.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã MH", "Tên Mặt Hàng", "Size", "Màu", "Số Lượng", "Giá Tiền", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGioHang);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTaoMoi.setBackground(new java.awt.Color(153, 255, 255));
        btnTaoMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaoMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Refresh.png"))); // NOI18N
        btnTaoMoi.setText("Tạo Mới");
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        btnThemSP.setBackground(new java.awt.Color(153, 255, 255));
        btnThemSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add to basket.png"))); // NOI18N
        btnThemSP.setText("Thêm Sản Phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(204, 255, 255));
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(204, 255, 255));
        btnSua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sua.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(btnTaoMoi)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnSua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoMoi)
                    .addComponent(btnThemSP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Tổng Tiền: ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("VNĐ");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTongTien.setText("...");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblTongTien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTongTien))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        cboGiamGia.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cboGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "5", "10", "15", "20" }));
        cboGiamGia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboGiamGiaItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Giảm Giá Hóa Đơn:");

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThanhToan.setBackground(new java.awt.Color(153, 255, 255));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Payment.png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Tên Khách Hàng");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Địa Chỉ");

        txtTenKH.setEditable(false);
        txtTenKH.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txtDiaChi.setEditable(false);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Mã Khách Hàng:");

        lblMaKH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaKH.setText("...");

        cboSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboSDT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSDTItemStateChanged(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Số Điện Thoại");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblMaKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cboSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblMaKH)
                    .addComponent(jLabel6)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("%");

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Mã Hóa Đơn:");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaHD.setText("...");

        lblMaND.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaND.setText("...");

        lblNgayTao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNgayTao.setText("...");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Ngày Tạo:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tên Người Dùng:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaND))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNgayTao))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaHD)))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblMaHD))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblMaND))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNgayTao))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cboGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabs.addTab("Giỏ Hàng", jPanel2);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Mục Khách Hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("Tên Khách Hàng");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("Số Điện Thoại");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel22.setText("Địa Chỉ");

        txtTenKH1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txtSDT1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        txtDiaChi1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("Mã Khách Hàng:");

        lblMaKH1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaKH1.setText("...");

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThemKH.setBackground(new java.awt.Color(204, 255, 255));
        btnThemKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThemKH.setText("Thêm");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnSuaKH.setBackground(new java.awt.Color(204, 255, 255));
        btnSuaKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSuaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sua.png"))); // NOI18N
        btnSuaKH.setText("Sửa");
        btnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(204, 255, 255));
        btnXoaKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoaKH.setText("Xóa");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnTaoMoiKH.setBackground(new java.awt.Color(204, 255, 255));
        btnTaoMoiKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaoMoiKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/new.png"))); // NOI18N
        btnTaoMoiKH.setText("Mới");
        btnTaoMoiKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSuaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(btnTaoMoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoMoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(lblMaKH1))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenKH1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(txtDiaChi1)
                            .addComponent(txtSDT1))))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(lblMaKH1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtTenKH1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel15.setToolTipText("");
        jPanel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtTimKiemKH.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtTimKiemKH.setToolTipText("");
        txtTimKiemKH.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        btnTimKiemKH.setBackground(new java.awt.Color(153, 255, 255));
        btnTimKiemKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTimKiemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tim_kiem.png"))); // NOI18N
        btnTimKiemKH.setText("Tìm");
        btnTimKiemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtTimKiemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblKhachHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHang);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Khách Hàng", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(352, 352, 352)
                .addComponent(lblTieuDe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTieuDe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboLoaiMatHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoaiMatHangItemStateChanged
        // TODO add your handling code here:
        fillTableMatHang();
    }//GEN-LAST:event_cboLoaiMatHangItemStateChanged

    private void cboGiamGiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboGiamGiaItemStateChanged
        String GiamGia = (String) cboGiamGia.getSelectedItem().toString();
        int tong = 0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            int tt = (int) tblGioHang.getValueAt(i, 6);
            tong += tt;
        }
        int tongt = tong - (tong * Integer.parseInt(GiamGia) / 100);
        lblTongTien.setText(String.valueOf(tongt));
    }//GEN-LAST:event_cboGiamGiaItemStateChanged


    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        index = tblKhachHang.getSelectedRow();
        if (index >= 0) {
            lblMaKH1.setText(tblKhachHang.getValueAt(index, 0).toString());
            txtTenKH1.setText(tblKhachHang.getValueAt(index, 1).toString());
            txtSDT1.setText(tblKhachHang.getValueAt(index, 2).toString());
            txtDiaChi1.setText(tblKhachHang.getValueAt(index, 3).toString());
            btnThemKH.setEnabled(false);
            btnSuaKH.setEnabled(true);
            btnXoaKH.setEnabled(true);
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnTimKiemMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemMHActionPerformed
        // TODO add your handling code here:
        fillTableMatHang();
    }//GEN-LAST:event_btnTimKiemMHActionPerformed

    void addMatHang() {
        try {
            CTHoaDon cthd = new CTHoaDon();
            String mahd = lblMaHD.getText();
            String mamh = txtMaMH.getText();
            String tenmh = txtTenMH.getText();
            String MAU = txtMau.getText();
            String size = txtSize.getText();
            int sLgThem = Integer.parseInt(txtSoLuongThem.getText());
            double giaTien = Double.parseDouble(txtGiaTien.getText());
            double thanhTien = sLgThem * giaTien;

            cthd.setMaHD(mahd);
            cthd.setMaMH(mamh);
            cthd.setTenMH(tenmh);
            cthd.setMAU(MAU);
            cthd.setSize(size);
            cthd.setSoLuong(sLgThem);
            cthd.setGiaTien(giaTien);
            cthd.setThanhTien(thanhTien);
            cthddao.insert(cthd);
            fillTableGioHang();
            MsgBox.alert(this, "Thêm sản phẩm thành công!");
            tabs.setSelectedIndex(1);
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm sản phẩm thất bại!");
            tabs.setSelectedIndex(1);
        }

    }

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        soLuong = Integer.parseInt(txtSoLuongThem.getText());
        si = Integer.parseInt(txtSLConLai.getText());
        if (txtMaMH.getText().length() == 0) {
            MsgBox.alert(this, "Không tìm thấy mặt hàng để thêm vào giỏ hàng!!!");
        } else if (txtSoLuongThem.getText().length() == 0) {
            MsgBox.alert(this, "Bạn chưa nhập số lượng!");
        } else if (soLuong <= 0) {
            MsgBox.alert(this, "Số lượng phải lớn hơn 0!");
        } else if (soLuong > si) {
            MsgBox.alert(this, "Số lượng mặt hàng trong kho không đủ để thêm vào giỏ hàng!");
        } else {
            addMatHang();
            btnThem.setEnabled(false);
            clearFromMH();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
        // TODO add your handling code here:
        this.fillComboboxSDT();
        String mahd = lblMaHD.getText().trim();
        cthddao.delete(mahd);
        hddao.delete(mahd);
        newForm();
    }//GEN-LAST:event_btnTaoMoiActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        index = -1;
        tabs.setSelectedIndex(0);
        btnThem.setEnabled(true);
        clearFromMH();
//        Status();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        try {
//            soLuong = 0;
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 4).toString());
                if (soLuong <= 0) {
                    MsgBox.alert(this, "Số lượng phải lớn hơn hoặc 0!");
                    return;
                }
            }
            if (soLuong > 0) {
                update();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Số lượng nhập sai định dạng!");
            return;
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
        fillTableGioHang();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblMatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMatHangMouseClicked
        // TODO add your handling code here:
        index = tblMatHang.getSelectedRow();
        if (index >= 0) {
            edit();
        }
    }//GEN-LAST:event_tblMatHangMouseClicked

    boolean CheckID(boolean chk) {
        List<HoaDon> list = hddao.selectAll();
        if (chk) {
            for (HoaDon hd : list) {
                if (lblMaHD.getText().trim().equalsIgnoreCase(hd.getMaHD().trim())) {
                    MsgBox.alert(this, "Mã hóa đơn đã tồn tại");
                    if (MsgBox.confirm(this, "Đã có mã mặt hàng này, bạn có muốn tạo một mã mới?")) {
                        // set mã hóa đơn
                        new Thread() {
                            @Override
                            public void run() {
                                for (int i = 0; i <= 9; i++) {
                                    try {
                                        int so = (int) Math.round(Math.random() * 10000000);
                                        lblMaHD.setText("HD0" + String.valueOf(so));
                                        Thread.sleep(0);
                                    } catch (Exception e) {
                                        break;
                                    }
                                }
                            }
                        }.start();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        try {
//            if (CheckID(true)) {
            if (tblGioHang.getRowCount() <= 0) {
                MsgBox.alert(this, "Trong giỏ hàng chưa có sản phẩm để thanh toán!!!");
            } else {
                updateHoaDon();

                for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                    String mamh = (String) tblGioHang.getValueAt(i, 0);

                    MatHang mh = mhdao.selectByID(mamh);
                    si = mh.getSoLuong();
                    soLuong = (int) tblGioHang.getValueAt(i, 4);
                    int s = si - soLuong;
                    mh.setSoLuong(s);
                    mhdao.update(mh);
                    HoaDonJframe.it.fillTableHoaDon();
                    MainFrame.ti.fillTable();
                }

                MsgBox.alert(this, "Thanh toán thành công!");
                this.thanhToanThanhCong();
                this.fillTableMatHang();
                MatHangJframe.it.fillTable();
                ThongKeJframe.tk.fillToTableBanChay();
                ThongKeJframe.tk.fillToTableDoanhThu();
                ThongKeJframe.tk.fillToTableKhachHang();
                MainFrame.ti.fillTable();
                MainFrame.ti.fillMatHang();
                MainFrame.ti.FillKhachHang();
            }
//            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi thanh toán");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void cboSDTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSDTItemStateChanged
        // TODO add your handling code here:
        String sdt = cboSDT.getSelectedItem().toString();
        KhachHang kh = khdao.selectSDTKH(sdt);
        lblMaKH.setText(Integer.toString(kh.getMaKH()));
        txtTenKH.setText(kh.getTenKH());
        txtDiaChi.setText(kh.getDiaChi());
    }//GEN-LAST:event_cboSDTItemStateChanged

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
        index = tblGioHang.getSelectedRow();
        if (index >= 0) {
            String mamh = (String) tblGioHang.getValueAt(index, 0);
            MatHang mh = mhdao.selectByID(mamh);

            txtMaMH.setText(mamh);
            txtTenMH.setText(mh.getTenMH());
            txtMau.setText(mh.getMAU());
            txtSize.setText(mh.getSize());
            txtSLConLai.setText(Integer.toString(mh.getSoLuong()));
            txtGiaTien.setText(Double.toString(mh.getGiaTien()));
            txtSoLuongThem.setText((String) tblGioHang.getValueAt(index, 4).toString());
            btnSua.setEnabled(true);
            btnXoa.setEnabled(true);
            btnThanhToan.setEnabled(false);
        } else {
            btnThanhToan.setEnabled(true);
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
        }

    }//GEN-LAST:event_tblGioHangMouseClicked

    void fillTableKhachHang() {
        model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
//        List<KhachHang> listKH = khdao.selectAll();
        List<KhachHang> listKH = khdao.selectByKeyword(txtTimKiemKH.getText());
        for (KhachHang kh : listKH) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getDienThoai(),
                kh.getDiaChi()
            });
        }
    }

    void deleteKH() {

        int makh = Integer.parseInt(lblMaKH1.getText());
        if (MsgBox.confirm(this, "Bạn có thực sự muốn khách hàng này?")) {
            try {
//                mhdao.deleteChiTiet(mamh);
                khdao.delete(makh);
                this.fillTableKhachHang();
                this.clearFromKH();
                MsgBox.alert(this, "Xóa thành công!");

            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thát bại!");
            }
        }
    }

    void setFormKH(KhachHang kh) {
        lblMaKH1.setText(Integer.toString(0));
        txtSDT1.setText(kh.getDienThoai());
        txtDiaChi1.setText(kh.getDiaChi());
        txtTenKH1.setText(kh.getTenKH());
    }

    void clearFromKH() {
        KhachHang kh = new KhachHang();
        setFormKH(kh);
        index = -1;
        btnThemKH.setEnabled(true);
        btnSuaKH.setEnabled(false);
        btnXoaKH.setEnabled(false);
//        Status();
    }

    KhachHang getFormKH() {
        KhachHang ncc = new KhachHang();
        ncc.setMaKH(Integer.parseInt(lblMaKH1.getText()));
        ncc.setTenKH(txtTenKH1.getText());
        ncc.setDienThoai(txtSDT1.getText());
        ncc.setDiaChi(txtDiaChi1.getText());
        return ncc;
    }

    void insertKH() {
        KhachHang kh = getFormKH();

        try {
            if (!Auth.isManager()) {
                MsgBox.alert(this, "Bạn không có quyền thực hiện chức năng này!");
            } else {
                khdao.insert(kh);
                this.fillTableKhachHang();
                this.clearFromKH();
                MsgBox.alert(this, "Thêm mới thành công!");
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
        }
    }

    void updateKH() {
        KhachHang ncc = getFormKH();
        try {
            if (!Auth.isManager()) {
                MsgBox.alert(this, "Bạn không có quyền thực hiện chức năng này!");
            } else {
                khdao.update(ncc);
                this.fillTableKhachHang();
                MsgBox.alert(this, "Cập nhật thành công!");
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }

    }


    private void btnTaoMoiKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiKHActionPerformed
        // TODO add your handling code here:
        clearFromKH();
    }//GEN-LAST:event_btnTaoMoiKHActionPerformed

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        // TODO add your handling code here:
        if (checkKH(true)) {
            insertKH();
            this.fillComboboxSDT();
        }
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
        // TODO add your handling code here:
        if (checkKH(true)) {
            updateKH();
            fillComboboxSDT();
        }
    }//GEN-LAST:event_btnSuaKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        deleteKH();
        fillComboboxSDT();
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnTimKiemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKHActionPerformed
        // TODO add your handling code here:
        fillTableKhachHang();
    }//GEN-LAST:event_btnTimKiemKHActionPerformed

    public void hieuUng() {
        //di chuyển tiêu đề
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        String td = lblTieuDe.getText();
                        int i = 0;
                        lblTieuDe.setText(td.split(" ")[i + 1] + " " + td.split(" ")[i + 2] + " " + td.split(" ")[i + 3] + " " + td.split(" ")[i]);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
        //set Ngày tạo hóa đơn dưới dang dd-MM-yyyy
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        SimpleDateFormat formater = new SimpleDateFormat();
                        formater.applyPattern("dd/MM/YYYY");
                        Date row = new Date();
                        String time = formater.format(row);
                        lblNgayTao.setText(time);

                        Thread.sleep(1000);

                    } catch (Exception e) {
                        break;
                    }

                }
            }
        }.start();
        // set mã hóa đơn
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 9; i++) {
                    try {
                        int so = (int) Math.round(Math.random() * 10000000);
                        lblMaHD.setText("HD0" + String.valueOf(so));
                        Thread.sleep(0);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }.start();
    }

    public String getMaHD() {
        return lblMaHD.getText();
    }

    public String getTongTien() {
//        double tongtien = Double.parseDouble(lblTongTien.getText());
        return lblTongTien.getText();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnTaoMoiKH;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimKiemKH;
    private javax.swing.JButton btnTimKiemMH;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JComboBox<String> cboGiamGia;
    private javax.swing.JComboBox<String> cboLoaiMatHang;
    public javax.swing.JComboBox<String> cboSDT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblMaHD;
    public javax.swing.JLabel lblMaKH;
    public javax.swing.JLabel lblMaKH1;
    private javax.swing.JLabel lblMaND;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblMatHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiaChi1;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaMH;
    private javax.swing.JTextField txtMau;
    private javax.swing.JTextField txtSDT1;
    private javax.swing.JTextField txtSLConLai;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtSoLuongThem;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenKH1;
    private javax.swing.JTextField txtTenMH;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemMH;
    // End of variables declaration//GEN-END:variables
}
