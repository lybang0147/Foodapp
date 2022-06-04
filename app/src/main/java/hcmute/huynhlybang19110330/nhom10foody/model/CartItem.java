package hcmute.huynhlybang19110330.nhom10foody.model;

public class CartItem {
    private int id;
    private int cartid;
    private Food food;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem(int id, int cartid, Food food, int quantity) {
        this.id = id;
        this.cartid = cartid;
        this.food = food;
        this.quantity = quantity;
    }
}
