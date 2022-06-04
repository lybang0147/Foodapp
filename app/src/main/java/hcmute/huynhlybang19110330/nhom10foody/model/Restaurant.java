package hcmute.huynhlybang19110330.nhom10foody.model;

public class Restaurant {
    private int Id;
    private String tenNhaHang;
    private AddressModel address;
    private String mota;
    private byte[] image;
    private int userId;
    private float danhGia;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(float danhGia) {
        this.danhGia = danhGia;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenNhaHang() {
        return tenNhaHang;
    }

    public void setTenNhaHang(String tenNhaHang) {
        this.tenNhaHang = tenNhaHang;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Restaurant(int id, String tenNhaHang, AddressModel address, String mota, byte[] image, int userId, float danhGia) {
        Id = id;
        this.tenNhaHang = tenNhaHang;
        this.address = address;
        this.mota = mota;
        this.image = image;
        this.userId = userId;
        this.danhGia = danhGia;
    }
}
