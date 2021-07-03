/*USE master
DROP DATABASE SNKSport_DA1_PRO1041
GO*/

CREATE DATABASE SNKSport_DA1_PRO1041
GO

USE SNKSport_DA1_PRO1041
GO

IF OBJECT_ID('CTHoaDon') IS NOT NULL
DROP TABLE CTHoaDon
GO

IF OBJECT_ID('HoaDon') IS NOT NULL
DROP TABLE HoaDon
GO

IF OBJECT_ID('KhachHang') IS NOT NULL
DROP TABLE KhachHang
GO

IF OBJECT_ID('MatHang') IS NOT NULL
DROP TABLE MatHang
GO

IF OBJECT_ID('LoaiMH') IS NOT NULL
DROP TABLE LoaiMH
GO

IF OBJECT_ID('NhaCC') IS NOT NULL
DROP TABLE NhaCC
GO

IF OBJECT_ID('NguoiDung') IS NOT NULL
DROP TABLE NguoiDung
GO

CREATE TABLE NguoiDung(
	MaND NVARCHAR(10) NOT NULL,
	MatKhau NVARCHAR(50) NOT NULL,
	HoTen NVARCHAR(50) NOT NULL,
	GioiTinh BIT DEFAULT 1, -- 1 là nam, 0 là nữ
	NgaySinh DATE NOT NULL,
	DienThoai NVARCHAR(10) NOT NULL,
	QueQuan NVARCHAR(50) NOT NULL,
	Luong MONEY DEFAULT -1,
	VaiTro BIT DEFAULT 0, --0 là nhân viên, 1 là trưởng phòng

	PRIMARY KEY (MaND)
)

INSERT INTO NguoiDung(MaND, MatKhau, HoTen, NgaySinh, DienThoai, QueQuan, VaiTro)
			VALUES (N'ADMIN', N'admin', N'ADMIN', '11/11/1111', N'', N'', 1)
INSERT INTO NguoiDung(MaND, MatKhau, HoTen, NgaySinh, DienThoai, QueQuan,Luong, VaiTro)
			VALUES (N'hainv', N'123', N'Hai', '11/13/2011', N'0987654321', N'Ha Noi',9999999 ,0)
GO

CREATE TABLE NhaCC(
	MaNCC INT IDENTITY,
	TenNCC NVARCHAR(50) NOT NULL,
	DienThoai NVARCHAR(10) NOT NULL,
	DiaChi NVARCHAR(50) NOT NULL,

	PRIMARY KEY (MaNCC)
)
INSERT INTO NhaCC(TenNCC, DienThoai, DiaChi) VALUES (N'Công ty TNHH Tiến Đạt', N'0987678654', N'Quan Hoa, Cầu Giấy, Hà Nội')
INSERT INTO NhaCC(TenNCC, DienThoai, DiaChi) VALUES (N'Công ty TNHH thương mại Quý Nam', N'0986868688', N'Bắc Từ Liêm, Hà Nội')
INSERT INTO NhaCC(TenNCC, DienThoai, DiaChi) VALUES (N'Xưởng giày Trọng Dương', N'0989779992', N'Hà Đông, Hà Nội')
GO


CREATE TABLE LoaiMH(
	MaLMH INT IDENTITY(1,1),
	TenLMH NVARCHAR(50) NOT NULL,
	MoTa NVARCHAR(50) NULL,

	PRIMARY KEY (MaLMH)
)
INSERT INTO LoaiMH(TenLMH, MoTa) VALUES (N'Nike',N'Giày chính hãng Nike!')
INSERT INTO LoaiMH(TenLMH, MoTa) VALUES (N'Adidas',N'Giày chính hãng Adidas!')
INSERT INTO LoaiMH(TenLMH, MoTa) VALUES (N'Vans',N'Giày chính hãng Vans!')
INSERT INTO LoaiMH(TenLMH, MoTa) VALUES (N'Convert',N'Giày chính hãng Convert!')
GO

