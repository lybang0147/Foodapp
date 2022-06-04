package hcmute.huynhlybang19110330.nhom10foody.model;

import java.util.Arrays;

public class Food {
private int Id;
private int IdNhaHang;
private String tenMon;
private int gia;
private byte[] image;
private String chiTiet;
private int soLuongDaBan;
private boolean trangThai;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdNhaHang() {
        return IdNhaHang;
    }

    public void setIdNhaHang(int idNhaHang) {
        IdNhaHang = idNhaHang;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public Food(int id, int idNhaHang, String tenMon, int gia, byte[] image, String chiTiet, int soLuongDaBan, boolean trangThai) {
        Id = id;
        IdNhaHang = idNhaHang;
        this.tenMon = tenMon;
        this.gia = gia;
        this.image = image;
        this.chiTiet = chiTiet;
        this.soLuongDaBan = soLuongDaBan;
        this.trangThai = trangThai;
    }

    public Food(int id,String tenMon, int gia, byte[] image, int soLuongDaBan, boolean trangThai) {
        Id=id;
        this.tenMon = tenMon;
        this.gia = gia;
        this.image = image;
        this.soLuongDaBan = soLuongDaBan;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Food{" +
                "Id=" + Id +
                ", IdNhaHang=" + IdNhaHang +
                ", tenMon='" + tenMon + '\'' +
                ", gia=" + gia +
                ", image=" + Arrays.toString(image) +
                ", chiTiet='" + chiTiet + '\'' +
                ", soLuongDaBan=" + soLuongDaBan +
                ", trangThai=" + trangThai +
                '}';
    }
}
