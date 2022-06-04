package hcmute.huynhlybang19110330.nhom10foody.model;

public class AddressModel {
    private int id;
    private Double lat;
    private Double lng;
    private String fulladdress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public AddressModel(int id, Double lat, Double lng, String fulladdress) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.fulladdress = fulladdress;
    }
}