CREATE TABLE MatHang(
	MaMH NCHAR(10) NOT NULL,
	TenMH NVARCHAR(50) NOT NULL,
	MAU NVARCHAR(20) NOT NULL,
	SoLuong INT NOT NULL,
	Size NCHAR(2) NOT NULL,
	HinhAnh NVARCHAR(50) NOT NULL,
	GiaTien MONEY NOT NULL,
	MaNCC INT NOT NULL,
	MaLMH INT NOT NULL,
	MaND NVARCHAR(10) NOT NULL,
	NgayNhap DATE DEFAULT getdate(),
	
	PRIMARY KEY (MaMH),
	FOREIGN KEY (MaNCC) REFERENCES NhaCC(MaNCC) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY (MaLMH) REFERENCES LoaiMH(MaLMH) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY (MaND) REFERENCES NguoiDung(MaND) ON DELETE NO ACTION ON UPDATE CASCADE
)
INSERT INTO MatHang(MaMH,TenMH, MAU, SoLuong, Size, HinhAnh, GiaTien, MaNCC, MaLMH, MaND) 
		VALUES (N'MH01', N'Vans Old Skool', N'Đen', 10, N'40', N'VansOldSkool.jpg', N'200000', 1, 3, N'ADMIN')
insert into MatHang(MaMH,TenMH, MAU, SoLuong, Size,HinhAnh, GiaTien, MaNCC, MaLMH, MaND) 
		values (N'MH02', N'Converse 1987', N'Trắng', 30, N'39', N'Converse1987.jpg', N'150000', 3, 4, N'ADMIN')
insert into MatHang(MaMH,TenMH, MAU, SoLuong, Size,HinhAnh, GiaTien, MaNCC, MaLMH, MaND) 
		values (N'MH03', N'Nike Off White', N'Trắng - Xanh Dương', 20, N'42', N'NikeOffWhite.jpg', N'550000', 2, 1, N'ADMIN')
INSERT INTO MatHang(MaMH,TenMH, MAU, SoLuong, Size,HinhAnh, GiaTien, MaNCC, MaLMH, MaND) 
		VALUES (N'MH04', N'Vans Old Skool', N'Nâu', 50, N'41', N'VansOldSkool.jpg', N'190000', 1, 3, N'ADMIN')
GO

CREATE TABLE KhachHang(
	MaKH INT IDENTITY(1,1),
	TenKH NVARCHAR(50) NOT NULL,
	DienThoai NVARCHAR(10) NOT NULL,
	DiaChi NVARCHAR(50) NOT NULL,

	PRIMARY KEY (MaKH)
)
Insert into KhachHang(TenKH, DienThoai, DiaChi) Values (N'Ngô Văn Anh Hải', N'0988886666', N'Dubai')
Insert into KhachHang(TenKH, DienThoai, DiaChi) Values (N'Ngô Văn Hải', N'0988844666', N'Dui')
Insert into KhachHang(TenKH, DienThoai, DiaChi) Values (N'Ngô Văn A', N'0988556666', N'Dai')
GO
CREATE TABLE HoaDon(
	MaHD NCHAR(10) NOT NULL,
	MaKH INT,
	TongTien MONEY,
	MaND NVARCHAR(10) NOT NULL,
	NgayTao DATE DEFAULT getdate(),

	PRIMARY KEY (MaHD),
	FOREIGN KEY (MaND) REFERENCES NguoiDung(MaND) ON DELETE NO ACTION,
	FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH) ON DELETE NO ACTION ON UPDATE CASCADE,
)
Insert into HoaDon(MaHD, MaKH, TongTien, MaND) Values (N'HD00001', 1, N'200000', N'ADMIN')
Insert into HoaDon(MaHD, MaKH, TongTien, MaND) Values (N'HD00003', 1, N'400000', N'ADMIN')
Insert into HoaDon(MaHD, MaKH, TongTien, MaND) Values (N'HD00004', 1, N'500000', N'ADMIN')
Insert into HoaDon(MaHD, MaKH, TongTien, MaND) Values (N'HD00002', 2, N'400000', N'ADMIN')
Insert into HoaDon(MaHD, MaKH, TongTien, MaND) Values (N'HD00005', 3, N'9900000', N'ADMIN')
GO

