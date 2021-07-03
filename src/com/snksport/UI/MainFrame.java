/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snksport.UI;

import com.snksport.DAO.HoaDonChiTietDAO;
import com.snksport.DAO.HoaDonDAO;
import com.snksport.DAO.KhachHangDAO;
import com.snksport.DAO.MatHangDAO;
import com.snksport.Entity.CTHoaDon;
import com.snksport.Entity.MatHang;
import com.snksport.Utils.Auth;
import com.snksport.Utils.MsgBox;
import com.snksport.Utils.XImage;
//import com.sun.org.glassfish.external.statistics.annotations.Reset;
//import java.awt.Color;
//import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author ThanhNam
 */
public class MainFrame extends javax.swing.JFrame {

    public static MainFrame ti;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        ti = this;
        setLocationRelativeTo(this);
        setIconImage(XImage.getAppIcon());
        starDongHo();
        lblName.setText(Auth.user.getMaND().toUpperCase());
        pnlTrangChu.setOpaque(true);
        BackTrangChu();

        FillKhachHang();
//        fillMatHang();
        fillTable();
    }

    public void BackTrangChu() {
        if (Auth.isLogin()) {
            pnlTrangChu.setOpaque(true);

            //Đóng các cửa sổ khác
            frameNguoiDung.setVisible(false);
            frameNCCandLMH.setVisible(false);
            frameMatHang.setVisible(false);
            frameBanHang.setVisible(false);
            frameHoaDon.setVisible(false);
            frameThongKe.setVisible(false);
            frameNguoiDung.dispose();
            frameNCCandLMH.dispose();
            frameMatHang.dispose();
            frameBanHang.dispose();
            frameHoaDon.dispose();
            frameThongKe.dispose();
            DesktopPane.removeAll();
//            TrangChu frame = new TrangChu();
            DesktopPane.add(pnlTrangChu);
            pnlTrangChu.setVisible(true);
        }
    }

    public void starDongHo() {
        //Đồng hồ
        new Timer(10, new ActionListener() {
            SimpleDateFormat fomat = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblTime.setText(fomat.format(new Date()));
            }
        }).start();
    }

    public void dangXuat() {
        if (MsgBox.confirm(this, "Bạn có muốn đăng xuất không?")) {
            Auth.clear();
            this.dispose();
            new DangNhap().setVisible(true);
        }
    }

    public void openDoiMatKhau() {
        if (Auth.isLogin()) {
            DoiMatKhauJDialog frame = new DoiMatKhauJDialog(this, true);
            frame.setVisible(true);
        }
    }

    NguoiDungJframe frameNguoiDung = new NguoiDungJframe();

    public void openNguoiDung() {
        if (Auth.isLogin()) {
//            if (frameNguoiDung.isVisible() == true) {
//                MsgBox.alert(this, "Cửa sổ này hiện đang mở!");
//            } else {
            if (!Auth.isManager()) {
                MsgBox.alert(this, "Bạn không có quyền xem Người dùng!!!");
            } else {
                //Đóng các cửa sổ khác
                pnlTrangChu.setVisible(false);
                frameNguoiDung.setVisible(false);
                frameNCCandLMH.setVisible(false);
                frameMatHang.setVisible(false);
                frameBanHang.setVisible(false);
                frameHoaDon.setVisible(false);
                frameThongKe.setVisible(false);
                frameNguoiDung.dispose();
                frameNCCandLMH.dispose();
                frameMatHang.dispose();
                frameBanHang.dispose();
                frameHoaDon.dispose();
                frameThongKe.dispose();
                DesktopPane.removeAll();
                DesktopPane.add(frameNguoiDung);
                frameNguoiDung.setVisible(true);
            }
        }

//        }
    }

    MatHangJframe frameMatHang = new MatHangJframe();

    public void openMatHang() {
        if (Auth.isLogin()) {
//            if (frameMatHang.isVisible() == true) {
//                MsgBox.alert(this, "Cửa sổ này hiện đang mở!");
//            } else {
            //Đóng các cửa sổ khác
            pnlTrangChu.setVisible(false);
            frameNguoiDung.setVisible(false);
            frameNCCandLMH.setVisible(false);
            frameMatHang.setVisible(false);
            frameBanHang.setVisible(false);
            frameHoaDon.setVisible(false);
            frameThongKe.setVisible(false);
            frameNguoiDung.dispose();
            frameNCCandLMH.dispose();
            frameMatHang.dispose();
            frameBanHang.dispose();
            frameHoaDon.dispose();
            frameThongKe.dispose();
            DesktopPane.removeAll();
            DesktopPane.add(frameMatHang);
            frameMatHang.setVisible(true);
//            }
        }
    }

    BanHangJframe frameBanHang = new BanHangJframe();

    public void openBanHang() {
        if (Auth.isLogin()) {
//            if (frameBanHang.isVisible() == true) {
//                MsgBox.alert(this, "Cửa sổ này hiện đang mở!");
//            } else {
            //Đóng các cửa sổ khác
            pnlTrangChu.setVisible(false);
            frameNguoiDung.setVisible(false);
            frameNCCandLMH.setVisible(false);
            frameMatHang.setVisible(false);
            frameBanHang.setVisible(false);
            frameHoaDon.setVisible(false);
            frameThongKe.setVisible(false);
            frameNguoiDung.dispose();
            frameNCCandLMH.dispose();
            frameMatHang.dispose();
            frameBanHang.dispose();
            frameHoaDon.dispose();
            frameThongKe.dispose();
            DesktopPane.removeAll();
            DesktopPane.add(frameBanHang);
            frameBanHang.setVisible(true);
//            }
        }
    }

    HoaDonJframe frameHoaDon = new HoaDonJframe();

    public void openHoaDon() {
        if (Auth.isLogin()) {
//            if (frameHoaDon.isVisible() == true) {
//                MsgBox.alert(this, "Cửa sổ này hiện đang mở!");
//            } else {
            //Đóng các cửa sổ khác
            pnlTrangChu.setVisible(false);
            frameNguoiDung.setVisible(false);
            frameNCCandLMH.setVisible(false);
            frameMatHang.setVisible(false);
            frameBanHang.setVisible(false);
            frameHoaDon.setVisible(false);
            frameThongKe.setVisible(false);
            frameNguoiDung.dispose();
            frameNCCandLMH.dispose();
            frameMatHang.dispose();
            frameBanHang.dispose();
            frameHoaDon.dispose();
            frameThongKe.dispose();
            DesktopPane.removeAll();
            HoaDonDAO dao = new HoaDonDAO();
            dao.deleteHDnull();
            DesktopPane.add(frameHoaDon);
            frameHoaDon.setVisible(true);
//            }
        }
    }

    NhaCCvsLoaiMHJframe frameNCCandLMH = new NhaCCvsLoaiMHJframe();

    public void openNCCandLMH() {
        if (Auth.isLogin()) {
//            if (frameNCCandLMH.isVisible() == true) {
//                MsgBox.alert(this, "Cửa sổ này hiện đang mở!");
//            } else {
            if (!Auth.isManager()) {
                MsgBox.alert(this, "Bạn không có quyền truy cập vào đây!!!");
            } else {
                //Đóng các cửa sổ khác
                pnlTrangChu.setVisible(false);
                frameNguoiDung.setVisible(false);
                frameNCCandLMH.setVisible(false);
                frameMatHang.setVisible(false);
                frameBanHang.setVisible(false);
                frameHoaDon.setVisible(false);
                frameThongKe.setVisible(false);
                frameNguoiDung.dispose();
                frameNCCandLMH.dispose();
                frameMatHang.dispose();
                frameBanHang.dispose();
                frameHoaDon.dispose();
                frameThongKe.dispose();
                DesktopPane.removeAll();
                DesktopPane.add(frameNCCandLMH);
                frameNCCandLMH.setVisible(true);
//                }
            }
        }
    }

    ThongKeJframe frameThongKe = new ThongKeJframe();

    public void openThongKe(int index) {
        HoaDonDAO dao = new HoaDonDAO();
        dao.deleteHDnull();
        if (Auth.isLogin()) {
            if (index == 2 && !Auth.isManager()) {
                MsgBox.alert(this, "Bạn không có quyền xem doanh thu!!!");
            } else {
                //Đóng các cửa sổ khác
                pnlTrangChu.setVisible(false);
                frameNguoiDung.setVisible(false);
                frameNCCandLMH.setVisible(false);
                frameMatHang.setVisible(false);
                frameBanHang.setVisible(false);
                frameHoaDon.setVisible(false);
                frameThongKe.setVisible(false);
                frameNguoiDung.dispose();
                frameNCCandLMH.dispose();
                frameMatHang.dispose();
                frameBanHang.dispose();
                frameHoaDon.dispose();
                frameThongKe.dispose();
                DesktopPane.removeAll();
                DesktopPane.add(frameThongKe);
                frameThongKe.setVisible(true);
                frameThongKe.selectTab(index);
            }
        }
    }

    public void openGioiThieu() {
        if (Auth.isLogin()) {
            new GioiThieuJDialog1(this, true).setVisible(true);
        }
    }

    public void openHuongDan() {
//        try {
//            Desktop.getDesktop().browse(new File("help/index.html").toURI());
//        } catch (IOException ex) {
//            MsgBox.alert(this, "Không tìm thấy file hướng dẫn!");
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnDangXuat = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        btnTrangChu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnMatHang = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        btnGioHang = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnHoaDon = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        btnGioiThieu = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        jToolBar2 = new javax.swing.JToolBar();
        lblName = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        lblTime = new javax.swing.JLabel();
        DesktopPane = new javax.swing.JDesktopPane();
        pnlTrangChu = new javax.swing.JPanel();
        txthoTen = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnXemKH = new javax.swing.JButton();
        lblTenKH = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMatHang = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        btnXemMH = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnXemSP = new javax.swing.JButton();
        lblTenSP = new javax.swing.JLabel();
        lblGiaTien = new javax.swing.JLabel();
        lblLoaiMatHang = new javax.swing.JLabel();
        btnBD = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuHeThong = new javax.swing.JMenu();
        mniTrangChu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniDMK = new javax.swing.JMenuItem();
        mniExit = new javax.swing.JMenuItem();
        mnuQuanLy = new javax.swing.JMenu();
        mniMatHang = new javax.swing.JMenuItem();
        mniBanHang = new javax.swing.JMenuItem();
        mniHoaDon = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mniNCCandLMH = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mniNguoiDung = new javax.swing.JMenuItem();
        mnuThongKe = new javax.swing.JMenu();
        mniKhachHang = new javax.swing.JMenuItem();
        mniTKmatHang = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mniDoanhThu = new javax.swing.JMenuItem();
        mnuTroGiup = new javax.swing.JMenu();
        mniGioiThieu = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mniHuongDan = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SNKSport");
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1080, 876));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setForeground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);

        btnDangXuat.setBackground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dang_xuat.png"))); // NOI18N
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.setFocusable(false);
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDangXuat);
        jToolBar1.add(jSeparator9);

        btnTrangChu.setBackground(new java.awt.Color(255, 255, 255));
        btnTrangChu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Home.png"))); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setFocusable(false);
        btnTrangChu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTrangChu);
        jToolBar1.add(jSeparator1);

        btnMatHang.setBackground(new java.awt.Color(255, 255, 255));
        btnMatHang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Full basket.png"))); // NOI18N
        btnMatHang.setText("Mặt Hàng");
        btnMatHang.setFocusable(false);
        btnMatHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMatHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatHangActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMatHang);
        jToolBar1.add(jSeparator10);

        btnGioHang.setBackground(new java.awt.Color(255, 255, 255));
        btnGioHang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGioHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/gio_hang.png"))); // NOI18N
        btnGioHang.setText("Bán Hàng");
        btnGioHang.setFocusable(false);
        btnGioHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGioHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioHangActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGioHang);
        jToolBar1.add(jSeparator4);

        btnHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        btnHoaDon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hoa_don.png"))); // NOI18N
        btnHoaDon.setText("Hóa Đơn");
        btnHoaDon.setFocusable(false);
        btnHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHoaDon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHoaDon);
        jToolBar1.add(jSeparator11);

        btnGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        btnGioiThieu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Info.png"))); // NOI18N
        btnGioiThieu.setText("Giới thiệu");
        btnGioiThieu.setFocusable(false);
        btnGioiThieu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGioiThieu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioiThieuActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGioiThieu);
        jToolBar1.add(jSeparator12);

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setFloatable(false);
        jToolBar2.setForeground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);

        lblName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        lblName.setText("Name");
        jToolBar2.add(lblName);
        jToolBar2.add(jSeparator3);

        lblTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Alarm.png"))); // NOI18N
        lblTime.setText("Time");
        jToolBar2.add(lblTime);

        DesktopPane.setBackground(new java.awt.Color(204, 204, 204));
        DesktopPane.setLayout(new java.awt.BorderLayout());

        pnlTrangChu.setBackground(new java.awt.Color(0, 176, 170));

        txthoTen.setBackground(new java.awt.Color(102, 255, 255));
        txthoTen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txthoTen.setForeground(new java.awt.Color(240, 240, 240));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Khách Hàng Mua Nhiều Nhất");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Họ Tên: ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Số Điện Thoại:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Địa Chỉ:");

        jLabel6.setText("_____________________________________________________________________");

        jLabel8.setText("_____________________________________________________________________");

        btnXemKH.setBackground(new java.awt.Color(51, 102, 255));
        btnXemKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXemKH.setText("Xem Thêm");
        btnXemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemKHActionPerformed(evt);
            }
        });

        lblTenKH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTenKH.setText("...");

        lblSDT.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSDT.setText("...");

        lblDiaChi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDiaChi.setText("...");

        javax.swing.GroupLayout txthoTenLayout = new javax.swing.GroupLayout(txthoTen);
        txthoTen.setLayout(txthoTenLayout);
        txthoTenLayout.setHorizontalGroup(
            txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txthoTenLayout.createSequentialGroup()
                .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txthoTenLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txthoTenLayout.createSequentialGroup()
                                .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenKH)
                                    .addComponent(lblSDT)
                                    .addComponent(lblDiaChi))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(txthoTenLayout.createSequentialGroup()
                        .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txthoTenLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnXemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(txthoTenLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        txthoTenLayout.setVerticalGroup(
            txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txthoTenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTenKH))
                .addGap(18, 18, 18)
                .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblSDT))
                .addGap(18, 18, 18)
                .addGroup(txthoTenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblDiaChi))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(btnXemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 87, 97));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setForeground(new java.awt.Color(240, 240, 240));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Những Sản Phẩm Sắp Hết Hàng");

        tblMatHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblMatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "Màu", "Size", "SL Còn Lại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMatHang);

        jLabel39.setText("_______________________________________________________________________________________________________________________________________________");

        btnXemMH.setBackground(new java.awt.Color(51, 102, 255));
        btnXemMH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXemMH.setText("Xem Thêm");
        btnXemMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemMHActionPerformed(evt);
            }
        });

        jLabel40.setText("_______________________________________________________________________________________________________________________________________________");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(jLabel21))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(btnXemMH, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXemMH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel4.setBackground(new java.awt.Color(239, 254, 131));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setForeground(new java.awt.Color(240, 240, 240));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Sản Phẩm Bán Chạy Nhất");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Tên Sản Phẩm: ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Giá Tiền:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Loại Mặt Hàng:");

        jLabel14.setText("____________________________________________________________________");

        btnXemSP.setBackground(new java.awt.Color(153, 255, 204));
        btnXemSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXemSP.setText("Xem Thêm");
        btnXemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemSPActionPerformed(evt);
            }
        });

        lblTenSP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTenSP.setText("...");

        lblGiaTien.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGiaTien.setText("...");

        lblLoaiMatHang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLoaiMatHang.setText("...");

        btnBD.setBackground(new java.awt.Color(153, 255, 204));
        btnBD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBD.setText("Biểu Đồ Thống Kê");
        btnBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBDActionPerformed(evt);
            }
        });

        jLabel15.setText("____________________________________________________________________");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnXemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenSP)
                                    .addComponent(lblGiaTien)
                                    .addComponent(lblLoaiMatHang)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jLabel9)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblTenSP))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblGiaTien))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblLoaiMatHang))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemSP, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlTrangChuLayout = new javax.swing.GroupLayout(pnlTrangChu);
        pnlTrangChu.setLayout(pnlTrangChuLayout);
        pnlTrangChuLayout.setHorizontalGroup(
            pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTrangChuLayout.createSequentialGroup()
                        .addComponent(txthoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTrangChuLayout.setVerticalGroup(
            pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txthoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        DesktopPane.add(pnlTrangChu, java.awt.BorderLayout.CENTER);

        mnuHeThong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Desktop.png"))); // NOI18N
        mnuHeThong.setText("Hệ Thống");
        mnuHeThong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        mniTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Home.png"))); // NOI18N
        mniTrangChu.setText("Trang Chủ");
        mniTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTrangChuActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniTrangChu);
        mnuHeThong.add(jSeparator2);

        mniDMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Refresh.png"))); // NOI18N
        mniDMK.setText("Đổi Mật Khẩu");
        mniDMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDMKActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniDMK);

        mniExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mniExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dang_xuat.png"))); // NOI18N
        mniExit.setText("Đăng Xuất");
        mniExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniExitActionPerformed(evt);
            }
        });
        mnuHeThong.add(mniExit);

        jMenuBar1.add(mnuHeThong);

        mnuQuanLy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Application form.png"))); // NOI18N
        mnuQuanLy.setText("Quản Lý");
        mnuQuanLy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        mniMatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Full basket.png"))); // NOI18N
        mniMatHang.setText("Quản lý mặt hàng");
        mniMatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniMatHangActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniMatHang);

        mniBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/gio_hang.png"))); // NOI18N
        mniBanHang.setText("Quản lý bán hàng");
        mniBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniBanHangActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniBanHang);

        mniHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hoa_don.png"))); // NOI18N
        mniHoaDon.setText("Quản lý hóa đơn");
        mniHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHoaDonActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniHoaDon);
        mnuQuanLy.add(jSeparator6);

        mniNCCandLMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Home.png"))); // NOI18N
        mniNCCandLMH.setText("Quản lý nhà cung cấp & Loại mặt hàng");
        mniNCCandLMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNCCandLMHActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniNCCandLMH);
        mnuQuanLy.add(jSeparator7);

        mniNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        mniNguoiDung.setText("Quản lý người dùng");
        mniNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNguoiDungActionPerformed(evt);
            }
        });
        mnuQuanLy.add(mniNguoiDung);

        jMenuBar1.add(mnuQuanLy);

        mnuThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thong_ke.png"))); // NOI18N
        mnuThongKe.setText("Thống kê");
        mnuThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        mniKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/User group.png"))); // NOI18N
        mniKhachHang.setText("Khách hàng thân thiết");
        mniKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKhachHangActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniKhachHang);

        mniTKmatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Numbered list.png"))); // NOI18N
        mniTKmatHang.setText("Mặt hàng bán chạy");
        mniTKmatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTKmatHangActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniTKmatHang);
        mnuThongKe.add(jSeparator8);

        mniDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Diagram.png"))); // NOI18N
        mniDoanhThu.setText("Doanh thu");
        mniDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoanhThuActionPerformed(evt);
            }
        });
        mnuThongKe.add(mniDoanhThu);

        jMenuBar1.add(mnuThongKe);

        mnuTroGiup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/help.png"))); // NOI18N
        mnuTroGiup.setText("Trợ giúp");
        mnuTroGiup.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        mniGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Info.png"))); // NOI18N
        mniGioiThieu.setText("Giới thiệu hệ thống");
        mniGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniGioiThieuActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniGioiThieu);
        mnuTroGiup.add(jSeparator5);

        mniHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Online.png"))); // NOI18N
        mniHuongDan.setText("Hướng dẫn sử dụng");
        mniHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHuongDanActionPerformed(evt);
            }
        });
        mnuTroGiup.add(mniHuongDan);

        jMenuBar1.add(mnuTroGiup);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
            .addComponent(DesktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        openHoaDon();
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioHangActionPerformed
        // TODO add your handling code here:
        openBanHang();
    }//GEN-LAST:event_btnGioHangActionPerformed

    private void btnMatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatHangActionPerformed
        // TODO add your handling code here:
        openMatHang();
    }//GEN-LAST:event_btnMatHangActionPerformed

    private void mniMatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniMatHangActionPerformed
        // TODO add your handling code here:
        openMatHang();
    }//GEN-LAST:event_mniMatHangActionPerformed

    private void mniBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniBanHangActionPerformed
        // TODO add your handling code here:
        openBanHang();
    }//GEN-LAST:event_mniBanHangActionPerformed

    private void mniHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHoaDonActionPerformed
        // TODO add your handling code here:
        openHoaDon();
    }//GEN-LAST:event_mniHoaDonActionPerformed

    private void mniNCCandLMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNCCandLMHActionPerformed
        // TODO add your handling code here:
        openNCCandLMH();
    }//GEN-LAST:event_mniNCCandLMHActionPerformed

    private void mniNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNguoiDungActionPerformed
        // TODO add your handling code here:
        openNguoiDung();
    }//GEN-LAST:event_mniNguoiDungActionPerformed

    private void mniTKmatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTKmatHangActionPerformed
        // TODO add your handling code here:
        openThongKe(1);
    }//GEN-LAST:event_mniTKmatHangActionPerformed

    private void mniDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoanhThuActionPerformed
        // TODO add your handling code here:
        openThongKe(2);
    }//GEN-LAST:event_mniDoanhThuActionPerformed

    private void mniGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniGioiThieuActionPerformed
        // TODO add your handling code here:
        openGioiThieu();
    }//GEN-LAST:event_mniGioiThieuActionPerformed

    private void mniHuongDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHuongDanActionPerformed
        // TODO add your handling code here:
        openHuongDan();
    }//GEN-LAST:event_mniHuongDanActionPerformed

    private void btnGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioiThieuActionPerformed
        // TODO add your handling code here:
        openGioiThieu();
    }//GEN-LAST:event_btnGioiThieuActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void mniExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExitActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_mniExitActionPerformed

    private void mniDMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDMKActionPerformed
        // TODO add your handling code here:
        openDoiMatKhau();
    }//GEN-LAST:event_mniDMKActionPerformed

    private void mniKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKhachHangActionPerformed
        // TODO add your handling code here:
        openThongKe(0);
    }//GEN-LAST:event_mniKhachHangActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        // TODO add your handling code here:
        BackTrangChu();
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void mniTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTrangChuActionPerformed
        // TODO add your handling code here:
        BackTrangChu();
    }//GEN-LAST:event_mniTrangChuActionPerformed

    private void btnBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBDActionPerformed
        JFreeChart pieChart = createChart(createDataset());
        //           lblBieudo.setToolTipText(String.valueOf(pieChart));
        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Biểu đồ ");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(XImage.getAppIcon());
    }//GEN-LAST:event_btnBDActionPerformed

    private void btnXemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemSPActionPerformed
        MainFrame.ti.openThongKe(1);
    }//GEN-LAST:event_btnXemSPActionPerformed

    private void btnXemMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemMHActionPerformed
        MainFrame.ti.openMatHang();
    }//GEN-LAST:event_btnXemMHActionPerformed

    private void btnXemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemKHActionPerformed
        MainFrame.ti.openThongKe(0);
    }//GEN-LAST:event_btnXemKHActionPerformed

    HoaDonChiTietDAO cthd = new HoaDonChiTietDAO();
    KhachHangDAO khDao = new KhachHangDAO();
    MatHangDAO mhdao = new MatHangDAO();

    void FillKhachHang() {
//       KhachHang hd = khDao.Top1();
        lblTenKH.setText(ThongKeJframe.tk.getTenKH());
        lblSDT.setText(ThongKeJframe.tk.getSDT());
        lblDiaChi.setText(ThongKeJframe.tk.getDiaChi());
    }

    void fillMatHang() {
        lblTenSP.setText(ThongKeJframe.tk.getTenSP());
        lblGiaTien.setText(Double.toString(ThongKeJframe.tk.GetGiaSP()) + " VNĐ");
        lblLoaiMatHang.setText(ThongKeJframe.tk.getLMH());
    }

    void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblMatHang.getModel();
            model.setRowCount(0);
            List<MatHang> list = mhdao.selectAll();//doc du lieu tu CSDL
            for (int i = 0; i < list.size(); i++) {
                MatHang mh = list.get(i);
                if (mh.getSoLuong() <= 5) {
                    model.addRow(new Object[]{
                        mh.getTenMH(),
                        mh.getMAU(),
                        mh.getSize(),
                        mh.getSoLuong(),}); //them 1 hang vao table
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Biểu Đồ Thống Kê Loại Mặt Hàng Bán Chạy", dataset, true, true, true);
        return chart;
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        List<CTHoaDon> listCT = cthd.selectTongSL();
        for (CTHoaDon hd : listCT) {
            dataset.setValue(hd.getTenLMH(), new Integer(hd.getTong()));
        }
//        dataset.setValue("Nhóm 15 - 59", new Double(66.0));
//        dataset.setValue("Nhóm trên 60", new Double(9.0));
        return dataset;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane DesktopPane;
    private javax.swing.JButton btnBD;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnGioHang;
    private javax.swing.JButton btnGioiThieu;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnMatHang;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnXemKH;
    private javax.swing.JButton btnXemMH;
    private javax.swing.JButton btnXemSP;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblLoaiMatHang;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTenSP;
    public javax.swing.JLabel lblTime;
    private javax.swing.JMenuItem mniBanHang;
    private javax.swing.JMenuItem mniDMK;
    private javax.swing.JMenuItem mniDoanhThu;
    private javax.swing.JMenuItem mniExit;
    private javax.swing.JMenuItem mniGioiThieu;
    private javax.swing.JMenuItem mniHoaDon;
    private javax.swing.JMenuItem mniHuongDan;
    private javax.swing.JMenuItem mniKhachHang;
    private javax.swing.JMenuItem mniMatHang;
    private javax.swing.JMenuItem mniNCCandLMH;
    private javax.swing.JMenuItem mniNguoiDung;
    private javax.swing.JMenuItem mniTKmatHang;
    private javax.swing.JMenuItem mniTrangChu;
    private javax.swing.JMenu mnuHeThong;
    private javax.swing.JMenu mnuQuanLy;
    private javax.swing.JMenu mnuThongKe;
    private javax.swing.JMenu mnuTroGiup;
    private javax.swing.JPanel pnlTrangChu;
    private javax.swing.JTable tblMatHang;
    private javax.swing.JPanel txthoTen;
    // End of variables declaration//GEN-END:variables
}
