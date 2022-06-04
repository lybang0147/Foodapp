package hcmute.huynhlybang19110330.nhom10foody.model;

public class Order {
    private int id;
    private CartModel cartModel;
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
        return cartModel;
    }

    public void setCart(CartModel cartModel) {
        this.cartModel = cartModel;
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

    public Order(int id, CartModel cartModel, String date, String payment, int shipprice, int total) {
        this.id = id;
        this.cartModel = cartModel;
        this.date = date;
        this.payment = payment;
        this.shipprice = shipprice;
        this.total = total;
    }
}
