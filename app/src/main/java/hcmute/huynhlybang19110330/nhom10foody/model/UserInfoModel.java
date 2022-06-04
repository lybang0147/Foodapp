package hcmute.huynhlybang19110330.nhom10foody.model;

public class UserInfoModel {
    private int id;
    private User user;
    private String name;
    private AddressModel address;
    private byte[] image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public UserInfoModel(int id, User user, String name, AddressModel address, byte[] image) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.address = address;
        this.image = image;
    }
}
