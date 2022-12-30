import java.util.Date;

public class Order {
    private final String id;
    private final String customer;
    private final Cart cart;
    private final Date date;
    private final int discount;
    private String status;

    public Order(String id, String customer, Cart cart, int discount) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.date = new Date();
        this.status = "Pending";
        this.discount = discount;
    }

    public Order(String id, String customer, Cart cart, String status, int discount) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.date = new Date();
        this.status = status;
        this.discount = discount;
    }



    public static void placeOrder(String fileName, Order order) {
        Method.writeOrderToDatabase(order, fileName);
        System.out.println("Order placed!");
    }



    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusDone() {
        this.status = "Done";
    }

    public int getDiscount() {
        return discount;
    }

    public int totalPrice () {
        return this.cart.getTotalAmount();
    }
}
