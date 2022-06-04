package hcmute.huynhlybang19110330.nhom10foody.model;

public class CartModel {
    private int id;
    private User user;
    private Restaurant restaurant;
    private int total;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public CartModel(int id, User user, Restaurant restaurant, int total) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.total = total;
    }
}
