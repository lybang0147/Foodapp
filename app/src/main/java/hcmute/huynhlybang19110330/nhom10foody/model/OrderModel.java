package hcmute.huynhlybang19110330.nhom10foody.model;

public class OrderModel {
    private int id;
    private CartModel cart;
    private String name;
    private AddressModel resaddress;
    private AddressModel useraddress;
    private String date;
    private String payment;
    private int shipprice;
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CartModel getCart() {
        return cart;
    }

    public void setCart(CartModel cart) {
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressModel getResaddress() {
        return resaddress;
    }

    public void setResaddress(AddressModel resaddress) {
        this.resaddress = resaddress;
    }

    public AddressModel getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(AddressModel useraddress) {
        this.useraddress = useraddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getShipprice() {
        return shipprice;
    }

    public void setShipprice(int shipprice) {
        this.shipprice = shipprice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OrderModel(int id, CartModel cart, String name, AddressModel resaddress, AddressModel useraddress, String date, String payment, int shipprice, int total) {
        this.id = id;
        this.cart = cart;
        this.name = name;
        this.resaddress = resaddress;
        this.useraddress = useraddress;
        this.date = date;
        this.payment = payment;
        this.shipprice = shipprice;
        this.total = total;
    }
}