CREATE TABLE CTHoaDon(
	MaHD NCHAR(10) NOT NULL,
	MaMH NCHAR(10) NOT NULL,
	TenMH NVARCHAR(50) NOT NULL,
	SoLuong INT NOT NULL,
	Size NCHAR(2) NOT NULL,
	MAU NVARCHAR(20) NOT NULL,
	GiaTien MONEY NOT NULL,
	ThanhTien MONEY NOT NULL,
	NgayTao DATE DEFAULT getdate(),


	PRIMARY KEY (MaHD, MaMH),
	FOREIGN KEY (MaMH) REFERENCES MatHang(MaMH) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD) ON DELETE NO ACTION ON UPDATE CASCADE
)
Insert Into CTHoaDon(MaHD, TenMH, SoLuong, Size, MAU,GiaTien, ThanhTien, MaMH)
		Values (N'HD00001', N'Vans Old Skool', '3', N'40', N'Đen',N'200000', N'600000', N'MH02')
Insert Into CTHoaDon(MaHD, TenMH, SoLuong, Size, MAU, GiaTien, ThanhTien, MaMH)
		Values (N'HD00002', N'Nike Off White', '1', N'40', N'Trắng - Xanh Dương',N'550000', N'550000', N'MH03')
		Insert Into CTHoaDon(MaHD, TenMH, SoLuong, Size, MAU, GiaTien, ThanhTien, MaMH)
		Values (N'HD00003',N'Converse 1987', '2', N'42', N'Trắng ',N'150000', N'300000', N'MH02')
GO


--DROP PROC [dbo].[sp_KhachHangThanThiet]
CREATE PROC [dbo].sp_KhachHangThanThiet
AS BEGIN
	
	SELECT TOP 10
		TenKH,
		DienThoai,
		DiaChi,
		COUNT(HoaDon.MaKH) AS soLuongMua
	FROM HoaDon join KhachHang 
	ON HoaDon.MaKH = KhachHang.MaKH
	GROUP BY TenKH, DiaChi, DienThoai
	ORDER BY soLuongMua DESC

END

--DROP PROC [dbo].[sp_ThongKeBanChay]
CREATE PROC [dbo].[sp_ThongKeBanChay]
@MONTH int,
@year int
AS BEGIN
	SELECT
		mh.MaMH,
		mh.TenMH,
		lmh.TenLMH,
		mh.GiaTien,
		SUM(ct.SoLuong) as SoLuongDaBan				
	FROM dbo.MatHang mh
		JOIN dbo.CTHoaDon ct ON ct.MaMH = mh.MaMH
		JOIN LoaiMH lmh ON mh.MaLMH = lmh.MaLMH
		WHERE month(ct.NgayTao) = @MONTH and YEAR(ct.NgayTao) = @year
	GROUP BY mh.MaMH, mh.TenMH, lmh.TenLMH, mh.GiaTien
	ORDER BY SoLuongDaBan DESC
END

EXEC sp_ThongKeBanChay 12,2020

--DROP PROC sp_ThongKeDoanhThu
CREATE PROC [dbo].[sp_ThongKeDoanhThu](@Year INT)
AS BEGIN
	SELECT
		MONTH(hd.NgayTao) as Thang,
		COUNT(hd.MaHD) TongSoHD,
		STR(MIN(hd.TongTien), 10) ThapNhat,
		STR(MAX(hd.TongTien), 10) CaoNhat,
		STR(AVG(hd.TongTien), 10) TrungBinh,
		STR(SUM(hd.TongTien), 12) DoanhThu
	FROM dbo.HoaDon hd
	WHERE YEAR(hd.NgayTao) = 2020
	GROUP BY MONTH(hd.NgayTao)
	order by DoanhThu DESC
END

EXEC sp_ThongKeDoanhThu 2020


select * from NguoiDung
select * from NhaCC
select * from LoaiMH
select * from MatHang

select * from KhachHang
select * from HoaDon 
select * from CTHoaDon



